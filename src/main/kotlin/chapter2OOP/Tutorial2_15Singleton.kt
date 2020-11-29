package chapter2OOP


fun main() {

    val firstSingleton = Singleton
    firstSingleton.printName()
    val secondSingleton = Singleton
    secondSingleton.name = "Hello World"
    firstSingleton.printName()

    println(
        "firstSingleton: $firstSingleton, secondSingleton: $secondSingleton," +
                " firstSingleton == secondSingleton ${firstSingleton == secondSingleton}"
    )

    /*
        Prints:

        Singleton class invoked.
        Kotlin Objects
        Hello World
        firstSingleton: chapter2OOP.Singleton@1d44bcfa, secondSingleton: chapter2OOP.Singleton@1d44bcfa, firstSingleton == secondSingleton true
     */

    val mySingleton1 = SingletonWithCompanion.createRandomClazz()
    val mySingleton2 = SingletonWithCompanion.createRandomClazz()

    println(
        "firstSingleton: $mySingleton1, secondSingleton: $mySingleton2," +
                " firstSingleton == secondSingleton  ${mySingleton1 == mySingleton2}"
    )
    /*
        Prints:

        firstSingleton: chapter2OOP.RandomClass@7852e922, secondSingleton: chapter2OOP.RandomClass@4e25154f, firstSingleton == secondSingleton  false
     */

}

object Singleton {

    init {
        println("Singleton class invoked.")
    }

    var name = "Kotlin Objects"

    @JvmStatic
    fun printName() {
        println(name)
    }
}

/*
JAVA counterpart from decompiling

public final class Singleton {

   static {
      Singleton var0 = new Singleton();
      INSTANCE = var0;
      String var1 = "Singleton class invoked.";
      boolean var2 = false;
      System.out.println(var1);
      name = "Kotlin Objects";
   }

   @NotNull
   private static String name;
   public static final Singleton INSTANCE;

   @NotNull
   public final String getName() {
      return name;
   }

   public final void setName(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      name = var1;
   }

   @JvmStatic
   public static final void printName() {
      String var1 = name;
      boolean var2 = false;
      System.out.println(var1);
   }

   private Singleton() {
   }
}
 */

class SingletonWithCompanion {

    companion object Factory {


        /**
         * This method does not create Singleton objects
         */
        fun createRandomClazz(): RandomClass = RandomClass()
    }
}


/*
public final class SingletonWithCompanion {
   @NotNull
   public static final SingletonWithCompanion.Factory Factory = new SingletonWithCompanion.Factory((DefaultConstructorMarker)null);


   public static final class Factory {
      @NotNull
      public final RandomClass createRandomClazz() {
         return new RandomClass();
      }

      private Factory() {
      }

      // $FF: synthetic method
      public Factory(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}

 */

class RandomClass