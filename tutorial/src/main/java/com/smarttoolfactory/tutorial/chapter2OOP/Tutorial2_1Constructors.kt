package com.smarttoolfactory.tutorial.chapter2OOP

import com.smarttoolfactory.tutorial.chapter2OOP.model.*

fun main() {

    val a: String? = null
    val b: String = ""
    println(a == b)

    // create obj object of myClass class
//    val obj = MyClass()
//    obj.printMe()
//
    val person1 = Person("Alex", "Smith", 29)
    val person2 = Person("Jane", "Smith", null)
//
    println("${person1.firstName},${person1.lastName} is  ${person1.age} years old")
//    println("${person2.firstName},${person2.lastName} is ${person2.age?.toString() ?: "?"} years old")
//
    val personType2 = Person2(
        "June",
        "Smith",
        23
    )

//    println("Name: ${personType2.getType()}, age: ${personType2.getAge()}")



    // INFO Init Blocks
    val initOrder = InitOrderDemo("Demo")

//    // INFO Secondary Constructors
    val person3 = Person4("Jane", "White")
    val person4 = Person4("Jake", "White")

    val constructors = Constructors(5)

    val auto1 = Auto(5, "Honda")
    val auto2 =
        Auto(name = "Ferrari")
    val auto3 = Auto(2, "BMW")


}