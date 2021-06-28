package com.smarttoolfactory.tutorial.chapter6Advanced


fun main() {

    val ints: Array<Int> = arrayOf(1, 2, 3)
    val any = Array<Any>(3) { "" }
    copy(ints, any)

}


fun copy(from: Array<out Any>, to: Array<Any>) {
    from.forEachIndexed { index, any ->
        to[index] = any
    }
}
