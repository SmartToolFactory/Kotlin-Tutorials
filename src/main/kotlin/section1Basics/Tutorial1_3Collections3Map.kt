package section1Basics

fun main(args: Array<String>) {

    /*
      ***** MAPs *****
     */

    /*
    * INFO Instantiation
    */
    val map1 = mapOf("b" to 2, "a" to 1)          // {b=2, a=1}
    val mapHash = hashMapOf("b" to 2, "a" to 1)      // {a=1, b=2}
    val mapLinked = linkedMapOf("b" to 2, "a" to 1)    // {b=2, a=1}
    val mapSorted = sortedMapOf("b" to 2, "a" to 1)    // {a=1, b=2}
    val mapMutable = mutableMapOf("b" to 2, "a" to 1)   // {b=2, a=1}


    val keys: MutableSet<String> = mapMutable.keys
    val values: MutableCollection<Int> = mapMutable.values
    val entries: MutableSet<MutableMap.MutableEntry<String, Int>> = mapMutable.entries
    val iterator = mapMutable.iterator();

    mapMutable.put("c",3)

    // Iterator contains Entry<K,V>, and gets iterator.hasNext() and iterates to next item if available

    /*
    * INFO Looping
    */

    // INFO Method1: Key & Values

    for (k in keys) {
        println("Map Method1a: K: $k")
    }

    for (v in values) {
        println("Map Method1b: V: $v")
    }

    for ((key, value) in mapMutable) {
        println("Map Method1c: K: $key, V: $value")
    }
    println("****************************")

    // INFO Method2: EntrySet
    entries.forEach { (k, v) -> println("Map Method2: $k, V: $v") }
    println("****************************")

    // INFO Method3: Iterator & Entry
    while (iterator.hasNext()) {
        val entry = iterator.next()
        println("Map Method3: K: ${entry.key}, V: ${entry.value}")
    }
    println("****************************")

    // INFO Method4: Loop iterator with foreach method
    iterator.forEach { println("Map Method4 K: ${it.key}, V: ${it.value}") }
    println("****************************")

    // INFO Method5: Looping Keys and Values directly
    mapMutable.forEach { (k, v) -> println("Map Method5 K: $k , V: $v") }
    println("****************************")

    /*
     * INFO Immutable(Read-Only) Maps
     */


    // 🔥 WARNING


}