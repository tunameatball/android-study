package com.kkh.record_app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kkh.record_app.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity(), OnTimerTickListener {

    companion object {
        const val REQUEST_CODE_RECORD_AUDIO = 200
    }


    enum class State {
        RELEASE, RECORDING, PLAYING
    }

    private lateinit var binding: ActivityMainBinding
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var fileName: String = ""
    private var state = State.RELEASE
    private lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fileName = "${externalCacheDir?.absolutePath}/audio_record_test.3gp"
        timer = Timer(this)

        binding.ivRecord.setOnClickListener {
            when (state) {
                State.RELEASE -> {
                    doRecord()
                }

                State.RECORDING -> {
                    onRecord(false)
                }

                else -> {
                    // do nothing
                }
            }
        }

        binding.ivPlay.setOnClickListener {
            when (state) {
                State.RELEASE -> {
                    onPlay(true)
                }

                State.PLAYING -> {
                    onPlay(false)
                }

                else -> {
                    // do nothing
                }
            }
        }

        binding.ivStop.setOnClickListener {
            when (state) {
                State.PLAYING -> {
                    onPlay(false)
                }
                else -> {
                    // do nothing
                }
            }
        }
    }

    private fun doRecord() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> {
                onRecord(true)
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.RECORD_AUDIO
            ) -> {
                showPermissionRationalDialog()
            }

            else -> {
                requestPermissionRecordAudio()
            }
        }
    }

    private fun onRecord(start: Boolean) = if (start) startRecording() else stopRecording()


    private fun startRecording() {
        state = State.RECORDING

        recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(this)
        } else {
            MediaRecorder()
        }.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e("Recorder PrePare()", e.localizedMessage?.plus("") ?: "${e.message}")
            }

            start()
        }

        binding.viewWave.clearData()
        timer.start()

        binding.ivRecord.apply {
            setImageDrawable(
                ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.baseline_stop_24
                )
            )
            imageTintList = ColorStateList.valueOf(Color.BLACK)
        }

        binding.ivPlay.apply {
            isEnabled = false
            alpha = 0.3f
        }
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }

        recorder = null

        timer.stop()

        state = State.RELEASE

        binding.ivRecord.apply {
            setImageDrawable(
                ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.baseline_fiber_manual_record_24
                )
            )
            imageTintList = ColorStateList.valueOf(Color.RED)
        }

        binding.ivPlay.apply {
            isEnabled = true
            alpha = 1f
        }
    }

    private fun onPlay(start: Boolean) = if (start) startPlaying() else stopPlaying()

    private fun startPlaying() {
        state = State.PLAYING
        player = MediaPlayer().apply {
            setDataSource(fileName)
            try {
                prepare()
                start()
            } catch (e: IOException) {
                Log.e("startPlaying", e.localizedMessage?.plus("") ?: "${e.message}")
            }
        }

        binding.viewWave.clearWave()
        timer.start()

        player?.setOnCompletionListener {
            stopPlaying()
        }

        binding.ivRecord.isEnabled = false
        binding.ivRecord.alpha = 0.3f
    }

    private fun stopPlaying() {
        state = State.RELEASE

        player?.release()
        player = null

        timer.stop()

        binding.ivRecord.isEnabled = true
        binding.ivRecord.alpha = 1f
    }

    private fun showPermissionRationalDialog() {
        AlertDialog.Builder(this)
            .setMessage("녹음 권한이 있어야 앱을 정상적으로 사용할 수 있습니다")
            .setPositiveButton("권한 허용하기") { _, _ ->
                requestPermissionRecordAudio()
            }
            .setNegativeButton("취소") { d, _ ->
                d.cancel()
            }
            .show()
    }

    private fun showPermissionSettingDialog() {
        AlertDialog.Builder(this)
            .setMessage("녹음 권한이 없으면 앱을 정상적으로 사용할 수 없습니다")
            .setPositiveButton("권한 설정") { _, _ ->
                navigateToAppSetting()
            }
            .setNegativeButton("취소") { d, _ ->
                d.cancel()
            }
            .show()
    }

    private fun navigateToAppSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }

        startActivity(intent)
    }

    private fun requestPermissionRecordAudio() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            REQUEST_CODE_RECORD_AUDIO
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val audioRecordPermissionGrant =
            requestCode == REQUEST_CODE_RECORD_AUDIO && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED

        if (audioRecordPermissionGrant) {
            onRecord(true)
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.RECORD_AUDIO
                )
            ) {
                showPermissionRationalDialog()
            } else {
                showPermissionSettingDialog()
            }
        }

    }

    override fun onTick(duration: Long) {
        val millisecond = duration % 1000
        val second = (duration / 1000) % 60
        val minute = (duration / 1000 / 60)

        binding.tvTimer.text = String.format("%02d:%02d.%d", minute, second, millisecond / 100)

        if (state == State.PLAYING) {
            binding.viewWave.replayAmplitude()
        } else if (state == State.RECORDING) {
            binding.viewWave.addAmplitude(recorder?.maxAmplitude?.toFloat() ?: 0f)
        }
    }
}