package section3Other

fun main(args: Array<String>) {

    // INFO Destructuring Declarations
    val account = Account("Standard", 100)
    val (name: String, amount: Int) = account
    println("Account type: $name, amount: $amount")


    // Info Returning Two Values from a Function
    val (result, status) = function()


    val map = mapOf("key1" to 1, "key2" to 2)

    for ((key, value) in map) {
        // do something with the key and the value
    }

    // Info Underscore for unused variables (since 1.1)
    val (_:String, _:Int, interest:Float) = account

}


data class Account(var type: String, var amount: Int, var interest: Float = 5.7f)


// Info Returning Two Values from a Function
data class Result(val result: Int, val status: String)

fun function(): Result {

    val result = 10
    val status = "COMPLETED"

    return Result(result, status)
}
