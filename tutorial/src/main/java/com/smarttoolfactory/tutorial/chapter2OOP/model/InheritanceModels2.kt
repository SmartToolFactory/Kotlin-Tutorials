package com.smarttoolfactory.tutorial.chapter2OOP.model

fun main() {
    val employee = Employee()
    println(employee.baseSalary) // 30000.0

    val programmer = Programmer()
    println(programmer.baseSalary) // 50000.0
}

// 🔥 INFO Overriding Properties

abstract class Account(initialAmount: Double) {
    open var baseAmount = initialAmount
}

class PrivateAccount(initial: Double) : Account(initial) {
    fun displayValue() {
        println("PrivateAccount Parent super.baseAmount: ${super.baseAmount}, derived: $baseAmount")
    }
}

class BusinessAccount(base: Double) : Account(base) {

    //🔥 INFO When you override a property or a member function of a super class,
    // the super class implementation
    //    is shadowed by the child class implementation.
    //    You can access the properties and functions of the super class using super() keyword.

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

    var unionProperty: String = ""
        set(value) {
            field = "$value $baseAmount"
        }

    fun displayValue() {
        println("UnionAccount Parent super.baseAmount: ${super.baseAmount}, derived: $baseAmount")
    }
}

/*

public final class UnionAccount extends Account {
   @NotNull
   private String unionProperty;
   private double baseAmount;

   public final void setBase(double amount) {
      this.setBaseAmount(amount);
   }

   @NotNull
   public final String getUnionProperty() {
      return this.unionProperty;
   }

   public final void setUnionProperty(@NotNull String value) {
      this.unionProperty = value + ' ' + this.getBaseAmount();
   }

   public final void displayValue() {
      String var1 = "UnionAccount Parent super.baseAmount: " + super.getBaseAmount() + ", derived: " + this.getBaseAmount();
      System.out.println(var1);
   }

   public double getBaseAmount() {
      return this.baseAmount;
   }

   public void setBaseAmount(double var1) {
      this.baseAmount = var1;
   }

   public UnionAccount(double baseAmount) {
      super(baseAmount);
      this.baseAmount = baseAmount;
      this.unionProperty = "";
   }
}

 */

open class Employee {
    // Use "open" modifier to allow child classes to override this property
    open val baseSalary: Double = 30000.0
}

class Programmer : Employee() {
    // Use "override" modifier to override the property of base class
    override val baseSalary: Double = 50000.0
}

interface AnimalBase {
    // 🔥 Implementing class MUST override this
    var MAX_AGE: Int

    fun makeSound(): String

    fun doMove(): String
}

class Snake : AnimalBase {

    // Has to be overriden
    override var MAX_AGE = 7

    override fun doMove() = "Slithers"

    override fun makeSound() = "Hisses"
}