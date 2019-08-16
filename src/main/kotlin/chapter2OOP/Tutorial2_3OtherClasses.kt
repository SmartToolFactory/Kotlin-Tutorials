package chapter2OOP

fun main(args: Array<String>) {

    // Companion Object
    val car = Car.makeCar(150)
    println(Car.Factory.cars.size)

    // Data class object
    val customer = Customer(1, "John", "Elm Street")

    var shape = Rectangle()


}

// INFO Companion Object Class
class Car(val horsepowers: Int){


    companion object Factory {
        val cars = mutableListOf<Car>()

        fun makeCar(horsepowers: Int): Car {
            val car = Car(horsepowers)
            cars.add(car)
            return car
        }
    }
}


// INFO Data Class
/*
 * 🔥
 */
data class Customer(val id: Int, val name: String, var address: String)


// INFO Enum Classes
enum class Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
    SATURDAY, SUNDAY
}

public enum class Planet(val mass: Double, val radius: Double) {
    MERCURY(3.303e+23, 2.4397e6),
    VENUS(4.869e+24, 6.0518e6),
    EARTH(5.976e+24, 6.37814e6),
    MARS(6.421e+23, 3.3972e6),
    JUPITER(1.9e+27, 7.1492e7),
    SATURN(5.688e+26, 6.0268e7),
    URANUS(8.686e+25, 2.5559e7),
    NEPTUNE(1.024e+26, 2.4746e7);

}

// INFO Abstract Class

abstract class Shape protected constructor() {

    constructor(x: Int, Y: Int) : this()

    var XLocation: Int
        get() = this.XLocation
        set(value: Int) {
            this.XLocation = value
        }

    var YLocation: Int
        get() = this.XLocation
        set(value: Int) {
            this.XLocation = value
        }

    var Width: Double
        get() = this.Width
        set(value: Double) {
            this.Width = value
        }

    var Height: Double
        get() = this.Height
        set(value: Double) {
            this.Height = value
        }

    abstract fun isHit(x: Int, y: Int): Boolean

}

class Ellipsis : Shape() {

    override fun isHit(x: Int, y: Int): Boolean {

        val xRadius = Width.toDouble() / 2
        val yRadius = Height.toDouble() / 2
        val centerX = XLocation + xRadius
        val centerY = YLocation + yRadius

        if (xRadius == 0.0 || yRadius == 0.0)
            return false

        val normalizedX = centerX - XLocation
        val normalizedY = centerY - YLocation
        return (normalizedX * normalizedX) / (xRadius * xRadius) +
                (normalizedY * normalizedY) / (yRadius * yRadius) <= 1.0
    }
}

class Rectangle : Shape() {

    override fun isHit(x: Int, y: Int): Boolean {
        return x >= XLocation && x <= (XLocation + Width) && y >=
                YLocation && y <= (YLocation + Height)
    }

}
