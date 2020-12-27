package chapter6Advanced

fun main() {

    val box =  Box {
        getModifier().width = 100
        getModifier().height = 200
    }

    box.getWidth()
    box.getHeight()

}


class Box(content: MyScope.() -> Unit) {

    private val modifier: MyScope.Modifier = MyScope.getModifier()

    init {
        MyScope.content()
    }

    fun getWidth() = modifier.width

    fun getHeight() = modifier.height
}

interface MyScope {

    fun Modifier.updateWidth(width: Int) {
        this.width = width
    }

    fun Modifier.updateHeight(height: Int) {
        this.height = height
    }

    fun getModifier(): Modifier

    class Modifier(var width: Int = 0, var height: Int = 0)

    companion object : MyScope {

        private val modifier = Modifier(0, 9)

        override fun getModifier() = modifier
    }
}