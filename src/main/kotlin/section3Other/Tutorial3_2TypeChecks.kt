package section3Other

fun main(args: Array<String>) {

// Info Cast
    var obj: String = "";
    if (obj is String) {
        print(obj.length)
    }
    if (obj !is String) { // same as !(obj is String) print("Not a String")
    } else {
        println(obj.length)
    }

    // INFO "Unsafe" cast operator
//    val y: Any? = null
//    val x: String = y as String
//    println(x) // INFO Throw TypeCastException

    // INFO "Safe" (nullable) cast operator
    val a:Any? = null
    val b:String? = a as? String
    println("a: $a") // prints a: null

}


// INFO Smart Casts
fun demo(x: Any) {
    if (x is String) {
        print(x.length) // x is automatically cast to String }
    }



}

fun testCast(x: Any) {
    when (x) {
        is Int -> print(x + 1)
        is String -> print(x.length + 1)
        is IntArray -> print(x.sum())
    }
}