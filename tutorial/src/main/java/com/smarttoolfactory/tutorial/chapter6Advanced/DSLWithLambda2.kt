package com.smarttoolfactory.tutorial.chapter6Advanced

/**
 * This is a sample to display how functions with literals used with Jetpack Compose.
 *
 * This is a series of mock functions to display simplified version of drawing with Compose.
 *
 * * Column uses [Layout] do draw on a [Canvas] function using [DrawScope]
 */
fun main() {

    val modifier = Modifier
    Column(modifier = modifier) {
        println("🔥 Draw this COLUMN")
    }
}

fun Column(modifier: Modifier, content: ColumnScope.() -> Unit) {

    Layout(modifier) {
        ColumnScope.content()
    }
}

interface ColumnScope {
    fun Modifier.alignColumn()

    companion object : ColumnScope {

        override fun Modifier.alignColumn() {
            println("🤔 Modifier for Column alignColumn() ")
        }

    }
}


interface Modifier {
    companion object : Modifier
}
 fun Layout(modifier: Modifier = Modifier,  content: () -> Unit) {
    println("Layout()")
    modifier.drawBehind {
        content()
    }
}

interface DrawScope {
    fun draw()
}

class DrawModifier(val onDraw: DrawScope.() -> Unit) : DrawScope {

    override fun draw() {
        println("⚠️ DrawModifier drawContent()")
        onDraw()
    }
}

fun Modifier.drawBehind(
    onDraw: DrawScope.() -> Unit
) {
    DrawModifier(
        onDraw = onDraw
    ).draw()
}