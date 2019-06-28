package section4Functions

fun main(args: Array<String>) {

    val result = highOrder(2, 3) { x, y ->
        x + y
    }


    println("Result $result")
}


fun doSomething(elements: List<String>): List<Pair<String, Int>> {

    return elements.groupBy {
        it
    }.map {
        Pair(it.key, it.value.count())
    }
}

fun highOrder(x: Int, y: Int, action: (Int, Int) -> Int): Int {
   return action(x, y)
}