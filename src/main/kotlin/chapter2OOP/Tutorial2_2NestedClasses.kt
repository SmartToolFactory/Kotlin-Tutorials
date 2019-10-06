package chapter2OOP

fun main() {

    // TODO Static Nested Classes
    val line = BasicGraph.Line(1, 0, -2, 0)
    line.draw()

    // nested class must be initialized
    println(OuterOfNestedClass.NestedClass().description) // accessing property
    val objNested = OuterOfNestedClass.NestedClass() // object creation
    objNested.foo() // access member function

    // TODO Inner Classes

    val lineWithInner = BasicGraphWithInner("Graph with Inner").InnerLine(1, 2, 3, 4)
    lineWithInner.draw()

    println(OuterOfInnerClass().InnerClass().description) // accessing property
    var objInner = OuterOfInnerClass().InnerClass() // object creation
    objInner.foo() // access member function

}

// 🔥 INFO: Equivalent of Java's STATIC Nested class

/*
 * INFO Nested class can NOT access to any members of outer class
 */
class BasicGraph(val name: String) {

    class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
        fun draw(): Unit {
            println("Drawing Line from ($x1:$y1) to ($x2, $y2)")
        }
    }

    fun draw(): Unit {
        println("Drawing the graph $name")
    }
}

class OuterOfNestedClass {

    private var name: String = "Ashu"

    class NestedClass {

        var description: String = "code inside nested class"
        private var id: Int = 101

        fun foo() {
            // 🔥 INFO cannot access the outer class member
            //  print("type is ${type}")
            println("Id is ${id}")
        }
    }

}

// 🔥 INFO: Equivalent of Java's INNER class
/**
 * Inner class which has inner tag in front of class name, of an access field of outer class unlike nested class
 */
class BasicGraphWithInner(graphName: String) {

    private val name: String

    init {
        name = graphName
    }

    /**
     * Classes with inner declaration can access to members of outer class even if they are private
     */
    inner class InnerLine(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
        fun draw(): Unit {
            println("Drawing Line from ($x1:$y1) to ($x2, $y2) for  graph $name ")
        }
    }

    fun draw(): Unit {
        println("Drawing the graph $name")
    }

}


class OuterOfInnerClass {
    private var name: String = "Ashu"

    inner class InnerClass {

        var description: String = "code inside inner class"
        private var id: Int = 101

        fun foo() {
            // 🔥 INFO  ACCESS the outer class member even private
            println("type is ${name}")
            println("Id is ${id}")
        }
    }
}


class A {

    private val somefield: Int = 1

    inner class B {

        private val somefield: Int = 1

        fun foo(s: String) {
            println("Field <somefield> from InterfaceB" + this.somefield)

            // 🔥 this with @ annotation points to instance of defined class
            println("Field <somefield> from InterfaceB" + this@B.somefield)
            println("Field <somefield> from BaseClassA" + this@A.somefield)
        }
    }
}

