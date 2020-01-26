package chapter2OOP

import java.lang.Exception
import java.lang.IllegalArgumentException

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

open class Vehicle(var type: VehicleType, var manufacturer: String) {

}

class SportsCar : Vehicle(VehicleType.CarType(1), "Tesla")


// TODO 🔥 ??? 'manufacturer' hides member of supertype  and needs 'override' modifier
//class Bus(var manufacturer: String) : Vehicle(VehicleType.BusType(2), manufacturer) {
//
//}