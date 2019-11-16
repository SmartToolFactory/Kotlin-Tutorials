package chapter2OOP

import chapter2OOP.model.BusinessAccount
import chapter2OOP.model.PrivateAccount
import chapter2OOP.model.UnionAccount

fun main() {

    val privateAccount = PrivateAccount(22.5)
    println("Private  baseAmount: ${privateAccount.baseAmount} ")
    privateAccount.baseAmount = 130.0
    println("Private AFTER baseAmount: ${privateAccount.baseAmount} ")
    privateAccount.displayValue()

    val businessAccount = BusinessAccount(50.0)
    println("Business baseAmount: ${businessAccount.baseAmount} ")
    businessAccount.baseAmount = 130.0
    println("Business AFTER baseAmount: ${businessAccount.baseAmount} ")
    businessAccount.displayValue()

    val unionAccount = UnionAccount(61.8)
    unionAccount.setBase(12.54)
    unionAccount.displayValue()

    /*
        Private  baseAmount: 22.5
        Private AFTER baseAmount: 130.0
        PrivateAccount Parent super.baseAmount: 130.0, derived: 130.0

        Business baseAmount: 0.0
        Business AFTER baseAmount: 390.0
        BusinessAccount Parent super.baseAmount: 50.0, derived: 390.0

        UnionAccount Parent super.baseAmount: 61.8, derived: 12.54
     */

}