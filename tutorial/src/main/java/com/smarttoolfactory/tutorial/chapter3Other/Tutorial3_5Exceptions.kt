package com.smarttoolfactory.tutorial.chapter3Other

import java.io.*
import java.lang.Integer.parseInt

fun main() {

    try {
        // some code
    } catch (e: Exception) {
        // handler(if finally is available catch is optional)
    } finally {
        // optional finally block
    }


    // INFO Try is an expression
    val a: Int? = try {
        parseInt("5")
    } catch (e: NumberFormatException) {
        null
    }

    // INFO Checked Exceptions

    val stringBuilder = StringBuilder()
    stringBuilder.append(charArrayOf('a', 'b', 'c'), 1, 2)

    // INFO Checked Exception with File IO
    try {

        val homePath = System.getProperty("user.home")

        // Throws FileNotFoundException
        File("$homePath/desktop/test.txt")
                .writer().use {
                    it.write("Hello World")
                }

    } catch (e: FileNotFoundException) {
        println("Exception occurred: ${e.message}")
    }

    // INFO Nothing Type
    val person = Person(null)
    // Throws IllegalArgumentException: Name required since name is NULL
//    val s = person.name ?: throw IllegalArgumentException("Name required")

    val test = person.name ?: fail("Name required")
    println(test) // 'test' is known to be initialized at this point


    val x = null // 'x' has type `Nothing?`
    val l = listOf(null) // 'l' has type `List<Nothing?>

}


data class Person(val name: String?)

fun fail(message: String): Nothing {
    throw IllegalArgumentException(message)
}
