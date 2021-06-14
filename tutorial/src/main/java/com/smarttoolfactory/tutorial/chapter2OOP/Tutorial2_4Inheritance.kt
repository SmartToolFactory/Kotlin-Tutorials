package com.smarttoolfactory.tutorial.chapter2OOP


fun main() {

//    val derived = Derived("Foo", "Bar")

    // Class with interface and base class
//    val c = C()
//    // INFO 🔥 f() method calls super method of Class BaseClassA and Interface InterfaceB
//    c.f()

    val sportsCar = SportsCar()

    println("SportsCar type: ${sportsCar.type}, manufacturer: ${sportsCar.manufacturer}")

    val resultSuccess = Result.Success<Int>(3)
    val resultError = Result.Error<Int>(IllegalArgumentException("Exception"))

    val resultBoxed = getResult("Hello World", 2)

    when (resultBoxed) {
        is Result.Success -> println("Result: ${resultBoxed.data}")
        is Result.Error -> println("Error: ${resultBoxed.exception.message}")
    }

    val bus = Bus("Ford")

    bus.printMaker()
    /*
        Prints Ford
     */

    println("Manufacturer: ${(bus as Vehicle).manufacturer}")
    /*
        Prints Ford
     */

}

fun <T> getResult(data: T, index: Int): Result<T> {
    return if (index < 0) {
        Result.Error<T>(IllegalAccessException("Number cannot be smaller than 0"))
    } else {
        Result.Success<T>(data)
    }
}


sealed class VehicleType {
    data class CarType(val type: Int) : VehicleType()
    data class BusType(val type: Int) : VehicleType()
}

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val exception: Exception) : Result<T>()
}

open class Vehicle(var type: VehicleType, open var manufacturer: String)

class SportsCar : Vehicle(VehicleType.CarType(1), "Tesla")

/**
 *
 */
class Bus(override var manufacturer: String) :
    Vehicle(VehicleType.BusType(2), "$manufacturer + Bus") {

    fun printMaker() {
        println("Maker: $manufacturer, super manufacturer: ${super.manufacturer}")
    }
}

/*
   @NotNull
   private String manufacturer;

   public final void printMaker() {
      String var1 = "Maker: " + this.getManufacturer();
      boolean var2 = false;
      System.out.println(var1);
   }

   @NotNull
   public String getManufacturer() {
      return this.manufacturer;
   }

   public void setManufacturer(@NotNull String var1) {
      this.manufacturer = var1;
   }

   public Bus(@NotNull String manufacturer) {
      super((VehicleType)(new VehicleType.BusType(2)), manufacturer + " + Bus");
      this.manufacturer = manufacturer;
   }
 */