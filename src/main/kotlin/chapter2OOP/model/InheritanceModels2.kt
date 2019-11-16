package chapter2OOP.model


abstract class Account(initialAmount: Double) {
    open var baseAmount = initialAmount
}

class PrivateAccount(initial: Double) : Account(initial) {
    fun displayValue() {
        println("PrivateAccount Parent super.baseAmount: ${super.baseAmount}, derived: $baseAmount")
    }
}

class BusinessAccount(base: Double) : Account(base) {

    // Overriding a value returns a different value than parent has
    override var baseAmount: Double = 0.0
        set(value) {
            field = value * 3
        }


    fun displayValue() {
        println("BusinessAccount Parent super.baseAmount: ${super.baseAmount}, derived: $baseAmount")
    }
}

// Overriding a value returns a different value than parent has
class UnionAccount(override var baseAmount: Double) : Account(baseAmount) {

    fun setBase(amount: Double) {
        baseAmount = amount
    }

    fun displayValue() {
        println("UnionAccount Parent super.baseAmount: ${super.baseAmount}, derived: $baseAmount")
    }
}