package com.smarttoolfactory.tutorial.chapter6Advanced

import java.util.*

fun main() {

    val ints: Array<Int> = arrayOf(1, 2, 3)
    val any = Array<Any>(3) { "" }
    copy(ints, any)

    var listBaseShape = listOf(BaseShapeK())
    var listOfShape= listOf(ShapeK())

    // 🔥 Compile ERROR
    // THIS DOES NOT WORK IN JAVA, it should have ? super BaseShapeK to Work
//    listOfShape = listBaseShape

    listBaseShape = listOfShape


    val shapeBuilder: ShapeBuilder<in ShapeK> = ShapeBuilder(CircleK())
    println("ShapeBuilder shape: ${shapeBuilder.shape}")
    shapeBuilder.shape = RectangleK()
    println("ShapeBuilder After shape: ${shapeBuilder.shape}")


    val shapeBuilder2: ShapeBuilder<out ShapeK> = ShapeBuilder(CircleK())
    /*
        🔥 Compile Error
        Type mismatch.
        Required:
        Nothing?
        Found:
        RectangleK
     */
//    shapeBuilder2.shape = RectangleK()


    val shapeBuilderIn: ShapeBuilderIn<ShapeK>  = ShapeBuilderIn(CircleK())
    println("ShapeBuilderIn shape: ${shapeBuilderIn.fetchShape()}")
    shapeBuilderIn.updateShape(RectangleK())
    println("ShapeBuilderIn After shape: ${shapeBuilderIn.fetchShape()}")



}

internal open class BaseShapeK

internal open class ShapeK : BaseShapeK()

internal class CircleK : ShapeK()

internal class RectangleK : ShapeK()

internal class TriangleK : ShapeK()

/**
 * Invariant class
 */
private class ShapeBuilder<T : ShapeK>(var shape: T? = null)

/**
 * Covariant class
 *
 * **shape** in constructor cannot have ***var*** parameters
 */
private class ShapeBuilderOut<out T : ShapeK>(private val shape: T?) {

    private var newShape = shape

    // 🔥🔥 COMPILE ERROR Type parameter T is declared as 'out' but occurs in 'in' position in type T?
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

    🔥 COMPILE ERROR, this is not possible without bounds
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


open class Person

open class Banker : Person()


fun copyList(src: List<Person>, dest: MutableList<in Person>) {
    src.forEach { person: Person ->
        dest.add(person)
    }
}

fun copy(from: Array<out Any>, to: Array<Any>) {
    from.forEachIndexed { index, any ->
        to[index] = any
    }
}


fun copyFrom(src: List<Person>): MutableList<in Banker> {

    val list: MutableList<in Person> = ArrayList()

    src.forEach { person: Person ->
        list.add(person)
    }
    return list
}
