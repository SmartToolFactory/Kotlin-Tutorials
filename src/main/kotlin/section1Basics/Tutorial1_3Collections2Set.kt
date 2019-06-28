package section1Basics

fun main(args: Array<String>) {

    /*
     ***** SETs *****
    */

    /*
     * INFO Instantiation
     */
    // Immutable(Read-Only)
    val setImmutable1 = setOf(3, 5, 1)          // [3, 5, 1]

    // Mutable
    val setHash = hashSetOf(3, 5, 1)      // [5, 1, 3]
    val setLinkedHash = linkedSetOf(3, 5, 1)    // [3, 5, 1]
    val setTree = sortedSetOf(3, 5, 1)    // [1, 3, 5]
    val setMutable = mutableSetOf(3, 5, 1)   // [3, 5, 1]

    setImmutable1.forEach { it -> println("setImmutable1 Item $it") }
    setHash.forEach { it -> println("setHash Item $it") }
    setLinkedHash.forEach { it -> println("setLinkedHash Item $it") }
    setTree.forEach { it -> println("setTree Item $it") }

    /*
     * INFO Immutable(Read-Only) Collections
     */


    // 🔥 WARNING


}