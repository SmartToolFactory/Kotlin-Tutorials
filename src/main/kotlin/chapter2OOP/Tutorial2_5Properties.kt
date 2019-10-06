package chapter2OOP

fun main() {

    // INFO  Setters and getters
//    val setterAndGetterWrapper = SetterAndGetters()
//    println(setterAndGetterWrapper.stringRepresentation)
//    setterAndGetterWrapper.stringRepresentation = "Hello World"

    // INFO Backing Field
    val user = User("Jackson", "Hitch")
    user.myName = "Johnny"

    // INFO 🔥⚠️ Throws StackOverflow exception when get() called
//    println("User type ${user.myName}")

    // INFO Backing Property
    val humanWithBackingProperty = HumanWithBackingProperty()
    println("Human age: ${humanWithBackingProperty.age}")
    humanWithBackingProperty.age = 15
    println("Human age after set(): ${humanWithBackingProperty.age}")
    // Lambda method
    humanWithBackingProperty.printAge()


}

// INFO  Setters and getters
class SetterAndGetters {

    var stringRepresentation: String
        get() = this.toString()
        set(value) {
            setDataFromString(value) // parses the string and assigns values to other properties
        }

    // INFO 🔥🔥🔥⚠️ Using property = with a set() function calls set recursively and causes StackOverflow exception
    private fun setDataFromString(value: String) {
        // 🔥 !!! Causes recursive call to set() from this method
//        stringRepresentation = "New Assignment"
        println("🤨 SettersAndGetters setDataFromString $stringRepresentation")
    }
}


const val PREFIX = "[ABC]"


// INFO BACKING FIELDS
class Person {

    // set: if value set to first type have length < 1 => throw error else add prefix "ABC" to the type
    // get: if type is not empty -> trim for remove whitespace and add '.' else return default type
    var lastName: String = ""
        get() {
            if (field.isNotEmpty()) {
                return field.trim() + "."
            }
            return field
        }
        set(value) {
            if (value.length > 1) {
                field = PREFIX + value
            } else {
                throw IllegalArgumentException("Last type too short")
            }
        }
}


// INFO 🔥⚠️ Throws StackOverflow exception when get() called
class User(name: String, surName: String) {

    var myName: String = name
        get() {
            return myName.substring(0, 4)
        }

}


// INFO 🔥⚠️ Throws StackOverflow exceptions when get() or set() called for firstName or LastName
class User2 {
    var firstName: String //backing field generated
        get() = firstName
        set(value) {
            firstName = value
        }
    var lastName: String //backing field generated
        get() = lastName
        set(value) {
            lastName = value
        }
    val name: String //no backing field generated
        get() = "{$firstName $lastName}"
    var address: String = "XYZ" //^because there is no default //^implementation of an accessor

}


class Human {
    val age = 20
        get() {
            println("Age is: $field")
            return field
        }
}

// INFO Java Equivalent of Kotlin code for backing field. Note: This won't work in Kotlin class
//public final class Human {
//    private final int age = 20;
//
//    public final int getAge() {
//        String var1 = "Age is: " + this.age;
//        System.out.println(var1);
//        return this.age;
//    }
//}

// INFO 🔥 Backing Properties

class HumanWithBackingProperty {

    private var _age: Int = 20

    var age: Int
        get() {
            println("HumanWithBackingProperty get()")
            return _age
        }
        set(value) {
            println("HumanWithBackingProperty set()")
            _age = value
        }

    val printAge = {
        println("Age is: $_age")
    }
}

