package chapter2OOP

fun main() {
    val direction = Direction.NORTH

    val waiting = ProtocolState.WAITING
   println("WAITING signal(): ${ waiting.signal()}")

    var state = ProtocolState.WAITING
    repeat(4) {
        state = state.signal()

        println("state: $state")
    }

    val redColor = Color.RED

    println("Color: $redColor, rgb: ${redColor.rgb}")

    val printerType = EnumPrinterType.DOTMATRIX

    println("printerType: $printerType, pageAmount: ${printerType.pageAmount}, price: ${printerType.price}")


}

// 🔥 INFO Enum Classes
enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

// 🔥 INFO Initialization
enum class Color(val rgb: Int) {

    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)

}

// 🔥 INFO Anonymous Classes
enum class ProtocolState {

    WAITING {
        override fun signal() = TALKING
    },

    TALKING {
        override fun signal() = WALKING
    },

    WALKING {
        override fun signal() = WAITING
    };

    abstract fun signal(): ProtocolState
}

// 🔥 INFO Working with Enum Constants


// 🔥 Enum constructors are private, enums can not be instantiated
enum class EnumPrinterType(var pageAmount: Int, internal var price: String) {

    DOTMATRIX(3, "cheap"), INKJET(5, "expensive"), LASER(7, "very expensive")

}

