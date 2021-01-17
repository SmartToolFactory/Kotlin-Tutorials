package chapter6Advanced

fun main() {

    val reified = Reified()

    val result = reified.getClassOf<String>()

    val str = result.newInstance()

    println(str)

    val publicAccount = reified.getAccountClass<PrivateAccount>().newInstance()
    val publicAccountBalance = publicAccount.getBalance()
    println("Public Balance $publicAccountBalance")

    val privateAccount = reified.getAccountInstance<PrivateAccount>()
    val privateAccountBalance = privateAccount.getBalance()
    println("Private Balance: $privateAccountBalance")


}

class Reified {

    internal inline fun <reified T> getClassOf(): Class<T> {
        return T::class.java
    }

    internal inline fun <reified T : Account> getAccountClass(): Class<T> {
        return T::class.java
    }

    internal inline fun <reified T : Account> getAccountInstance(): T {
        return T::class.java.newInstance()
    }
}

interface Account {
    fun getBalance(): Int
}

class PrivateAccount() : Account {

    override fun getBalance(): Int {
        return 3
    }
}

class PublicAccount() : Account {
    override fun getBalance(): Int {
        return 5
    }
}