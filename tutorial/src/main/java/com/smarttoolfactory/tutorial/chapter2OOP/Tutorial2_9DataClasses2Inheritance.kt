package com.smarttoolfactory.tutorial.chapter2OOP

fun main() {

    /*
        Overloading method getName in Corn and Vegetable let's these object to call
        one for the static type(type on left side o the equation val milkCorn:Vegetable = Corn())

     */
    val apple = Apple(0, "🍏")
    println("Apple name: ${apple.name}")

    val corn: Corn = Corn(0, "🌽")
    println("Corn name: ${corn.name}")
    corn.name = "🌽🌽"
    corn.printName()

    val milkCorn: Vegetable = corn
    corn.id = 20
    println("MilkCorn name: ${milkCorn.name}")
    milkCorn.name = "🌽x3"
    val m = milkCorn as Corn
    println("MilkCorn Changed name: ${milkCorn.name}, m id: ${m.id}")
    corn.printName()


    /*
        Prints
        Apple name: 🍏
        Corn name: 🌽
        Name: 🌽🌽, super name: 🌽
        MilkCorn name: 🌽🌽
        MilkCorn Changed name: 🌽x3, m id: 20
        Name: 🌽x3, super name: 🌽
     */

    val vegetableList = listOf<Vegetable>(corn)

    vegetableList.forEach {
        print("Vegetable name: $it")
    }
    /*
        Prints:
        Vegetable name: Corn(id=20, name=🌽x3)

     */
}

open class Fruit {
    open var name: String = "fruit"
}

/*
public class Fruit {
   @NotNull
   private final String name = "fruit";

   @NotNull
   public String getName() {
      return this.name;
   }
}
 */

data class Apple(val id: Int, override var name: String) : Fruit() {

}
/*
public final class Apple extends Fruit {

   private final int id;
   @NotNull
   private String name;

   public Apple(int id, @NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      super();
      this.id = id;
      this.name = name;
   }

   public final int getId() {
      return this.id;
   }

   @NotNull
   public String getName() {
      return this.name;
   }

   public void setName(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.name = var1;
   }



   public final int component1() {
      return this.id;
   }

   @NotNull
   public final String component2() {
      return this.getName();
   }

}
 */

open class Vegetable(open var name: String)
/*
public class Vegetable {
   @NotNull
   private final String name;

   @NotNull
   public String getName() {
      return this.name;
   }

   public Vegetable(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      super();
      this.name = name;
   }
}
 */

class Bean(id: Int, name:String) : Vegetable(name) {

    fun printName() {
        println("Name: $name, super name: ${super.name}")
    }
}

/*
public final class Bean extends Vegetable {

   public Bean(int id, @NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      super(name);
   }

   public final void printName() {
      String var1 = "Name: " + this.getName() + ", super name: " + super.getName();
      boolean var2 = false;
      System.out.println(var1);
   }


}
 */

data class Corn(var id: Int, override var name: String) : Vegetable(name) {

    fun printName() {
        println("Name: $name, super name: ${super.name}")
    }

}

/*
public final class Corn extends Vegetable {
   private final int id;
   @NotNull
   private String name;

  public Corn(int id, @NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      super(name);
      this.id = id;
      this.name = name;
   }

   public final void printName() {
      String var1 = "Name: " + this.getName() + ", super name: " + super.getName();
      boolean var2 = false;
      System.out.println(var1);
   }

   public final int getId() {
      return this.id;
   }

   @NotNull
   public String getName() {
      return this.name;
   }

   public void setName(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.name = var1;
   }

   public final int component1() {
      return this.id;
   }

   @NotNull
   public final String component2() {
      return this.getName();
   }

   @NotNull
   public final Corn copy(int id, @NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      return new Corn(id, name);
   }

   // $FF: synthetic method
   public static Corn copy$default(Corn var0, int var1, String var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = var0.id;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.getName();
      }

      return var0.copy(var1, var2);
   }

   @NotNull
   public String toString() {
      return "Corn(id=" + this.id + ", name=" + this.getName() + ")";
   }

   public int hashCode() {
      int var10000 = this.id * 31;
      String var10001 = this.getName();
      return var10000 + (var10001 != null ? var10001.hashCode() : 0);
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof Corn) {
            Corn var2 = (Corn)var1;
            if (this.id == var2.id && Intrinsics.areEqual(this.getName(), var2.getName())) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
 */


abstract class Resource {
    abstract var id: Long
    abstract var location: String
}

data class Article(
    override var id: Long = 0,
    override var location: String = "",
    var isbn: String
) : Resource()