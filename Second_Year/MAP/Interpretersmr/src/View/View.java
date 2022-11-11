package View;

import Controller.Controller;
import Exceptions.InterpreterException;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.ValueExpression;
import Model.Expressions.VariableExpression;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IntValue;

import javax.swing.text.StyledEditorKit;
import java.util.Scanner;

public class View {
    private final Controller controller;
    private final Scanner scanner = new Scanner(System.in);

    public View(Controller controller) {
        this.controller = controller;
    }

    private void printMenu()
    {
        System.out.println("Select what program you want to run: ");
        System.out.println("1. Run program 1.");
        System.out.println("2. Run program 2.");
        System.out.println("3. Run program 3.");
        System.out.println("4. Exit :( ");
    }

    public void run()
    {
        int option;

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

    private void printProgram2()
    {
        System.out.println("Program 2 is: ");
        System.out.println("int a;");
        System.out.println("int b;");
        System.out.println("a = 2 + 3*5");
        System.out.println("b = a + 1;");
        System.out.println("Print(v);");
        System.out.println();
    }

    private void printProgram1()
    {
        System.out.println("Program 1 is: ");
        System.out.println("int v;");
        System.out.println("v = 2;");
        System.out.println("Print(v);");
        System.out.println();
    }

    private void printProgram3()
    {
        System.out.println("Program 3 is: ");
        System.out.println("bool a;");
        System.out.println("int v;");
        System.out.println("a = true;");
        System.out.println("If a Then v = 2 Else v = 3;");
        System.out.println("Print(v);");
        System.out.println();
    }

    private void runProgram3() { // bool a; int v; a = true;
        // If a Then v = 2 Else v = 3; Print(v);
        printProgram3();

        IStatement example3 = new CompoundStatement(
                new DeclarationStatement(new BoolType(), "a"), new CompoundStatement(
                        new DeclarationStatement(new IntType(), "v"), new CompoundStatement(
                                new AssignStatement("a", new ValueExpression(new BoolValue(true))), new CompoundStatement(
                                        new IfStatement(new VariableExpression("a"), new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                new PrintStatement(new VariableExpression("v")))))
        );

        controller.add(example3);
        try {
            controller.allStep();
        }
        catch (InterpreterException ie)
        {
            System.out.println(ie.getMessage());
        }
    }


    private void runProgram2() { // int a; int b; a = 2 + 3*5; b = a + 1; Print(b);
        printProgram2();
        IStatement example2 = new CompoundStatement(
                new DeclarationStatement(new IntType(), "a"),
                new CompoundStatement(new DeclarationStatement(new IntType(), "b"),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression(1,
                                new ValueExpression(new IntValue(2)),
                                new ArithmeticExpression(3, new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignStatement("b",
                                        new ArithmeticExpression(1, new VariableExpression("a"),
                                                new ArithmeticExpression(1, new VariableExpression("a"), new ValueExpression(new IntValue(1))))),
                                        new PrintStatement(new VariableExpression("b")))))
        );
        controller.add(example2);
        try {
            controller.allStep();
        }
        catch (InterpreterException ie)
        {
            System.out.println(ie.getMessage());
        }
    }

    private void runProgram1() { // int v; v = 2; Print(v);
        printProgram1();
        IStatement example1 = new CompoundStatement(new DeclarationStatement( new IntType(),"v"),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
        controller.add(example1);
        try {
            this.controller.allStep();
        }
        catch (InterpreterException ie)
        {
            System.out.println(ie.getMessage());
        }
    }
}
