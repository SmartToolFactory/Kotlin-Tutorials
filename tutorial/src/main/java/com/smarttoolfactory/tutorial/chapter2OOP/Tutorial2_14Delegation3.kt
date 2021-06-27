package com.smarttoolfactory.tutorial.chapter2OOP

import kotlin.properties.Delegates

fun main() {

    // INFO 🔥 Standard Delegates
    // INFO 🔥 Lazy
    println(lazyValue)
    println(lazyValue)

//     INFO 🔥 Observable
    val user = UserObservable()
    user.name = "first"
    user.name = "second"

//     INFO 🔥 Vetoable
    println(max) // 0
    max = 10
    println(max) // 10

    max = 5 // will fail with IllegalArgumentException

    // INFO 🔥 Storing Properties in a Map
    val userMapDelegate = ImmutableUser(
        mapOf(
            "type" to "John Doe",
            "age" to 25
        )
    )

    println(userMapDelegate.name) // Prints "John Doe"
    println(userMapDelegate.age) // Prints 25
}

// INFO 🔥 Standard Delegates

// INFO 🔥 Lazy
// WARNING The lazy {...} delegate can only be used for val properties
val lazyValue: String by lazy {
    println("Invoked only the first time lazy initialized!")
    "Hello"
}


// INFO 🔥 Observable

class UserObservable {
    var name: String by Delegates.observable("<no type>") { prop, old, new ->
        println("$old -> $new")
    }
}

// INFO 🔥 Vetoable
var max: Int by Delegates.vetoable(0) { property, oldValue, newValue ->

    println("vetoable property: $property, oldValue: $oldValue, newValue: $newValue")
    if (newValue > oldValue) true
    else throw IllegalArgumentException("New value must be larger than old value.")
}


// INFO 🔥 Storing Properties in a Map

class ImmutableUser(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int by map
}


class MutableUser(val map: MutableMap<String, Any?>) {
    var name: String by map
    var age: Int by map
}
