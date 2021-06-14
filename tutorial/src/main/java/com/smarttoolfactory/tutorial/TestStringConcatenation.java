package com.smarttoolfactory.tutorial;

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

        // We can add to List<? super T> but CANNOT read from this list
        List<? super Account> contravariantList = new ArrayList<>();
        contravariantList.add(account);
        // 🔥 Compile ERROR: Required type: capture of ? super Account
//        for (Account acc1 : accountList) {
//
//        }

        // We can CANNOT add to List<? extends T> but CAN read from this list
        List<? extends Account> covariantList = new ArrayList<>();
        // 🔥 Compile ERROR: Required type: capture of ? extends Account
//        accountList.add(account);
        for (Account acc2 : covariantList) {

        }

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
