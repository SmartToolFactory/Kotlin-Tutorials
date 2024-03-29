package com.smarttoolfactory.tutorial.chapter2OOP

import com.smarttoolfactory.tutorial.chapter2OOP.model.BusinessAccount
import com.smarttoolfactory.tutorial.chapter2OOP.model.PrivateAccount
import com.smarttoolfactory.tutorial.chapter2OOP.model.Snake
import com.smarttoolfactory.tutorial.chapter2OOP.model.UnionAccount

fun main() {

//    val privateAccount = PrivateAccount(22.5)
//    println("Private  baseAmount: ${privateAccount.baseAmount} ")
//    privateAccount.baseAmount = 130.0
//    println("Private AFTER baseAmount: ${privateAccount.baseAmount} ")
//    privateAccount.displayValue()
//
//    val businessAccount = BusinessAccount(50.0)
//    println("Business baseAmount: ${businessAccount.baseAmount} ")
//    businessAccount.baseAmount = 130.0
//    println("Business AFTER baseAmount: ${businessAccount.baseAmount} ")
//    businessAccount.displayValue()

    val unionAccount = UnionAccount(61.8)
    unionAccount.setBase(12.54)
    unionAccount.displayValue()

    unionAccount.unionProperty = "Test"

    println("Union property: ${unionAccount.unionProperty}")

    /*
        Private  baseAmount: 22.5
        Private AFTER baseAmount: 130.0
        PrivateAccount Parent super.baseAmount: 130.0, derived: 130.0

        Business baseAmount: 0.0
        Business AFTER baseAmount: 390.0
        BusinessAccount Parent super.baseAmount: 50.0, derived: 390.0

        UnionAccount Parent super.baseAmount: 61.8, derived: 12.54
        Union property: Test 12.54
     */


//    val snake = Snake()
//    println("Snake Sound: ${snake.makeSound()}")
//    println("Snake Move: ${snake.doMove()}")
//    println("Snake Max Age: ${snake.MAX_AGE}")
}