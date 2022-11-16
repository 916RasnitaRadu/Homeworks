package View;

import Controller.Controller;
import Exceptions.InterpreterException;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.ValueExpression;
import Model.Expressions.VariableExpression;
import Model.ProgramState.ProgramState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;

import java.io.IOException;
import java.util.Scanner;

public class View {
    private final Controller controller;
    private final Scanner scanner = new Scanner(System.in);

    public View(Controller controller) {
        this.controller = controller;
    }

    private void printStepMenu()
    {
        System.out.println("a. Do it all in one step");
        System.out.println("b. Do it step by step.");
    }

    private void printMenu()
    {
        System.out.println("Select what program you want to run: ");
        System.out.println("1. Run program 1.");
        printProgram1();
        System.out.println("2. Run program 2.");
        printProgram2();
        System.out.println("3. Run program 3.");
        printProgram3();
        System.out.println("4. Run program 4.");
        printProgram4();
        System.out.println("5. Exit :( ");
    }

    public void run()
    {
        int option, option2;


        while (true)
        {
            printMenu();
            try {
                System.out.print("Please enter your option: ");
                option = scanner.nextInt();
                switch (option)
                {
                    case 1:
                        runProgram1();
                        break;
                    case 2:
                        runProgram2();
                        break;
                    case 3:
                        runProgram3();
                        break;
                    case 4:
                        runProgram4();
                        break;
                    case 5:
                        System.out.println("Bye");
                        System.exit(0);
                    default:
                        System.out.println("Invalid input!");
                        break;
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String getProg1()
    {
        return "int v;\nv = 2;\nPrint(v);\n";
    }

    public static String getProg3()
    {
        return "bool a;\nv = 2;\nPrint(v);\n";
    }

    public static String getProg2()
    {
        return "int a;\nint v;\na = true;\nIf a Then v = 2 Else v = 3;\nPrint(v);\n";
    }

    public static void printProgram2()
    {
        System.out.println("Program 2 is: ");
        System.out.println("int a;");
        System.out.println("int b;");
        System.out.println("a = 2 + 3*5;");
        System.out.println("b = a + 1;");
        System.out.println("Print(v);");
        System.out.println();
    }

    public static void printProgram1()
    {
        System.out.println("Program 1 is: ");
        System.out.println("int v;");
        System.out.println("v = 2;");
        System.out.println("Print(v);");
        System.out.println();
    }

    public static void printProgram3()
    {
        System.out.println("Program 3 is: ");
        System.out.println("bool a;");
        System.out.println("int v;");
        System.out.println("a = true;");
        System.out.println("If a Then v = 2 Else v = 3;");
        System.out.println("Print(v);");
        System.out.println();
    }

    private void printProgram4()
    {
        System.out.println("string varf;");
        System.out.println("varf = \"test.in\";");
        System.out.println("openRFile(varf);");
        System.out.println("int varc");
        System.out.println("readFile(varf,varc);print(varc);");
        System.out.println("readFile(varf,varc);print(varc);");
        System.out.println("closeRFile(varf)");
        System.out.println();
    }

    private void runProgram4()
    {
        IStatement example4 = new CompoundStatement(new DeclarationStatement(new StringType(), "varf"),
                new CompoundStatement(new AssignStatement("varf", new ValueExpression(new StringValue("D:\\Faculta\\MAP\\MAP2022-2023\\Interpretersmr\\src\\View\\test.in"))),
                        new CompoundStatement(new OpenReadFile(new VariableExpression("varf")), new CompoundStatement(
                                new DeclarationStatement(new IntType(), "varc"), new CompoundStatement(
                                        new ReadFile(new VariableExpression("varf"), "varc"),
                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")), new CompoundStatement(
                                        new ReadFile(new VariableExpression("varf"), "varc"), new CompoundStatement(
                                                new PrintStatement(new VariableExpression("varc")), new CloseReadFile(new VariableExpression("varf"))))))))));

        controller.add(example4);

        String option;
        printStepMenu();
        System.out.print("Your option: ");
        option = scanner.next();

        if (option.equals("a"))
        {
            try {
                controller.allStep();
            }
            catch (InterpreterException | IOException ie)  {
                System.out.println(ie.getMessage());
            }
        }
        else if (option.equals("b"))
        {
            try {
                ProgramState programState = controller.getProgramState();
                String option2 = "x";
                while (option2.equals("x")) {
                    programState = controller.oneStep(programState);
                    this.controller.displayProgramState(programState);
                    System.out.println("Press 'x' to continue");
                    option2 = scanner.next();
                }
            }
            catch (InterpreterException | IOException ie)
            {
                System.out.println(ie.getMessage());
            }
        }
        else {System.out.println("Invalid option!"); }
    }

    private void runProgram3() { // bool a; int v; a = true;
        // If a Then v = 2 Else v = 3; Print(v);

        IStatement example3 = new CompoundStatement(
                new DeclarationStatement(new BoolType(), "a"), new CompoundStatement(
                        new DeclarationStatement(new IntType(), "v"), new CompoundStatement(
                                new AssignStatement("a", new ValueExpression(new BoolValue(true))), new CompoundStatement(
                                        new IfStatement(new VariableExpression("a"), new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                new PrintStatement(new VariableExpression("v")))))
        );

        controller.add(example3);

        String option;
        printStepMenu();
        System.out.println("Your option: ");
        option = scanner.next();
        if (option.equals("a"))
        {
            try {
                controller.allStep();
            }
            catch (InterpreterException | IOException ie)
            {
                System.out.println(ie.getMessage());
            }
        }
        else if (option.equals("b"))
        {
            try {
                ProgramState programState = controller.getProgramState();
                String option2 = "x";
                while (option2.equals("x")) {
                    programState = controller.oneStep(programState);
                    this.controller.displayProgramState(programState);
                    System.out.println("Press 'x' to continue");
                    option2 = scanner.next();
                }
            }
            catch (InterpreterException | IOException ie)
            {
                System.out.println(ie.getMessage());
            }
        }
        else {System.out.println("Invalid option!"); }
    }


    private void runProgram2() { // int a; int b; a = 2 + 3*5; b = a + 1; Print(b);
        IStatement example2 = new CompoundStatement(
                new DeclarationStatement(new IntType(), "a"),
                new CompoundStatement(new DeclarationStatement(new IntType(), "b"),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression("+",
                                new ValueExpression(new IntValue(2)),
                                new ArithmeticExpression("*", new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignStatement("b",
                                        new ArithmeticExpression("+", new VariableExpression("a"),
                                                new ArithmeticExpression("+", new VariableExpression("a"), new ValueExpression(new IntValue(1))))),
                                        new PrintStatement(new VariableExpression("b")))))
        );
        controller.add(example2);

        String option;

        printStepMenu();
        System.out.print("Your option: ");
        option = scanner.next();

        if (option.equals("a"))
        {
            try {
                controller.allStep();
            }
            catch (InterpreterException | IOException ie)
            {
                System.out.println(ie.getMessage());
            }
        }
        else if (option.equals("b"))
        {
            try{
                ProgramState programState = controller.getProgramState();
                String option2 = "x";
                while (option2.equals("x"))
                {
                    programState = controller.oneStep(programState);
                    this.controller.displayProgramState(programState);
                    System.out.println("Press 'x' to continue");
                    option2 = scanner.next();

                }
            }
            catch (InterpreterException | IOException ie)
            {
                System.out.println(ie.getMessage());
            }
        }
        else System.out.println("Invalid option! Try again.");

    }

    private void runProgram1() { // int v; v = 2; Print(v);
        IStatement example1 = new CompoundStatement(new DeclarationStatement( new IntType(),"v"),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

        controller.add(example1);

        String option;

        printStepMenu();
        System.out.print("Your option: ");
        option = scanner.next();

        if (option.equals("a"))
        {
            try {
                this.controller.allStep();
            }
            catch (InterpreterException | IOException ie)
            {
                System.out.println(ie.getMessage());
            }
        }
        else if (option.equals("b"))
        {
            try{
                ProgramState programState = controller.getProgramState();
                String option2 = "x";
                while (option2.equals("x"))
                {
                    programState = controller.oneStep(programState);
                    this.controller.displayProgramState(programState);
                    System.out.println("Press 'x' to continue");
                    option2 = scanner.next();

                }
                programState = controller.oneStep(programState);
            }
            catch (InterpreterException | IOException ie)
            {
                System.out.println(ie.getMessage());
            }
        }
        else System.out.println("Invalid option! Try again.");

    }
}
