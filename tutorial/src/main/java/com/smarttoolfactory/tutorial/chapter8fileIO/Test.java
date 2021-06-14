package com.smarttoolfactory.tutorial.chapter8fileIO;

import java.io.*;

public class Test {

    public static void writeFile4() throws IOException {
        File fout = new File("out.txt");
        FileOutputStream fos = new FileOutputStream(fout);

        OutputStreamWriter osw = new OutputStreamWriter(fos);

        for (int i = 0; i < 10; i++) {
            osw.write("something");
        }

        osw.close();
    }

    public static void writeFile3() throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("out.txt"));

        for (int i = 0; i < 10; i++) {
            pw.write("something");
        }

        pw.close();
    }

    public static void writeFile2() throws IOException {
        FileWriter fw = new FileWriter("out.txt");

        for (int i = 0; i < 10; i++) {
            fw.write("something");
        }

        fw.close();
    }
}
