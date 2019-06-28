package section1Basics

import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {

    /*
     ***** LISTs *****
    */

    /*
     * INFO Instantiation
     */

    // INFO Lists
    // Immutable(Read-only)
    val listImmutable1 = listOf(1, 2, 3)
    // Mutable
    val listMutable1 = arrayListOf(1, 2, 3)
    val listMutable2 = mutableListOf("a", "b", "c")

    // INFO empty lists
    // Immutable(Read-only)
    val listEmptyImmutable1 = listOf<Int>()
    val listEmpty2Immutable: List<Int> = emptyList()
    // Mutable
    val listEmptyMutable1 = arrayListOf<Int>()
    val listEmptyMutable2 = mutableListOf<String>()

    // INFO nullability
    // Immutable(Read-only)
    val listNullableImmutable1 = listOf("a", null)                  // [a, null]
    // Mutable
    val listNullableMutable1 = arrayListOf("a", null)             // [a, null]
    val listNullableMutable2 = mutableListOf("a", null)           // [a, null]

    listNullableMutable2.forEach { element -> println("listNullable3:  $element") }

    // 🔥 INFO How to create ArrayList instances in Kotlin while explicitly assigning their types
//    val a: ArrayList<Int>          = arrayListOf(1,2,3)
//    val b: AbstractList<Int>       = arrayListOf(1,2,3)
//    val c: MutableList<Int>        = arrayListOf(1,2,3)
//    val d: AbstractCollection<Int> = arrayListOf(1,2,3)
//    val e: MutableCollection<Int>  = arrayListOf(1,2,3)
//    // Immutable(Read-only)
//    val f: Collection<Int>         = arrayListOf(1,2,3)
//    val g: MutableIterable<Int>    = arrayListOf(1,2,3)
//    // Immutable(Read-only)
//    val h: Iterable<Int>           = arrayListOf(1,2,3)
//    // Immutable(Read-only)
//    val i: List<Int>               = arrayListOf(1,2,3)

    // 🔥 WARNING This list cannot not contain NULL elements
    // Immutable(Read-only)
    val listNonNullable1: List<String> = listOfNotNull<String>("a", null)   // [a]
    val listNonNullable2 = listOfNotNull("a", null)           // [a]

    listNonNullable2.forEach { element -> println("listNonNullable2:  $element") }

    /*
     * INFO Immutable(Read-Only) Lists
     */
    // This list does not have remove and methods
    val daysOfWeek: List<String> = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    println("daysOfWeek item get 3rd: ${daysOfWeek.get(2)}")


    // 🔥 WARNING This is a MUTABLE list
    val numbers: MutableList<Int> = mutableListOf(1, 2, 3)
    // 🔥 WARNING This is an IMMUTABLE(Read-Only) list
    val readOnlyList: List<Int> = numbers

    println("readOnlyList item 1: " + readOnlyList[0])

    /*
     * INFO Mutable Lists
     */

    // WARNING arrayListOf() instantiates a mutable list
    val listMonths = arrayListOf<String>();

    listMonths.add("Jan")
    listMonths.add("Feb")
    listMonths.add("Mar")
    listMonths.add("Apr")

    listMonths.forEach { month -> println("Month $month") }
    listMonths.removeAt(listMonths.lastIndex)
    println("AFTER REMOVING LAST ITEM")
    listMonths.forEach { month -> println("Month $month") }

    // INFO Instantiate a mutable list in 3 different ways
//    val intMutableList: MutableList<Int> = mutableListOf<Int>()
//    val intMutableList: MutableList<Int> = mutableListOf()
    val intMutableList = mutableListOf<Int>()


    intMutableList.add(1)
    // Add item to an index
    intMutableList.add(1, 6)
    intMutableList.forEach { i -> println("Number: $i") }

    // This is a mutable list
    val numberList = ArrayList<Int>()
    numberList.add(1)
    numberList.add(2)
    numberList.add(3)
    numberList.add(4)
    numberList.add(5)

    // INFO LinkedList
    val myLinkedList: LinkedList<String> = LinkedList();
    myLinkedList.add("New text")

    /*
     *  INFO METHODS
     */

    val a = listOf(10, 20, 30, 40, 10)
    val names = listOf("joel", "ed", "chris", "maurice", "john", "rachel")

    a.any{it > 20}              //true
    a.contains(10)              //true
    a.count()                   //5
    a.count{it > 10}            //3
    a.distinct()                //[10, 20, 30, 40]
//    a.distinctBy()

    a.drop(1)                   //[20, 30, 40, 10]
    a.drop(2)                   //[30, 40, 10]
    a.dropLast(1)               //[10, 20, 30, 40]
    a.dropLast(2)               //[10, 20, 30]
    a.dropWhile{it < 30}        //[30, 40, 10]
    a.dropLastWhile{it != 30}   //[10, 20, 30]

    a.filter{it != 10}          //[20, 30, 40]
    a.find{it != 10}            //20
    a.first()                   //10
    a.first { s-> s>10 }
    a.firstOrNull()             //TODO
    a.fold(0){acc, x -> acc+x}  //110 (sum function)
    a.forEach{println(it)}      //prints out the list values

    a.getOrElse(0){0}           //10
    a.getOrElse(1){0}           //20
    a.getOrElse(11){0}          //0
    // TODO: better groupBy
    a.groupBy({it}, {it+1})     //{10=[11, 11], 20=[21], 30=[31], 40=[41]}
    a.indexOf(10)               //0
    a.indexOf(30)               //2
//    a.indexOfFirst()
//    a.indexOfLast()

    a.intersect(numbers)
    a.isEmpty()                 //false
    a.isNotEmpty()              //true
    a.last()                    //10
    names.last{it.length < 4}   //ed
    a.lastOrNull()              //10

    a.map{it + 1}               //[11, 21, 31, 41, 11]
    a.map{it * 2}               //[20, 40, 60, 80, 20]
    a.max()                     //40
    a.maxBy{it + 3}             //40
  //  maxWith                     //TODO
    a.min()                     //10
    a.minBy{it + 3}             //10
  //  minWith                     //TODO
    a.onEach{println(it)}       //prints each element and returns
    //a copy of the list
    a.partition{it >10}         //([20, 30, 40], [10, 10])
    a.reduce{acc, x -> acc+x}   //110 (sum function)
    a.slice(0..2)               //[10, 20, 30]
    a.slice(1..2)               //[20, 30]
    a.sorted()                  //[10, 10, 20, 30, 40]
    a.sortedBy{it}              //[10, 10, 20, 30, 40]
    names.sortedBy{it.length}   //[ed, joel, chris, maurice]
    a.sum()                     //110
    a.sumBy{it + 1}             //115

    a.take(1)                   //[10]
    a.take(2)                   //[10, 20]
    a.takeLast(1)               //[10]
    a.takeLast(2)               //[40, 10]
    a.takeLastWhile{it < 40}    //[10]
    a.takeWhile{it < 40}        //[10, 20, 30]
    a.union(names)              //[10, 20, 30, 40, joel, ed, chris, maurice]
    a.zip(names)                //[(10, joel), (20, ed), (30, chris), (40, maurice)]
    names.zip(a)                //[(joel, 10), (ed, 20), (chris, 30), (maurice, 40)]

    val groupedMap =  names.groupBy {  it.length }
    groupedMap.forEach { (k, v) -> println("key: $k, value $v") }

}