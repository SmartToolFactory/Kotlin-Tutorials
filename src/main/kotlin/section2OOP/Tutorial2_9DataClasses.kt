package section2OOP

fun main(args: Array<String>) {

    val person1 = PersonData("John")
    val person2 = PersonData("Jack")
    person1.age = 10
    person2.age = 20

}

data class UserData(val name: String, val age: Int)

//To exclude a property from the generated implementations, declare it inside the class body:
data class PersonData(val name: String) {
    var age: Int = 0
}