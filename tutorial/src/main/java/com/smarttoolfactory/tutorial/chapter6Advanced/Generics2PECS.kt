package com.smarttoolfactory.tutorial.chapter6Advanced

fun main() {

    val tomatoBasket = VegetableBasket<Tomato>()
    val vegetableBasket = VegetableBasket<Vegetable>()

    val plants = ArrayList<Plant>()
    val vegtables = ArrayList<Vegetable>()
    val tomatoes = ArrayList<Tomato>()

    // Can use any class that super of T which is Tomato
    tomatoBasket.doSomething(plants)
    tomatoBasket.doSomething(vegtables)
    tomatoBasket.doSomething(tomatoes)

    vegetableBasket.doSomething(plants)
    vegetableBasket.doSomething(vegtables)

    // ❌ COMPILE ERROR Tomato is sub-type of Vegetable, it can accept Vegetable or super types
//    vegetableBasket.doSomething(tomatoes)


}

interface Producer<out T> {
    fun produce(): T
}

interface Consumer<in T> {
    fun consume(t: T)
}

open class Plant
open class Vegetable : Plant()
class Tomato : Vegetable()
class Cucumber : Vegetable()

class VegetableBasket<T> {

    fun doSomething(list: ArrayList<in T>) {
        list.forEach {
            println("VegetableBasket element: $it")
        }
    }
}


class InOutTestClass<in I, out O> {

    fun foo1(i: I) {

    }

    // ❌ COMPILE ERROR Type parameter I is declared as 'in' but occurs in 'out' position in type I
//    fun foo2():I{}

    // ❌ COMPILE ERROR Type parameter O is declared as 'out' but occurs in 'in' position in type O
//    fun foo3(o:O) = {}

    inline fun <reified O> foo4(): O {
        return O::class.java.newInstance()
    }


}