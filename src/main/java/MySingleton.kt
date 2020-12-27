class MySingleton {

    var type = "Singleton"

    companion object {

        private var INSTANCE: MySingleton? = null
        val instance: MySingleton?
            get() {
                if (INSTANCE == null) {
                    INSTANCE = MySingleton()
                }
                return INSTANCE
            }
    }
}

fun main() {
    MySingleton.instance
}