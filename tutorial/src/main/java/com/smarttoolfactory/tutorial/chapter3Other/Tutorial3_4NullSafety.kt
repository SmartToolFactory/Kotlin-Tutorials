package com.smarttoolfactory.tutorial.chapter3Other

fun main() {

    val a: String = "abc"
//    a = null // compilation error

    var b: String? = "abc"
    b = null // ok print(b)
//    val l = b.length // WARNING error: variable 'b' can be null

    // INFO Checking for null in conditions
    val l = if (b != null) b.length else -1


    val c: String? = "Kotlin"
    if (c != null && c.length > 0) {
        print("String of length ${c.length}")
    } else {
        print("Empty string")
    }


    val notNullString = "Kotlin"
    val nullableString: String? = null
    println(nullableString?.length)
    println(notNullString?.length) // Unnecessary safe call

    // INFO Safe Calls with Let
    val listWithNulls: List<String?> = listOf("Kotlin", null)
    for (item in listWithNulls) {
        item?.let { println(it) } // prints Kotlin and ignores null
    }

//    BaseClassA safe call can also be placed on the left side of an assignment.
//    Then, if one of the receivers in the safe calls chain is null,
//    the assignment is skipped, and the expression on the right is not evaluated at all:

    // If either `person` or `person.department` is null, the function is not called:
//    person?.department?.head = managersPool.getManager()

    // INFO Elvis Operator
    val myInt: Int = if (b != null) b.length else -1
    val myInt2 = b?.length ?: -1

    // INFO The !! Operator
//    val nullableInt = b!!.length // Throws NPE since b is NULL

    // INFO Safe Casts
//    Regular casts may result into a ClassCastException if the object is not of the target type.
//    Another option is to use safe casts that return null if the attempt was not successful:
    val randomVal: Any = 3
    val aInt: Int? = randomVal as? Int

    // INFO Collections of Nullable Type
    val nullableList: List<Int?> = listOf(1, 2, null, 4)
    // Filters out the null elements
    val intList: List<Int> = nullableList.filterNotNull()


}