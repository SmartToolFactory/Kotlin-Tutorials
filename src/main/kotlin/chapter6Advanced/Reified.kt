package chapter6Advanced


class Reified {

    fun main() {

        val result = getClassOf<String>()


        val str = result.newInstance()


        println(str)


    }

    private inline fun <reified T : Any> getClassOf(): Class<T> {
        return T::class.java
    }


}