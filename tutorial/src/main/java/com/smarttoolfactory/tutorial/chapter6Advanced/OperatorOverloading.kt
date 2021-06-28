package com.smarttoolfactory.tutorial.chapter6Advanced

fun main() {

    val firstText = StringFirstLetter("Hello")
    val secondText = StringFirstLetter("World")

    val text = firstText plus secondText
    val text2 = firstText + secondText

    println(text) // Prints HW
}


class StringFirstLetter(val value: String) {
//    operator fun plus(other: StringFirstLetter): String {
//        return "${this.value.first()}${other.value.first()}"
//    }
}

// With infix
 infix operator fun StringFirstLetter.plus(other: StringFirstLetter): String {
    return "${this.value.first()}${other.value.first()}"
}