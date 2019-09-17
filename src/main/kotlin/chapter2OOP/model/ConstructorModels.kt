package chapter2OOP.model

class MyClass {

    // property (data member)
    private var name: String = "Tutorials.point"

    // member function
    fun printMe() {
        println("You are at the best Learning website Named-" + name)
    }
}


// INFO CONSTRUCTOR
// age parameter can be NULL
// 🔥 INFO If the primary constructor does not have any annotations or visibility modifiers, the constructor keyword can be omitted:
//🔥 INFO If the constructor has annotations or visibility modifiers, the constructor keyword is required, and the modifiers go before it:
class Person constructor(var firstName: String, val lastName: String, val age: Int?) {
    //...
}

// INFO INIT BLOCK

// INFO During an instance initialization,
// the initializer blocks are executed in the 🔥 SAME ORDER as they appear in the class body,
// interleaved with the property initializers:

class InitOrderDemo(name: String) {

    // 1st
    val firstProperty = "First property: $name".also(::println)

    // 2nd
    init {
        println("First initializer block that prints ${name}")
    }

    // 3rd
    val secondProperty = "Second property: ${name.length}".also(::println)

    // 4th
    init {
        println("Second initializer block that prints ${name.length}")
    }
}


class MYNewClass(private val name: String) {

}

// INFO 🔥 Assign Constructor parameters to class fields
// INFO 🔥 ⚠️ ️Prefixing your constructor arguments with val or var is not a must;
// if you don't want the getter (or setter if you use var) to be generated, you can always do the following:

class Person2(firstName: String, lastName: String, howOld: Int?) {

    private val name: String
    private val age: Int?

    init {
        // IMPORTANT 🔥🔥🔥 Properties must be initialized in constructor(this could also be inside init{}),
        // or be abstract or lateinit
        this.name = "$firstName,$lastName"
        this.age = howOld
    }

    fun getName(): String = this.name
    fun getAge(): Int? = this.age
}

class Customer(name: String) {
    val customerKey = name.toUpperCase()
}

// INFO 🔥 Constructors with val/var properties
class User(val id: Long, email: String) {
    val hasEmail = email.isNotBlank()    //email can be accessed here

    init {
        //email can be accessed here
    }

    fun getEmail() {
        // 🔥 email can't be accessed here
    }
}

// INFO SECONDARY CONSTRUCTORS

class Person3 {

    // INFO Secondary Constructor
    constructor(firstName: String, lastName: String) {
        println("😳 Secondary constructor of Person3")
    }
}

class Person4 constructor(val firstName: String, val lastName: String, val age: Int?) {

    // INFO 🔥 Secondary Constructor that calls Primary Constructor
    constructor(firstName: String, lastName: String) : this(firstName, lastName, null) {
        println("😳 Secondary constructor of Person4")
    }
}

class Constructors {

    constructor(i: Int) {
        println("😎 Secondary Constructor of Constructors class")
    }

    // INFO 🔥 init block is called before secondary constructor
    init {
        println("Init block of Constructors class")
    }

}

class Auto(age: Int) {


    init {
        println("🥳 Init block of Auto class with $age")
    }

    constructor(name: String) : this(0) {
        println("🚗🚗 Secondary Constructor of Auto with type $name")
    }

    constructor(i: Int, name: String) : this(i) {
        println("🚙 Secondary Constructor of Auto class with type $name and i $i")
    }
}

