package chapter1Basics

import java.util.*

fun main() {

    // 🔥 INFO Prints from 0 to 5
    for (i in 0..5) {
        println("Index: $i")
    }

    // This list does not have remove and methods
    val daysOfWeek: List<String> = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

    println("List item get 3rd: ${daysOfWeek.get(2)}")


    // For each like loop
    for (day in daysOfWeek) {
        println("Day: $day")
    }

    // 🔥 INFO For loop with INDEX and VALUES
    for ((index, value) in daysOfWeek.withIndex()) {
        println("Day withIndex: #$index: $value")
    }

    // ----- LOOPING -----
    // You can use for loops to cycle through arrays
    // ranges, or anything else that implements the
    // iterator function

    for (x in 1..10) {
        println("Loop in range 1..10 : $x")
    }

    // Generate a random number from 1 to 50
    val rand = Random()
    val magicNum = rand.nextInt(50) + 1

    // While loops while a condition is true
    var guess = 0

    while (magicNum != guess) {
        guess += 1
    }

    println("Magic num is $magicNum and you guessed $guess")

    for (x in 1..20) {

        println("Loop before continue $x")
        if (x % 2 == 0) {
            // Continue jumps back to the top of the loop
            continue
        }

        println("Odd : $x")

        // Break jumps out of the loop and stops looping
        if (x == 15) break

    }

    /*
        Loop through Arrays
     */
    val array: Array<Int> = arrayOf(3, 6, 9)

    // Iterate for indexes
    for (i in array.indices) {
        println("Mult 3 : ${array[i]}")
    }

    // Output indexes
    for ((index, value) in array.withIndex()) {
        println("Index : $index & Value : $value")
    }

    val testArray = IntArray(4)

    testArray.forEachIndexed { index, value ->
        println("forEachIndexed: index: $index, value: $value")
    }

}