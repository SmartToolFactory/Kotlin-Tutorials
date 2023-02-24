package com.smarttoolfactory.tutorial.chapter6Advanced

fun main() {

    val myMap:HashMap<Person, Int> = hashMapOf()
    for (i in 0..1000) {
        myMap[Person("jon", id = i)] = 1
    }

    val person1 = Person("jon", 0)
    val person2 = Person("jon", 10)

    println("person1==person2 ${person1==person2}")
    println("myMap size: ${myMap.size}")

}

/**
 * Need to override equals and hash code to use objects from this class to be same keys
 * of HashMap
 */
class Person(private val name: String, private val id: Int) {
    override fun equals(other: Any?): Boolean {
        if(other !is Person) return  false

        if (other.name == this.name) return  true

        return false
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}