package com.smarttoolfactory.tutorial.chapter2OOP.model

class MyClass {

    // property (data member)
    private var name: String = "Tutorials.point"

    // member function
    fun printMe() {
        println("You are at the best Learning website Named-" + name)
    }
}

class MyObject(name: String) {

    val objName = name

    init {
        println("Name: $name")
    }
}
/*
public final class MyObject {
   @NotNull
   private final String objName;

   @NotNull
   public final String getObjName() {
      return this.objName;
   }

   public MyObject(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      super();
      this.objName = name;
      String var2 = "Name: " + name;
      boolean var3 = false;
      System.out.println(var2);
   }
}
 */


// INFO CONSTRUCTOR
// age parameter can be NULL
// 🔥 INFO If the primary constructor does not have any annotations or visibility modifiers,
// the constructor keyword can be omitted:
//🔥 INFO If the constructor has annotations or visibility modifiers,
// the constructor keyword is required, and the modifiers go before it:
class Person constructor(var firstName: String, val lastName: String, val age: Int?) {
    //...
}

// INFO INIT BLOCK

// INFO During an instance initialization,
// the initializer blocks are executed in the 🔥 SAME ORDER as they appear in the class body,
// interleaved with the property initializers:

class InitOrderDemo(name: String) {

    // 1st
    val firstProperty = "First property: $name".also(::println)

    // 2nd
    init {
        println("First initializer block that prints ${name}")
    }

    // 3rd
    val secondProperty = "Second property: ${name.length}".also(::println)

    // 4th
    init {
        println("Second initializer block that prints ${name.length}")
    }
}


class MYNewClass(private val name: String)

/*
public final class MYNewClass {
   private final String name;

   public MYNewClass(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      super();
      this.name = name;
   }
}
 */


// INFO 🔥 Assign Constructor parameters to class fields
// INFO 🔥 ⚠️ ️Prefixing your constructor arguments with val or var is not a must
// if you don't want the getter (or setter if you use var) to be generated, you can always do the following:

class Person2(firstName: String, lastName: String, howOld: Int?) {

    private val name: String
    private val age: Int?

    init {
        // IMPORTANT 🔥🔥🔥 Properties must be initialized in constructor(this could also be inside init{}),
        // or be abstract or lateinit
        this.name = "$firstName,$lastName"
        this.age = howOld
    }

    fun getName(): String = this.name
    fun getAge(): Int? = this.age
}

class Customer(name: String) {
    val customerKey = name.uppercase()
}

// INFO 🔥 Constructors with val/var properties
class User(val id: Long, email: String) {

    val hasEmail = email.isNotBlank()    //email can be accessed here

    init {
        //email can be accessed here
        println("Email $email")
    }

    fun getEmail() {
        // 🔥 email can't be accessed here
    }
}

// INFO SECONDARY CONSTRUCTORS

class Person3 {

    // INFO Secondary Constructor
    constructor(firstName: String, lastName: String) {
        println("😳 Secondary constructor of Person3")
    }
}

class Person4 constructor(val firstName: String, val lastName: String, val age: Int?) {

    // INFO 🔥 Secondary Constructor that calls Primary Constructor
    constructor(firstName: String, lastName: String) : this(firstName, lastName, null) {
        println("😳 Secondary constructor of Person4")
    }
}

/*

public final class Person4 {

    @NotNull
    private final String firstName;
    @NotNull
    private final String lastName;
    @Nullable
    private final Integer age;

    @NotNull
    public final String getFirstName() {
        return this.firstName;
    }

    @NotNull
    public final String getLastName() {
        return this.lastName;
    }

    @Nullable
    public final Integer getAge() {
        return this.age;
    }

    // 🔥 Primary Constructor
    public Person4(@NotNull String firstName, @NotNull String lastName, @Nullable Integer age) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    // 🔥 Secondary Constructor
    public Person4(@NotNull String firstName, @NotNull String lastName) {
        this(firstName, lastName, (Integer)null);
        String var3 = "\ud83d\ude33 Secondary constructor of Person4";
        System.out.println(var3);
    }
}

 */

class Constructors {

    constructor(i: Int) {
        println("😎 Secondary Constructor of Constructors class")
    }

    // INFO 🔥 init block is called before secondary constructor
    init {
        println("Init block of Constructors class")
    }

}

class Auto(age: Int) {


    init {
        println("🥳 Init block of Auto class with $age")
    }

    constructor(name: String) : this(0) {
        println("🚗🚗 Secondary Constructor of Auto with type $name")
    }

    constructor(i: Int, name: String) : this(i) {
        println("🚙 Secondary Constructor of Auto class with type $name and i $i")
    }
}

class SomeObject {

    constructor(age: Int)

    constructor(age: Int, name: String):this(age)

}
/*

public final class Auto {

   public Auto(int age) {
      String var2 = "\ud83e\udd73 Init block of Auto class with " + age;
      System.out.println(var2);
   }

   public Auto(@NotNull String name) {
      this(0);
      String var2 = "\ud83d\ude97\ud83d\ude97 Secondary Constructor of Auto with type " + name;
      System.out.println(var2);
   }

   public Auto(int i, @NotNull String name) {
      this(i);
      String var3 = "\ud83d\ude99 Secondary Constructor of Auto class with type " + name + " and i " + i;
      System.out.println(var3);
   }
}

 */