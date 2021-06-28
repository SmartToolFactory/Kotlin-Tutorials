package com.smarttoolfactory.tutorial.chapter6Advanced

fun main() {

}

interface Producer<out T> {
    fun produce(): T
}

interface Consumer<in T> {
    fun consume(t: T)
}