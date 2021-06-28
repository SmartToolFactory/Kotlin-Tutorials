package com.smarttoolfactory.tutorial.chapter6Advanced

fun main() {

    var listBaseShape = listOf(BaseShapeK())
    var listOfShape = listOf(ShapeK())

    // ❌ COMPILE ERROR
//    listOfShape = listBaseShape

    // THIS DOES NOT WORK IN JAVA, it should have ? super BaseShapeK to work
    // WORKS in KOTLIN
//    listBaseShape = listOfShape

    // ❌ COMPILE ERROR
//    listOfShape = listBaseShape

    /*
        Producer Consumer Behaviour
     */
    val shapeBuilder = ShapeBuilder(ShapeK())
    var shapeBuilderWithIN: ShapeBuilder<in ShapeK> = ShapeBuilder(CircleK())

    println("ShapeBuilder shape: ${shapeBuilderWithIN.shape}")
    shapeBuilderWithIN.shape = RectangleK()
    println("ShapeBuilder After shape: ${shapeBuilderWithIN.shape}")


    var shapeBuilderWithOUT: ShapeBuilder<out ShapeK> = ShapeBuilder(CircleK())

    /*
        ❌ COMPILE ERROR
        Type mismatch.
        Required:
        Nothing?
        Found:
        RectangleK
     */
//    shapeBuilderWithOUT.shape = RectangleK()

    val shapeBuilderIn: ShapeBuilderIn<ShapeK> = ShapeBuilderIn(CircleK())
    println("ShapeBuilderIn shape: ${shapeBuilderIn.fetchShape()}")
    shapeBuilderIn.updateShape(RectangleK())
    println("ShapeBuilderIn After shape: ${shapeBuilderIn.fetchShape()}")

    /*
        Assigning to Covariant or Contravariant
     */

//     🔥 Assigning concrete types to variants works
//    shapeBuilderWithIN = shapeBuilder
//    shapeBuilderWithOUT = shapeBuilder

    // ❌ Compile Error
//    shapeBuilder = shapeBuilderWithIN
//    shapeBuilder = shapeBuilderWithOUT

    val rectangleBuilder = ShapeBuilder(RectangleK())
    val baseShaBuilder = ShapeBuilder(BaseShapeK())

    // ❌ Compile Error IN type can only be assigned with higher(super) types
//    shapeBuilderWithIN = rectangleBuilder
    shapeBuilderWithIN = baseShaBuilder

    shapeBuilderWithOUT = rectangleBuilder
    // ❌ Compile Error OUT type can only be assigned with lower(sub) types
//    shapeBuilderWithOUT = baseShaBuilder
}

internal open class BaseShapeK

internal open class ShapeK : BaseShapeK()

internal class CircleK : ShapeK()

internal open class RectangleK : ShapeK()

internal class SquareK : RectangleK()

/**
 * Invariant class
 */
private class ShapeBuilder<T>(var shape: T? = null)

/**
 * Covariant class
 *
 * **shape** in constructor cannot have ***var*** parameters
 */
private class ShapeBuilderOut<out T : ShapeK>(private val shape: T?) {
    private var newShape = shape

    // 🔥🔥 ❌ Compile ErrorType parameter T is declared as 'out' but occurs in 'in' position in type T?
//    fun updateShape(shape: T?) {
//        newShape = shape
//
//    }
}

/**
 * Contravariant class
 * **shape** in constructor must be private. If it's not private it returns error
 *
 * ```Type parameter T is declared as 'in' but occurs in 'invariant' position in type T```
 */
private class ShapeBuilderIn<in T : ShapeK>(private var shape: T?) {

    fun updateShape(shape: T?) {
        this.shape = shape
    }

    fun fetchShape(): ShapeK? = shape
}


interface Source<out T>

/*

IN JAVA

    // Java
    interface Source<T> {}
    Copied!
    Then, it would be perfectly safe to store a reference to an instance of Source<String>
    in a variable of type Source<Object>- there are no consumer-methods to call.
    But Java does not know this, and still prohibits it:

    // Java
    void demo(Source<String> strs) {
      Source<Object> objects = strs; // 🔥🔥!!! Not allowed in Java
      // ...
    }

    ❌ COMPILE ERROR, this is not possible without bounds
    List<Object> objList = new ArrayList<>();
    List<String> strList = new ArrayList<>();

    objList = strList;
    List<String> is not subtype of List<Object>
 */

fun demoSource(strs: Source<String>) {
    val objects: Source<Any> = strs // 🔥 This is allowed in Kotlin, since T is an out-parameter
}

interface Dest<in T>

fun demoDest(strs: Dest<Any>) {
    val objects: Dest<String> = strs // 🔥 This is allowed in Kotlin, since T is an in-parameter
}


