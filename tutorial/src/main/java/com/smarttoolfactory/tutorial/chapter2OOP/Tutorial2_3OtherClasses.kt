package com.smarttoolfactory.tutorial.chapter2OOP

fun main() {

    // Companion Object
    val car = Car.makeCar(150)
    println(Car.cars.size)

    // Data class object
    val customer = Customer(1, "John", "Elm Street")

    var shape = Rectangle(1, 3)


}

// INFO Companion Object Class
class Car(val horsepowers: Int) {


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

    // IMPORTANT 🔥🔥 val, and var are NOT ALLOWED with SECOND constructor
    constructor(x: Int, Y: Int) : this()

    abstract var xLocation: Int
    abstract var yLocation: Int

    var width: Double = 0.0
    var height: Double = 0.0

    abstract fun isHit(x: Int, y: Int): Boolean
}

/*
public abstract class Shape {
   private double width;
   private double height;

      protected Shape() {
   }

   public Shape(int x, int Y) {
      this();
   }

   public abstract int getXLocation();
   public abstract void setXLocation(int var1);
   public abstract int getYLocation();
   public abstract void setYLocation(int var1);

   public final double getWidth() {
      return this.width;
   }

   public final void setWidth(double var1) {
      this.width = var1;
   }

   public final double getHeight() {
      return this.height;
   }

   public final void setHeight(double var1) {
      this.height = var1;
   }

   public abstract boolean isHit(int var1, int var2);
}
 */

class Ellipsis(xBase: Int, yBase: Int) : Shape(xBase, yBase) {

    override var xLocation: Int = 0
    override var yLocation: Int = 0

    override fun isHit(x: Int, y: Int): Boolean {

        val xRadius = width / 2
        val yRadius = height / 2
        val centerX = xLocation + xRadius
        val centerY = yLocation + yRadius

        if (xRadius == 0.0 || yRadius == 0.0)
            return false

        val normalizedX = centerX - xLocation
        val normalizedY = centerY - yLocation
        return (normalizedX * normalizedX) / (xRadius * xRadius) +
                (normalizedY * normalizedY) / (yRadius * yRadius) <= 1.0
    }
}

/*
public final class Ellipsis extends Shape {
   private int xLocation;
   private int yLocation;

   public Ellipsis(int xBase, int yBase) {
      super(xBase, yBase);
   }

   public int getXLocation() {
      return this.xLocation;
   }

   public void setXLocation(int var1) {
      this.xLocation = var1;
   }

   public int getYLocation() {
      return this.yLocation;
   }

   public void setYLocation(int var1) {
      this.yLocation = var1;
   }

   public boolean isHit(int x, int y) {
      double xRadius = this.getWidth() / (double)2;
      double yRadius = this.getHeight() / (double)2;
      double centerX = (double)this.getXLocation() + xRadius;
      double centerY = (double)this.getYLocation() + yRadius;
      if (xRadius != 0.0D && yRadius != 0.0D) {
         double normalizedX = centerX - (double)this.getXLocation();
         double normalizedY = centerY - (double)this.getYLocation();
         return normalizedX * normalizedX / (xRadius * xRadius) + normalizedY * normalizedY / (yRadius * yRadius) <= 1.0D;
      } else {
         return false;
      }
   }
}
 */

class Rectangle(override var xLocation: Int, override var yLocation: Int) : Shape() {

    override fun isHit(x: Int, y: Int): Boolean {
        return x >= xLocation && x <= (xLocation + width) && y >=
                yLocation && y <= (yLocation + height)
    }
}

/*
public final class Rectangle extends Shape {
   private int xLocation;
   private int yLocation;

   public Rectangle(int xLocation, int yLocation) {
      this.xLocation = xLocation;
      this.yLocation = yLocation;
   }

   public boolean isHit(int x, int y) {
      return x >= this.getXLocation() && (double)x <= (double)this.getXLocation() + this.getWidth() && y >= this.getYLocation() && (double)y <= (double)this.getYLocation() + this.getHeight();
   }

   public int getXLocation() {
      return this.xLocation;
   }

   public void setXLocation(int var1) {
      this.xLocation = var1;
   }

   public int getYLocation() {
      return this.yLocation;
   }

   public void setYLocation(int var1) {
      this.yLocation = var1;
   }
}
 */
