package chapter2OOP

fun main() {

//    val derived = Derived("Foo", "Bar")

    // Class with interface and base class
//    val c = C()
//    // INFO 🔥 f() method calls super method of Class BaseClassA and Interface InterfaceB
//    c.f()

    val sportsCar = SportsCar()

    println("SportsCar type: ${sportsCar.type}, manufacturer: ${sportsCar.manufacturer}")
}


sealed class VehicleType {
    data class CarType(val type: Int) : VehicleType()
    data class BusType(val type: Int) : VehicleType()
}

open class Vehicle(var type: VehicleType, var manufacturer: String) {

}

class SportsCar: Vehicle(VehicleType.CarType(1), "Tesla")


// TODO 🔥 ??? 'manufacturer' hides member of supertype  and needs 'override' modifier
//class Bus(var manufacturer: String) : Vehicle(VehicleType.BusType(2), manufacturer) {
//
//}