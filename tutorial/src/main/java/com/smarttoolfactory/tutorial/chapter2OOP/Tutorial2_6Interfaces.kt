package com.smarttoolfactory.tutorial.chapter2OOP

fun main() {

    val child = Child()
    println("Child propertyWithImplementation ${child.propertyWithImplementation}")
    child.foo()
    child.prop = 2
    child.foo()

    /*
        Prints:
        Child propertyWithImplementation fooBar
        29
        2
     */
}

interface MyInterface {
    fun bar()
    fun foo() {
        // optional body
        println("MyInterface foo()")
    }
}

/*
public interface MyInterface {
   void bar();

   void foo();

   public static final class DefaultImpls {
      public static void foo(@NotNull MyInterface $this) {
         String var1 = "MyInterface foo()";
         boolean var2 = false;
         System.out.println(var1);
      }
   }
}
 */

interface MyInterface2 {

    var prop: Int // abstract


    // INFO 🔥⚠️ This is NOT allowed: Property initializers are not allowed in interfaces
//    val propertyWithImplementation: String = "fooBar"

    // INFO 🔥⚠️ This CANNOT be var -> Property in interface cannot have a backing field
    // Creates a static nested class with static methodDefaultImpls.getPropertyWithImplementation
    val propertyWithImplementation: String
        get() = "fooBar"

    fun foo() {
        println(prop)
    }

}

/*
public interface MyInterface2 {
   int getProp();

   void setProp(int var1);

   @NotNull
   String getPropertyWithImplementation();

   void foo();


   public static final class DefaultImpls {

      @NotNull
      public static String getPropertyWithImplementation(@NotNull MyInterface2 $this) {
         return "fooBar";
      }

      public static void foo(@NotNull MyInterface2 $this) {
         int var1 = $this.getProp();
         boolean var2 = false;
         System.out.println(var1);
      }
   }
}
 */

class Child : MyInterface2 {
    override var prop: Int = 29
}

interface  Named {
    val name:String
}

interface PersonNamed:Named {

    val firstName:String
    val secondName: String

    override val name: String
        get() = "$firstName, lastName: $secondName"

}