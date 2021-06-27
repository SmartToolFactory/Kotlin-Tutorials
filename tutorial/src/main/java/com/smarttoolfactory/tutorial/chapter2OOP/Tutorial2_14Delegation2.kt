package com.smarttoolfactory.tutorial.chapter2OOP

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main() {

//    // INFO 🔥 Delegated Properties
//    val example = Example()
//    // getValue() function of Delegate class is called
//    println(example.p)
//    // setValue() function of Delegate class is called
//    example.p = "NEW"
//    println(example.p)
//
//    val derivedUser = DerivedUser()
//    derivedUser.todayTasks = "BUY SOMETHING"
//    println("PRINT: ${derivedUser.todayTasks}")
//
//    // INFO 🔥 ReadWriteProperty
//    val upperCaseString = UpperCaseString()
//    upperCaseString.str = "Hello "
//    println("Uppercase String: ${upperCaseString.str}")


    // INFO 🔥 Delegating to another property
    val myDelegateClass = MyDelegateClass(10, ClassWithDelegate(5))

    myDelegateClass.delegatedToTopLevel = 100
    myDelegateClass.delegatedToMember = 34
    myDelegateClass.delegatedAnotherClass = 13

    println("Top level: $topLevelInt, " +
            "memberInt: ${myDelegateClass.memberInt}, " +
            "anotherClassInstance.anotherClassInt: ${myDelegateClass.anotherClassInstance.anotherClassInt}")
    /*
        Prints:
        Top level: 100, memberInt: 34, anotherClassInstance.anotherClassInt: 13

        🔥 By delegating top level int to MyDelegate.delegatedToTopLevel any change on this
        property also changes topLevelInt
     */


    // INFO 🔥 Delegating to another property for Deprecating property
    val myClass = ClassDeprecateWithDelegate()
    // Notification: 'oldName: Int' is deprecated.
    // Use 'newName' instead
    myClass.oldName = 42
    println(myClass.newName) // 42

    myClass.newName = 100
    println("Old: ${myClass.oldName}, new: ${myClass.newName}")
    // Old: 100, new: 100

}


// INFO 🔥 Delegated Properties

class Delegate {

    // 🔥 first parameter is the object you read p from
    // second parameter holds a description of p itself (for example, you can take its name).
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

class Example {
    var p: String by Delegate()
}


class DelegatedUser {

    private var value: String? = null

    // INFO 🔥 thisRef: DerivedUser object, property is  todayTasks of DerivedUser class
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "DelegatedUser getValue() property type: ${property.name}, value: $value"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        this.value = value
        println("DelegatedUser setValue() thisRef: $thisRef, property: $property, property.type: ${property.name}, value: $value")
    }

}

class DerivedUser {
    var todayTasks: String by DelegatedUser()
}

// INFO 🔥 ReadWriteProperty
class UpperCaseString {
    var str by UpperCaseStringDelegate()
}

class UpperCaseStringDelegate : ReadWriteProperty<Any, String> {

    private var value: String = ""

    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return value
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        this.value = value.uppercase()
    }

}

// INFO 🔥 Delegating to another property

var topLevelInt: Int = 2

class ClassWithDelegate(var anotherClassInt: Int)

class MyDelegateClass(var memberInt: Int, val anotherClassInstance: ClassWithDelegate) {

    var delegatedToMember: Int by this::memberInt
    var delegatedToTopLevel: Int by ::topLevelInt

    var delegatedAnotherClass: Int by anotherClassInstance::anotherClassInt
}

var MyDelegateClass.extDelegated: Int by ::topLevelInt


// INFO 🔥 Delegating to another property for Deprecating property
class ClassDeprecateWithDelegate {
    var newName: Int = 0
    @Deprecated("Use 'newName' instead", ReplaceWith("newName"))
    var oldName: Int by this::newName
}

