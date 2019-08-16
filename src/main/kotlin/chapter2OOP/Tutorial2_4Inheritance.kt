package chapter2OOP

fun main(args: Array<String>) {

//    val derived = Derived("Foo", "Bar")

    // Class with interface and base class
//    val c = C()
//    // INFO 🔥 f() method calls super method of Class A and Interface B
//    c.f()


}


sealed class VehicleType {
    data class CarType(val type: Int) : VehicleType()
    data class BusType(val type: Int) : VehicleType()
}

open class Vehicle(var type: VehicleType, var manufacturer: String) {

}


// TODO 🔥 ??? 'manufacturer' hides member of supertype  and needs 'override' modifier
//class Bus(var manufacturer: String) : Vehicle(VehicleType.BusType(2), manufacturer) {
//
//}