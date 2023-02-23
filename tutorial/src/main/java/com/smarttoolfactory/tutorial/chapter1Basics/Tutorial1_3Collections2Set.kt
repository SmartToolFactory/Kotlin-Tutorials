package com.smarttoolfactory.tutorial.chapter1Basics

fun main() {

    /*
     ***** SETs *****
    */

    /*
     * INFO Instantiation
     */
    // Immutable(Read-Only)
    val setImmutable1 = setOf(3, 5, 1)          // [3, 5, 1]

    // Mutable
    val setHash = hashSetOf(3, 5, 1)      // [5, 1, 3]
    val setLinkedHash = linkedSetOf(3, 5, 1)    // [3, 5, 1]
    val setTree = sortedSetOf(3, 5, 1)    // [1, 3, 5]
    val setMutable = mutableSetOf(3, 5, 1)   // [3, 5, 1]

    setImmutable1.forEach { it -> println("setImmutable1 Item $it") }
    setHash.forEach { it -> println("setHash Item $it") }
    setLinkedHash.forEach { it -> println("setLinkedHash Item $it") }
    setTree.forEach { it -> println("setTree Item $it") }

    // When we put same object with hash code and equals it's replaced with previous one
    // If objects are not treated as same object from hashmap perspective new object is added
    // Hashset adds items as keys of inner hashMap as
    /*
        Object PRESENT = Object()
        mySet(person1) -> map.put(person1, PRESENT)
     */
    val person1 = Person("jon", 0)
    val person2 = Person("jon", 2)
    val mySet: HashSet<Person> = hashSetOf()
    val result1 = mySet.add(person1)
    val result2 = mySet.add(person2)
    println("Result1: $result1, result2: $result2")



}

class Person(
    val name: String, val id: Int
) {
//    override fun equals(other: Any?): Boolean {
//        if (other !is Person) return false
//        if (other.name == this.name) return true
//        return false
//    }
//
//    override fun hashCode(): Int {
//        return name.hashCode()
//    }
}