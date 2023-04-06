class ScopeFunctions {

    class User(
        val name: String,
        val age: Int,
        val gender: Boolean,
        var hasJob: Boolean = false,
    )

    fun main() {
        // let, run, apply, also, with
        // 1. let
        val a = 3
        a.let {}
        val user: User? = User("김광희", 10, true)

        // 수신객체.let {
        //
        //   마지막 줄 // return
        // }
        val age = user?.let { it.age }

        // 2. run
        // 수신객체.run { this ->
        //
        //   마지막 줄 return
        // }
        val kid = User("아이", 4, false)
        val kidAge = kid.run {
            this.age
        }

        // 3. apply
        // 수신객체.apply {
        //
        // }
        // return 값이 수신객체
        val kidName: User = kid.apply {
            hasJob = true
        }

        // 4. also
        // 수신객체.also {
        //
        // } return 수신객체
        val male = User("민수", 17, false, true)
        val maleValue = male.also {
            it.hasJob = false
        }
        // println(maleValue.hasJob) => false

        // 5. with
        // with(수신객체) {
        //
        // 마지막 줄 return
        // }

        val result = with(male) {
            hasJob = true
            hasJob
        }
    }
}