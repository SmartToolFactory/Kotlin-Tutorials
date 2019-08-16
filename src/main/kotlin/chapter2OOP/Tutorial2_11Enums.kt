package chapter2OOP

fun main() {
    val direction = Direction.NORTH

    val waiting = ProtocolState.WAITING


    val printerType = EnumPrinterType.DOTMATRIX

    printerType.pageAmount = 10


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
        override fun signal() = WAITING
    };

    abstract fun signal(): ProtocolState
}

// 🔥 INFO Working with Enum Constants


// 🔥 Enum constructors are private, enums can npt be instantiated
enum class EnumPrinterType(var pageAmount: Int, internal var price: String) {

    DOTMATRIX(3, "cheap"), INKJET(5, "expensive"), LASER(7, "very expensive")

}