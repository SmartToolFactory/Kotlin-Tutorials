package chapter2OOP

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JComponent

fun main() {

    val window = Window()

    // This is Anonymous inner class that implements OnClickListener interface

    // INFO 🔥 Object Expressions
    window.onWindowClick(object : OnClickListener {
        override fun onClick() {
            println("It's clicked!")
        }
    })

    val myClickListener = object : OnClickListener {
        override fun onClick() {

        }

    }

    // 🔥🔥🔥 NOT WORKING, SAM conversion does not work for Kotlin interfaces, at least as of 1.31
//    window.onWindowClick(OnClickListener{
//
//    })

    val handler = Handler()

    // Both handler methods are same
    handler.post(object : Runnable {
        override fun run() {
            println("Runnable run() thread: ${Thread.currentThread().name}")
        }
    })

    // INFO SAM conversion of interface
    handler.post(Runnable {
        println("Hello world from thread ${Thread.currentThread().name}")
    })


    val myRunnable = object : Runnable {
        override fun run() {
            println("Runnable run() thread: ${Thread.currentThread().name}")
        }
    }

    val thread0 = Thread(myRunnable)


    val thread1 = Thread(object : Runnable {
        override fun run() {
            println("Hello thread1 ${Thread.currentThread().name}")

        }
    })

    // INFO SAM conversion of interface
    val thread2 = Thread(Runnable {
        println("Hello thread2 ${Thread.currentThread().name}")
    })

    // INFO SAM conversion of Thread without Runnable
    val thread3 = Thread {
        println("Hello thread3 ${Thread.currentThread().name}")
    }

    thread0.start()
    thread1.start()
    thread2.start()
    thread3.start()

    foo()

    println("************** Object Declarations **************")

    // INFO 🔥 Object Declarations
    var mySingletonObject = MySingletonObject.getInstance()
    println("MySingletonObject first: $mySingletonObject")
    mySingletonObject = MySingletonObject.getInstance()
    // INFO 🔥 ⚠️ Both objects are the same
    println("MySingletonObject second: $mySingletonObject")


    // INFO 🔥 The type of the companion object can be omitted, in which case the type Companion will be used:
    val x = CompanionClass.Companion
    val y = CompanionClass.Companion
    // INFO 🔥 ⚠️⚠️⚠️ x and y refer to SAME OBJECT
    x.test()
    y.test()

    /*
        *** Companion Objects
     */
    // These are companion objects
    val companion1: MyClass1.Named = MyClass1
    val companion2: MyClass2.Companion = MyClass2

    // INFO 🔥 Companion Objects create new MyCustomClass instances because they return new instances ofMyCustomClass()
    // INFO 🔥 ⚠️ NOT SAME OBJECTS
    val instance1 = MyCustomClass.Factory.create()
    val instance2 = MyCustomClass.create()
    println("MyCustomClass created in companion object-> instance1: $instance1, instance2: $instance2")

    // INFO 🔥 ⚠️  SAME OBJECTS because they are Companion Objects in type of Factory
    val f: Factory<MyClass> = MyCompanionClass
    val g: Factory<MyClass> = MyCompanionClass
    print("Factory MyCompanionClass instances f $f, and g $g")

    val myClass1 = f.create()
    val myClass2 = f.create()

    println("Companion create() myClass1 $myClass1, myClass2: $myClass2")
}

// INFO 🔥 Object Expressions

class Window() {

    fun onWindowClick(listener: OnClickListener) {
        listener.onClick()
    }

}

interface OnClickListener {
    fun onClick()
}

class Handler() {
    fun post(runnable: Runnable) {
        runnable.run()
    }
}

// INFO 🔥 Anonymous object that extends J class and implements K interface
open class J(x: Int) {
    public open val y: Int = x
}

interface K {

}

val jk: J = object : J(1), K {
    override val y = 15
}

//If, by any chance, we need "just an object", with no nontrivial supertypes, we can simply say:
fun foo() {
    val adHoc = object {
        var x: Int = 0
        var y: Int = 0
    }
    println("foo() $(adHoc.x + adHoc.y)")
}

class L {

    // INFO 🔥 ⚠️⚠️⚠️  Private function, so the return type is the anonymous object type private
    private fun foo() = object {
        val x: String = "x"
    }

    // INFO 🔥 ⚠️⚠️⚠️  Public function, so the return type is Any
    fun publicFoo() = object {
        val x: String = "x"
    }

    fun bar() {
        val x1 = foo().x // Works
//   🙄 val x2 = publicFoo().x // ERROR: Unresolved reference 'x'
    }
}


//Just like Java's anonymous inner classes, code in object expressions can access variables from the enclosing scope.
//(Unlike Java, this is not restricted to final or effectively final variables.)
fun countClicks(window: JComponent) {

    // INFO 🔥 ⚠️ Anonymous inner class can access this variables without them being declared final
    var clickCount = 0
    var enterCount = 0

    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            clickCount++
        }

        override fun mouseEntered(e: MouseEvent) {
            enterCount++
        }
    })
}


// INFO 🔥 Object Declarations
object MySingletonObject {
    fun getInstance(): MySingletonObject {
        // return single instance of object
        return MySingletonObject
    }
}


class CompanionClass {
    companion object {
        // INFO 🔥 ⚠️⚠️ this refers to same object whenever this method is called
        fun test() {
            println("CompanionClass test() $this")
        }
    }
}

// INFO Companion object type can be omitted
class MyClass1 {
    companion object Named {}
}

class MyClass2 {
    companion object {}
}


// INFO 🔥 Companion Objects create instance of non-singleton
class MyCustomClass {
    companion object Factory {
        @JvmStatic
        fun create(): MyCustomClass = MyCustomClass()
    }
}

//Note that, even though the members of companion objects look like static members in other languages,
// at runtime those are still instance members of real objects, and can, for example, implement interfaces:

interface Factory<T> {
    fun create(): T
}

class MyCompanionClass {

    companion object : Factory<MyClass> {
        override fun create(): MyClass = MyClass()
    }
}




