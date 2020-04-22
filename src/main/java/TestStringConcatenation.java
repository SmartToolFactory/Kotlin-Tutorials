import java.util.ArrayList;
import java.util.List;

public class TestStringConcatenation {

    public static void main(String[] args) {

        long startTime = System.nanoTime();
        testStringConcat();
        long totalTime = System.nanoTime() - startTime;

        System.out.println("Total time for String concat: " + totalTime);

        startTime = System.nanoTime();
        testStringBuilder();
        totalTime = System.nanoTime() - startTime;
        System.out.println("Total time for StringBuilder: " + totalTime);


        Account account = new BankAccount();
        BankAccount bankAccount = new BankAccount();
        InterestAccount interestAccount = new InterestAccount();


        List<? extends Account> accountList = new ArrayList<>();

        List<? super InterestAccount> interestList = new ArrayList<>();

        interestList.add(interestAccount);



    }

    private static String testStringConcat() {
        return "ByteCode：ldc pushes a one-word constant onto the operand stack. ldc takes a single parameter, , which is the value to push."
                + "ByteCode：ldc pushes a one-word constant onto the operand stack. ldc takes a single parameter, , which is the value to push."
                + "ByteCode：ldc pushes a one-word constant onto the operand stack. ldc takes a single parameter, , which is the value to push."
                + "ByteCode：ldc pushes a one-word constant onto the operand stack. ldc takes a single parameter, , which is the value to push."
                + "ByteCode：ldc pushes a one-word constant onto the operand stack. ldc takes a single parameter, , which is the value to push."
                + "Lorem Ipsum ";

    }


    private static String testStringBuilder() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("ByteCode：ldc pushes a one-word constant onto the operand stack. ldc takes a single parameter, , which is the value to push.");
        stringBuilder.append("ByteCode：ldc pushes a one-word constant onto the operand stack. ldc takes a single parameter, , which is the value to push.");
        stringBuilder.append("ByteCode：ldc pushes a one-word constant onto the operand stack. ldc takes a single parameter, , which is the value to push.");
        stringBuilder.append("ByteCode：ldc pushes a one-word constant onto the operand stack. ldc takes a single parameter, , which is the value to push.");
        stringBuilder.append("ByteCode：ldc pushes a one-word constant onto the operand stack. ldc takes a single parameter, , which is the value to push.");

        stringBuilder.append("Lorem Ipsum");

        return stringBuilder.toString();

    }


    interface Account {

    }

    static class BankAccount implements Account {

    }

    static class InterestAccount implements Account {

    }
}