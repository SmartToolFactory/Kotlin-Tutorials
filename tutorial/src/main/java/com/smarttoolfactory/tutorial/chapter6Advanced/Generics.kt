package com.smarttoolfactory.tutorial.chapter6Advanced

class Entity(val id: Int)

interface DataSource {

}

// T: Entity is same as T extends Entity in java


class Repository<T : Entity> {


    fun save(entity: T) {
        if (entity.id != null) {
            println("id ${entity.id}")
        }
    }


}

data class Address(val street: String, val town: String, val country: String)

class BankCustomer(val id: Int, val name: String, val address: Address) {


    fun withdraw(amount: Double) {

    }
}

data class BankAccount(val id: Int, var amount: Double, val customer: BankCustomer) {


}


fun getBankAccount(id: Int, action: BankAccount.() -> BankAccount): BankAccount {

    val address = Address("Custom Str", "London", "UK")
    val customer = BankCustomer(id, "John", address)

    val bankAccount = BankAccount(id, 100.0, customer)


    return bankAccount.action()

}

fun main() {




}
