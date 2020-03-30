package chapter2OOP

/**
 * Example for data classes
 *
 * * — The primary constructor needs to have at least one parameter
 * * — All primary constructor parameters need to be marked as val or var
 * * — Data classes cannot be abstract, open, sealed or inner
 */
fun main() {

    val person1 = PersonData("John")
    val person2 = PersonData("John")

    // 🔥🔥 age property is not declared inside Constructor, so does NOT effect equals and hash code
    person1.age = 10
    person2.age = 20

    // 🔥🔥 Copying data from one data class to another, only copies properties in constructor
    val person3 = person2.copy(name = "Dave")

    // 🔥 If primary constructor properties have same values objects have Structural Equality
    val person4 = person2.copy(person2.name)


    // Prints: person1: 2314539, person2: 2314539, person3: 2122764, person4: 2314539
    println("person1: ${person1.hashCode()}, person2: ${person2.hashCode()}, " +
            "person3: ${person3.hashCode()}, person4: ${person4.hashCode()}")

    // Referential equality returns true only for the SAME objects even for data classes
    println("Person1 == Person2 -> ${person1 == person2}") // prints true
    println("Person1 === Person2 -> ${person1 === person2}") // prints false

    // 🔥 variables with default values can be omitted when data class is instantiated
    val carData = CarData(manifacturer = "", model = "")

    /*
          ***  EQUALITY ***
     */

    // == means Structural equality which looks properties of a class
    // === means Referential equality which is true for a and b point to the same object

    val bookData1 = BookData("LOTR", 54)
    val bookData2 = BookData("LOTR", 54)
    val bookData3 = bookData1

    val book1 = Book("LOTR", 54)
    val book2 = Book("LOTR", 54)
    val book3 = book1

    println("Book1: ${book1.hashCode()} == Book2: ${book2.hashCode()} -> ${book1 == book2}") // prints false
    // Book1: 1096979270 == Book2: 1078694789 -> false
    println("Book1 == Book2 -> ${book1 == book2}") // prints false
    println("Book1 === Book2 -> ${book1 === book2}") // prints false

    // Data class
    println("BookData1: ${bookData1.hashCode()} == BookData2: ${bookData2.hashCode()} -> ${bookData1 == bookData2}") // prints true
    // BookData1: 72624405 == BookData2: 72624405 -> true
    println("BookData2: ${bookData2.hashCode()} == BookData3: ${bookData3.hashCode()} -> ${bookData2 == bookData3}") // prints true
    // BookData2: 72624405 == BookData3: 72624405 -> true

    println("BookData1 === BookData2 -> ${bookData1 === bookData2}") // prints false


}

//🔥🔥 To exclude a property from the generated implementations, declare it inside the class body:
data class PersonData(val name: String) {
    var age: Int = 0

}

// 🔥 variables with default values can be omitted when data class is instantiated
data class CarData(
    var type: Int? = 0,
    var manifacturer: String,
    var model: String
) {
    fun type(type: Int): CarData = apply { this.type = type }

}


/**
 * This example is from Stackoverflow
 *
 * definition 1

data class Person (var name:String, var age:Int)
definition 2

class Person (var name:String, var age:Int)
definition 3

class Person (){
var name:String = ""
var age:Int = 1
}

Difference in equals, hashCode, & toString
the most important difference between definition 1 and definitions 2 & 3 is that in definition 1, the equals, hashcode and toString methods are overridden for you:

equals and hashCode methods test for structural equality
toString method returns a nice, human-friendly string
Code example:

NOTE: in Kotlin, the == operator calls an object's .equals() method. see operator overloading on kotlinlang.org for more info.

data class Person1 (var name:String, var age:Int)
class Person2 (var name:String, var age:Int)

@Test fun test1()
{
val alice1 = Person1("Alice", 22)
val alice2 = Person1("Alice", 22)
val bob = Person1("bob", 23)

// alice1 and alice2 are structurally equal, so this returns true.
println(alice1 == alice2)   // true

// alice1 and bob are NOT structurally equal, so this returns false.
println(alice1 == bob)      // false

// the toString method for data classes are generated for you.
println(alice1)     // Person1(name=Alice, age=22)
}

@Test fun test2()
{
val alice1 = Person2("Alice", 22)
val alice2 = Person2("Alice", 22)
val bob = Person2("bob", 23)

// even though alice1 and alice2 are structurally equal, this returns false.
println(alice1 == alice2) // false
println(alice1 == bob)    // false

// the toString method for normal classes are NOT generated for you.
println(alice1)  // Person2@1ed6993a
}
Difference in constructors
another difference between definitions 1 & 2 and definition 3 is that:

definitions 1 & 2 both have a constructor that takes 2 parameters
definition 3 only has a no argument constructor that assigns default values to the class members.
Code example:

data class Person1 (var name:String, var age:Int)
class Person2 (var name:String, var age:Int)
class Person3 ()
{
var name:String = ""
var age:Int = 1
}

@Test fun test3()
{
Person1("alice",22)     // OK
Person2("bob",23)       // OK
Person3("charlie",22)   // error

Person1()   // error
Person2()   // error
Person3()   // OK
}
The copy method
Finally, another difference between definition 1 and definitions 2 & 3 is that in definition 1,
a copy method is generated for it. Here's an example of how it can be used:

val jack = Person1("Jack", 1)
val olderJack = jack.copy(age = 2)

// jack.age = 1
// olderJack.age = 2
 */

class Book(val name: String, val age: Int)

data class BookData(val name: String, val age: Int)




