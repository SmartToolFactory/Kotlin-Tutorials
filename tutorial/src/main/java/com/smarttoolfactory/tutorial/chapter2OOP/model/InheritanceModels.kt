package com.smarttoolfactory.tutorial.chapter2OOP.model


open class Base(val name: String) {

    init {
        // INFO called 2nd
        println("Initializing Base")
    }

    open val size: Int =
        // INFO called 3rd
        name.length.also { println("Initializing size in Base: $it") }
}

class Derived(
    name: String,
    val lastName: String
)
// INFO called 1st
    : Base(name.capitalize().also { println("Argument for Base: $it") }) {

    init {
        // INFO called 4th
        println("Initializing Derived")
    }

    override val size: Int =
        // INFO called 5th
        (super.size + lastName.length).also { println("Initializing size in Derived:$it") }
}


open class BaseClassA {

    open fun f() {
        print("BaseClassA")
    }

    fun a() {
        print("a")
    }
}

interface InterfaceB {

    var name: String

    // interface members are 'open' by default
    fun f() {
        print("InterfaceB")
    }

    fun b() {
        print("b")
    }

    // INFO f(), and b() methods are not ABSTRACT but foo is abstract and must be
    // overridden on class that implement InterfaceB interface
    fun foo()
}

/*
public interface InterfaceB {
   @NotNull
   String getName();

   void f();

   void b();

   void foo();

    // 🔥 This class gets generated because of methods with body in Interface in Java
   public static final class DefaultImpls {
      public static void f(@NotNull InterfaceB $this) {
         String var1 = "InterfaceB";
         boolean var2 = false;
         System.out.print(var1);
      }

      public static void b(@NotNull InterfaceB $this) {
         String var1 = "b";
         boolean var2 = false;
         System.out.print(var1);
      }
   }
}
 */

class C() : BaseClassA(), InterfaceB {

    override fun foo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var className = ""

    override var name: String
        get() = className
        set(value) {
            className = value
        }

    // INFO only invokes methods called with super
    // 🔥 The compiler requires f() to be overridden because it's open fun in BaseClassA
    override fun f() {
        super<BaseClassA>.f() // call to BaseClassA.f()
        super<InterfaceB>.f() // call to InterfaceB.f() }
    }

}

class InterfaceTestClass(override var name: String) : InterfaceB {

    // 🔥 This should be implemented because it has no body in Interface
    override fun foo() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

/*
public final class InterfaceTestClass implements InterfaceB {
   @NotNull
   private String name;

      public InterfaceTestClass(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      super();
      this.name = name;
   }


   public void foo() {
      String var1 = "not implemented";
      boolean var2 = false;
      throw (Throwable)(new NotImplementedError("An operation is not implemented: " + var1));
   }

   @NotNull
   public String getName() {
      return this.name;
   }

   public void setName(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.name = var1;
   }

   public void f() {
      InterfaceB.DefaultImpls.f(this);
   }

   public void b() {
      InterfaceB.DefaultImpls.b(this);
   }
}
 */


// INFO Super with Constructor
// Parent class
open class Computer(
    val name: String,
    val brand: String
) {

    open var age: Double = 0.0

    open fun start() {

    }
}

// Child class (initializes the parent class)
class Laptop : Computer {

    override var age: Double = 0.0
        get() = super.age
        set(value) {
            field = value + 2
        }


    val batteryLife: Double

    // Calls super() to initialize the Parent class
    constructor(name: String, brand: String, batteryLife: Double) : super(name, brand) {
        this.batteryLife = batteryLife
    }

    // Calls another constructor (which calls super())
    constructor(name: String, brand: String) : this(name, brand, 0.0) {

    }
}
