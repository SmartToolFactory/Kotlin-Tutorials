package chapter6Advanced

import chapter2OOP.User
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


fun main() {

//    var delegatedNum1 by CalculateDelegate()
//    var delegatedNum2 by delegateCalculationFunction()
//
//    println("Initial delegatedNum1: $delegatedNum1, delegatedNum2: $delegatedNum2")
//    delegatedNum1 = 4
//    delegatedNum2 = 3
//    println("Final delegatedNum1: $delegatedNum1, delegatedNum2: $delegatedNum2")
    /*
        Prints:
        🔥 CalculateDelegate getValue() thisRef: null, property: var delegatedNum1: kotlin.Int
        🔥 CalculateDelegate getValue() thisRef: null, property: var delegatedNum2: kotlin.Int
        Initial delegatedNum1: 0, delegatedNum2: 0
        🤔 CalculateDelegate setValue() thisRef: null, property: var delegatedNum1: kotlin.Int, value: 4
        🤔 CalculateDelegate setValue() thisRef: null, property: var delegatedNum2: kotlin.Int, value: 3
        🔥 CalculateDelegate getValue() thisRef: null, property: var delegatedNum1: kotlin.Int
        🔥 CalculateDelegate getValue() thisRef: null, property: var delegatedNum2: kotlin.Int
        Final delegatedNum1: 8, delegatedNum2: 6
     */


    val owner = Owner()
    val newRes = Resource(5)

    println("Owner res: ${owner.varResource}")
    owner.varResource = newRes
    println("Owner res: ${owner.varResource}")

    /*
        Prints:
        🚀 ResourceDelegate getValue() thisRef: chapter6Advanced.Owner@4445629, property: var chapter6Advanced.Owner.varResource: chapter6Advanced.Resource
        Owner res: chapter6Advanced.Resource@1622f1b
        🍒 ResourceDelegate getValue() thisRef: chapter6Advanced.Owner@4445629, property: var chapter6Advanced.Owner.varResource: chapter6Advanced.Resource, value: chapter6Advanced.Resource@72a7c7e0
        🚀 ResourceDelegate getValue() thisRef: chapter6Advanced.Owner@4445629, property: var chapter6Advanced.Owner.varResource: chapter6Advanced.Resource
        Owner res: chapter6Advanced.Resource@72a7c7e0
     */

}

/**
 * Delegate [CalculateDelegate] to this function
 */
fun delegateCalculationFunction() = CalculateDelegate()

class CalculateDelegate {

    private var calculatedProperty = 0

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        println("🔥 CalculateDelegate getValue() thisRef: $thisRef, property: $property")
        return calculatedProperty
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        println("🤔 CalculateDelegate setValue() thisRef: $thisRef, property: $property, value: $value")
        calculatedProperty = value * 2
    }

}

/*
JAVA

   static final KProperty[] $$delegatedProperties =
    new KProperty[]{(KProperty)Reflection.mutableProperty0(new MutablePropertyReference0Impl(DelegatedPropertyKt.class, "delegatedNum1", "<v#0>", 1)), (KProperty)Reflection.mutableProperty0(new MutablePropertyReference0Impl(DelegatedPropertyKt.class, "delegatedNum2", "<v#1>", 1))};

   public static final void main() {
      CalculateDelegate var10000 = new CalculateDelegate();
      KProperty var1 = $$delegatedProperties[0];
      CalculateDelegate delegatedNum1 = var10000;
      var10000 = delegateCalculationFunction();
      KProperty var3 = $$delegatedProperties[1];
      CalculateDelegate delegatedNum2 = var10000;
      String var4 = "Initial delegatedNum1: " + delegatedNum1.getValue((Object)null, var1) + ", delegatedNum2: " + delegatedNum2.getValue((Object)null, var3);
      boolean var5 = false;
      System.out.println(var4);
      delegatedNum1.setValue((Object)null, var1, 4);
      delegatedNum2.setValue((Object)null, var3, 3);
      var4 = "Final delegatedNum1: " + delegatedNum1.getValue((Object)null, var1) + ", delegatedNum2: " + delegatedNum2.getValue((Object)null, var3);
      var5 = false;
      System.out.println(var4);
   }


public final class CalculateDelegate {
   private int calculatedProperty;

   public final int getValue(@Nullable Object thisRef, @NotNull KProperty property) {
      Intrinsics.checkNotNullParameter(property, "property");
      String var3 = "\ud83d\udd25 CalculateDelegate getValue() thisRef: " + thisRef + ", property: " + property;
      boolean var4 = false;
      System.out.println(var3);
      return this.calculatedProperty;
   }

   public final void setValue(@Nullable Object thisRef, @NotNull KProperty property, int value) {
      Intrinsics.checkNotNullParameter(property, "property");
      String var4 = "\ud83e\udd14 CalculateDelegate setValue() thisRef: " + thisRef + ", property: " + property + ", value: " + value;
      boolean var5 = false;
      System.out.println(var4);
      this.calculatedProperty = value * 2;
   }
}
 */

class Resource(var id: Int = 0) {
    override fun toString(): String {
        return "${super.toString()} with id: $id"
    }
}

class Owner {
    var varResource: Resource by ResourceDelegate()
}

class ResourceDelegate(private var resource: Resource = Resource()) {

    operator fun getValue(thisRef: Owner, property: KProperty<*>): Resource {

        println("🚀 ResourceDelegate getValue() thisRef: $thisRef, property: $property")
        return resource
    }

    operator fun setValue(thisRef: Owner, property: KProperty<*>, value: Any?) {

        println("🍒 ResourceDelegate getValue() thisRef: $thisRef, property: $property, value: $value")
        if (value is Resource) {
            resource = value
        }
    }
}
