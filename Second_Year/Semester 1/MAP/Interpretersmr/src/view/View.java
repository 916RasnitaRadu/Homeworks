package view;

import controller.Controller;


import java.io.IOException;
import java.util.Scanner;

public class View {
    private final Controller controller;
    private final Scanner scanner = new Scanner(System.in);

    public View(Controller controller) {
        this.controller = controller;
    }


    public static String getProg1() {
        return "int v;\nv = 2;\nPrint(v);\n";
    }

    public static String getProg3() {
        return "bool a;\nv = 2;\nPrint(v);\n";
    }

    public static String getProg2() {
        return "int a;\nint v;\na = true;\nIf a Then v = 2 Else v = 3;\nPrint(v);\n";
    }

    public static String getProg4() {
        return "string varf;\nvarf = \"test.in\";\nopenRFile(varf);\nint varc;\nreadFile(varf,varc);\nprint(varc);\nreadFile(varf,varc);\nprint(varc);\ncloseRFile(varf);\n";
    }

    public static String getProg5() {
        return "Ref int v;\nnew(v,20);\nRef Ref int a;\nnew(a,v);\nprint(readHeap(v));\nprint(readHeap(readHeap(a)) + 5);\n";
    }

    public static String getProg7() {
        return "int v;\nv = 4;\n(while (v > 0) print(v); v = v - 1);\nprint(v)";
    }

    public static String getProg6() {
        return "Ref int v;\nnew(v,20);\nprint(readHeap(v));\nwriteHeap(v,30);\nprint(readHeap(v) + 5);\n";
    }

    public static String getProg8() {
        return "Ref int v;\nnew(v,20);\nRef Ref int a;\nnew(a,v);\nnew(v,30);\nprint(readHeap(readHeap(a)));\n";
    }

    public static String getProg9(){
        return "int v;\nRef int a;\nv = 10;\nnew(a,22);\nfork(\n\twriteHeap(a,30);\n\tv = 32;\n\tprint(v);\n\tprint(readHeap(a)));\nprint(v);\nprint(readHeap(a));\n";
    }

    public static void printProgram2() {
        System.out.println("Program 2 is: ");
        System.out.println("int a;");
        System.out.println("int b;");
        System.out.println("a = 2 + 3*5;");
        System.out.println("b = a + 1;");
        System.out.println("Print(v);");
        System.out.println();
    }

    public static void printProgram1() {
        System.out.println("Program 1 is: ");
        System.out.println("int v;");
        System.out.println("v = 2;");
        System.out.println("Print(v);");
        System.out.println();
    }

    public static void printProgram3() {
        System.out.println("Program 3 is: ");
        System.out.println("bool a;");
        System.out.println("int v;");
        System.out.println("a = true;");
        System.out.println("If a Then v = 2 Else v = 3;");
        System.out.println("Print(v);");
        System.out.println();
    }

    private static void printProgram4() {
        System.out.println("string varf;");
        System.out.println("varf = \"test.in\";");
        System.out.println("openRFile(varf);");
        System.out.println("int varc");
        System.out.println("readFile(varf,varc);print(varc);");
        System.out.println("readFile(varf,varc);print(varc);");
        System.out.println("closeRFile(varf)");
        System.out.println();
    }
}
