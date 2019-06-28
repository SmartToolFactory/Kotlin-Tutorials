package section3Other

fun main(args: Array<String>) {

    println("Primitive Equality")
    val int1 = 10
    val int2 = 10

    println(int1 == int2)        // true
    println(int1.equals(int2))   // true
    println(int1 === int2)       // true


    println("Class Equality")

    class Employee(val name: String)

    val emp1 = Employee("Test")
    val emp2 = Employee("Test")

    println(emp1 == emp2)      //false
    println(emp1.equals(emp2)) //false
    println(emp1 === emp2)     //false

    println(emp1.name == emp2.name)       //true
    println(emp1.name.equals(emp2.name))  //true
    println(emp1.name === emp2.name)      //true

    println("Data Class Equality")

    data class EmployeeData(val name: String)

    val empData1 = EmployeeData("Test")
    val empData2 = EmployeeData("Tes")

    println(empData1 == empData2)         //true
    println(empData1.equals(empData2))    //true
    println(empData1 === empData2)        //false

    println(empData1.name == empData2.name)      //true
    println(empData1.name.equals(empData2.name)) //true
    println(empData1.name === empData2.name)     //true

    println("Numbers")
    val number1 = Integer(10) // create new instance
    val number2 = Integer(10) // create new instance
    val number3 = number1

    // check if number1 and number2 are Structural equality
    println(number1 == number2) // prints true

    // check if number1 and number2 points to the same object
    // in other words, checks for Referential equality
    println(number1 === number2) // prints false

    // check if number1 and number3 points to the same object
    println(number1 === number3) // prints true

}