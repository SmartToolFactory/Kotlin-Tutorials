package chapter6Advanced

/**
 * This is a sample to display how functions with literals used with Jetpack Compose.
 *
 * This is a series of mock functions to display simplified version of drawing with Compose.
 *
 * * Column uses [Layout] do draw on a [Canvas] function using [DrawScope]
 */
fun main() {

    Column(modifier = Modifier) {
        println("🔥 Draw this COLUMN")
    }
}

fun Column(modifier: Modifier, content: ColumnScope.() -> Unit) {

    Layout(modifier) {
        ColumnScope.content()
    }
}

interface ColumnScope {
    companion object : ColumnScope
}


interface Modifier {
    companion object : Modifier
}

inline fun Layout(modifier: Modifier = Modifier, crossinline content: () -> Unit) {
    println("Layout()")
    Canvas(modifier = modifier) {
        println("Layout() -> Canvas()")
        content()
    }
}


inline fun Canvas(modifier: Modifier = Modifier, crossinline content: DrawScope.() -> Unit) {
    println("Canvas()")
    modifier.drawBehind {
        content()
    }
}

interface DrawScope {
    fun drawContent()
}

class DrawModifier(val onDraw: DrawScope.() -> Unit) : DrawScope {

    override fun drawContent() {
        println("⚠️ DrawModifier drawContent()")
        onDraw()
    }
}

fun Modifier.drawBehind(
    onDraw: DrawScope.() -> Unit
) {
    DrawModifier(
        onDraw = onDraw
    ).drawContent()
}