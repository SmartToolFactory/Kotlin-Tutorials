package chapter6Advanced

class Reified {

    fun main() {

        val result = getClassOf<String>()

        val str = result.newInstance()

        println(str)

        val publicAccount = getAccountClass<PrivateAccount>().newInstance()
        val publicAccountBalance = publicAccount.getBalance()
        println("Public Balance $publicAccountBalance")

        val privateAccount = getAccountInstance<PrivateAccount>()
        val privateAccountBalance = privateAccount.getBalance()
        println("Private Balance: $privateAccountBalance")


    }

    private inline fun <reified T> getClassOf(): Class<T> {
        return T::class.java
    }

    private inline fun <reified T : Account> getAccountClass(): Class<T> {
        return T::class.java
    }

    private inline fun <reified T : Account> getAccountInstance(): T {
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