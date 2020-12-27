/**
 * Class for displaying inheritance when a field exists in both super and derived type
 *
 * If static type of object is super(Animal cat) then field from super type is used to
 * fetch and modify.
 *
 * * When a method is overridden by a derived class then derived class' method is called
 */
public class Animal {

    String name;

    public Animal(String name) {
        this.name = name;
    }

    public void printName() {
        System.out.println("Animal name: " + name);
    }

    public String getCatName() {
        return name;
    }
    public static void main(String[] args) {

        Animal cat = new Cat("Cat");

        cat.name = "Felix";
        cat.printName();

        // Cat name is from Animal but overridden method is from Cat dynamic type
        System.out.println("CAT NAME: " + cat.name +", CAT.getName(): " + cat.getCatName());

        /*
            Prints:

           Cat name: Cat, Animal name: Felix
           CAT NAME: Felix, CAT.getName(): Cat
         */

    }
}


class Cat extends Animal {

    // 🔥If type is Animal this is shadowed, name from Animal is set and get when called
    String name;

    public Cat(String name) {
        super("Felis Domesticus");
        this.name = name;
    }

    public String getCatName() {
        return name;
    }

    public void printName() {
        System.out.println("Cat name: " + name + ", Animal name: " + super.name);
    }
}