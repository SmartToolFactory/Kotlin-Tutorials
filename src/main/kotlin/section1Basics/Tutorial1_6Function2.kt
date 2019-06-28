package section1Basics


fun main(args: Array<String>) {


    val myLambda: (String) -> Unit = { s -> println(s) }
    val v: String = "Tutorials"
    myLambda(v)

    val myFun = bar()
    println(myFun("Hello world"))


    val isEven = modulo(2)
    val myList = arrayListOf(1, 2, 3)
    val result = myList.filter(isEven)

    val myFunction = createFunction(13)
    val test = myFunction()
    println("Test $test")

    val totalSum = sum(3, { 4 })
    println("Sum $totalSum")

    val filter: (Int) -> Boolean = { it > 2 }
}


fun bar(): (String) -> String = { str -> str.reversed() }

fun modulo(k: Int): (Int) -> Boolean = { it % k == 0 }

fun createFunction(arg: Int): () -> Int {
    val message = "I was created by CreateFunction()";
    return { arg }
}

private fun sum(a: Int, testFun: () -> Int): Int {
    return a + testFun()
}

