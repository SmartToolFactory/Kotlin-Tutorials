package com.smarttoolfactory.tutorial.chapter6Advanced;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class GenericJava {

    public static void main(String[] args) {

        List<Object> objList = new ArrayList<>();
        List<String> strList = new ArrayList<>();

//        objList = strList;
        /*
            🔥 COMPILE ERROR, this is not possible without bounds
            List<String> is not subtype of List<Object>
         */


        /*
            List type extensions and assigning bounded lists
         */
        List<? extends BaseShape> baseShapeExtends = new ArrayList<>();
        List<? super BaseShape> baseShapeSuper = new ArrayList<>();

        List<? extends Shape> shapesExtends = new ArrayList<>();
        List<? super Shape> shapesSuper = new ArrayList<>();

        baseShapeExtends = shapesExtends; // Works fine
//        shapesExtends = baseShapeExtends; // ❌ COMPILE ERROR Required type: List <? extends Shape>

//        baseShapeSuper = shapesSuper; // ❌ COMPILE ERROR Required type: List <? super UniShape>
        shapesSuper = baseShapeSuper; // Works fine


        Cow cow = new Cow();
        Mammal mammal = new Mammal();

        Animals<Mammal> mammals = new Animals<>();
        // 🔥 Even it's subtype of Animal method works in class
        mammals.doSomething(cow);

        Animals<Cow> cows = new Animals<>();

        // ❌ COMPILE ERROR Animals<Cow> is not sub-type of Animals<Mammal>
//        mammals = cows;
//        cows = mammals;

        // 🔥 Since this is List<T> it only works with type T, need List<? extends T>
        // to work with sub types of T
        mammals.doSomethingList(new ArrayList<Mammal>());
//        mammals.doSomethingList(new ArrayList<Cow>());  // ❌ COMPILE ERROR


        /*
            Covariance, contravariant setting and getting values
         */
        ShapeMaker<Shape> shapeMaker = new ShapeMaker<>();

        Circle circle = new Circle();
        Rectangle rectangle = new Rectangle();
        Triangle triangle = new Triangle();
        Shape shape = new Shape();
        BaseShape baseShape = new BaseShape();

        shapeMaker.setShape(circle);
        System.out.println("ShapeMaker shape: " + shapeMaker.getShape());
        shapeMaker.setShape(rectangle);
        System.out.println("ShapeMaker shape: " + shapeMaker.getShape());

        // shapeMaker.setShape(uniShape); // ❌ COMPILE ERROR

        ShapeCreator<Shape> shapeCreator = new ShapeCreator<>();
        shapeCreator.setShape(circle);
        System.out.println("ShapeCreator shape: " + shapeCreator.getShape()); // This is Circle

        ShapeCreator<? extends Shape> shapeCreatorExtends = new ShapeCreator<>();
        // This works because ? extends accept setting type to contravariant
//        shapeCreatorExtends = shapeCreator;
//        shapeCreatorExtends.setShape(shape); // ❌ COMPILE ERROR


        ShapeCreator<? super Shape> shapeCreatorSuper = new ShapeCreator<>();

        shapeCreatorSuper.setShape(shape);


        List<? super Shape> listOfShapes = shapeCreator.getListOfShapes(circle, rectangle, triangle);

        // ❌ COMPILE ERROR because it's super type, and can only return Object
//        Shape shape1 = shapeCreator.getShape();

        /*
            Code
            public static <T> void fill(List<? super T> list, T obj)
         */
        Collections.fill(new ArrayList<>(), "Hello");
    }


}


class Animals<T> {


    /**
     * This method can accept type T and sub-types of it
     * @param animal
     */
    public void doSomething(T animal) {
        System.out.println("Animal " + animal);
    }

    /**
     * This method only accepts <code>List<T></code>
     * @param animals
     */
    public void doSomethingList(List<T> animals) {
        for (T animal : animals) {
            System.out.println("Animals " + animal);
        }
    }

    public void doSomethingListUpperBounded(List<? extends T> animals) {
        for (T animal : animals) {
            System.out.println("Animals " + animal);
        }
    }

    public void doSomethingListLowerBounded(List<? super T> animals, T animal) {
        animals.add(animal);
    }
}

class Mammal {
    public void printName() {
        System.out.println("I'm MAMMAL");
    }
}

class Cow extends Mammal {
    public void printName() {
        System.out.println("I'm Cow");
    }
}

class Dolphin extends Mammal {
    public void printName() {
        System.out.println("I'm Dolphin");
    }
}

class Bird {
    public void printName() {
        System.out.println("I'm Bird");
    }
}

class ShapeCreator<T> {

    T shape;

    public T getShape() {
        return shape;
    }

    public void setShape(T shape) {
        this.shape = shape;
    }

    public <S extends Shape> List<? super S> getListOfShapes(S... shapes) {

        List<S> sList = new ArrayList<>();

        Collections.addAll(sList, shapes);


        List<? super S> shapeList = sList;

        return shapeList;
    }

}

class ShapeMaker<T extends Shape> {

    T shape;

    public T getShape() {
        return shape;
    }

    public void setShape(T shape) {
        this.shape = shape;
    }

}


class BaseShape {
}

class Shape extends BaseShape {
}

class Circle extends Shape {
}

class Rectangle extends Shape {
}

class Triangle extends Shape {
}

class Fruit {
    @Override
    public String toString() {
        return "I am a Fruit !!";
    }
}

class Apple extends Fruit {
    @Override
    public String toString() {
        return "I am an Apple !!";
    }

    public void appleMethod() {
        System.out.println("This is APPLE METHOD");
    }
}

class AsianApple extends Apple {
    @Override
    public String toString() {
        return "I am an AsianApple !!";
    }


}

class GenericsExamplesExtend {
    public static void main(String[] args) {
        //List of apples
        List<Apple> apples = new ArrayList<Apple>();
        apples.add(new Apple());

        //We can assign a list of apples to a basket of fruits;
        //because apple is subtype of fruit
        List<? extends Fruit> basket = apples;

        //Here we know that in basket there is nothing but fruit only
        for (Fruit fruit : basket) {
            System.out.println("GenericsExamplesExtend " + fruit);
        }

        //basket.add(new Apple()); //Compile time error
        //basket.add(new Fruit()); //Compile time error

        /*

        SOURCE: https://howtodoinjava.com/java/generics/java-generics-what-is-pecs-producer-extends-consumer-super/

        Look at the for loop above. It ensures that whatever it comes out from basket
        is definitely going to be a fruit; so you iterate over it and simply cast it a Fruit.
        Now in last two lines, I tried to add an Apple and then a Fruit in basket,
        but compiler didn’t allowed me. Why?

        The reason is pretty simple, if we think about it; the <? extends Fruit> wildcard
        tells the compiler that we’re dealing with a subtype of the type Fruit,
        but we cannot know which fruit as there may be multiple subtypes.
        Since there’s no way to tell, and we need to guarantee type safety (invariance),
        you won’t be allowed to put anything inside such a structure.

        On the other hand, since we know that whichever type it might be,
        it will be a subtype of Fruit, we can get data out of the structure with the
        guarantee that it will be a Fruit.

        🔥🔥 In above example, we are taking elements out of collection
        “List<? extends Fruit> basket“; so here this basket is actually producing
        the elements i.e. fruits.

        In simple words, when you want to ONLY retrieve elements out of a collection,
        treat it as a producer and use “? extends T>” syntax.
        “Producer extends” now should make more sense to you.
         */
    }
}

class GenericsExamplesSuper {

    public static void main(String[] args) {
        //List of apples
        List<Apple> apples = new ArrayList<Apple>();
        apples.add(new Apple());

        //We can assign a list of apples to a basket of apples
        List<? super Apple> basket = apples;


        basket.add(new Apple());      //Successful
        basket.add(new AsianApple()); //Successful
//        basket.add(new Fruit());   // 🔥🔥 Compile time error

        List<Fruit> fruits = new ArrayList<>();
        copyList(apples, fruits);


    /*
        We are able to add apple and even Asian apple inside basket,
        but we are not able to add Fruit (super type of apple) to basket. Why?

        Reason is that basket is a reference to a List of something that is a supertype of Apple.
        Again, we cannot know which supertype it is, but we know that Apple and any
        of its subtypes (which are subtype of Fruit) can be added to be without
        problem (you can always add a subtype in collection of supertype).
        So, now we can add any type of Apple inside basket.

        What about getting data out of such a type? It turns out that you the only thing
        you can get out of it will be Object instances: since we cannot know which supertype
        it is, the compiler can only guarantee that it will be a reference to an Object,
        since Object is the supertype of any Java type.
     */


        // List<? super Apple> is only writable list
        List<? super Apple> objects = copyFrom(fruits);


    }

    /**
     * Read from <code>List<? extends Fruit></code> and write to <code>List<? super Fruit></code>
     *
     * @param src  List being read from
     * @param dest List being written into
     */
    public static void copyList(List<? extends Fruit> src, List<? super Fruit> dest) {
        src.forEach((Consumer<Fruit>) fruit -> dest.add(fruit));
    }

    public static List<? super Apple> copyFrom(List<? extends Fruit> src) {

        List<? super Fruit> fruitList = new ArrayList<>();

        src.forEach(new Consumer<Fruit>() {
            @Override
            public void accept(Fruit fruit) {
                fruitList.add(fruit);
            }
        });

        return fruitList;
    }
}
