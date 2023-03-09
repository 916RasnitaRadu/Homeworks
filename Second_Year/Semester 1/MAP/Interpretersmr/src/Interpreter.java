import controller.Controller;
import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.ReferenceType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;
import view.*;

public class Interpreter {

    public static void main(String[] args) {

//        IRepository repository = new Repository("ceva.txt");
//        Controller controller = new Controller(repository);
//        View ui = new View(controller);
//        ui.run();

        IStatement example1 = new CompoundStatement(new DeclarationStatement( new IntType(),"v"),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new StringValue("312"))), //IntValue(2)
                        new PrintStatement(new VariableExpression("v"))));
        IRepository repository1 = new Repository("log1.txt");
        Controller controller1 = new Controller(repository1);
        controller1.add(example1);
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
        IRepository repository2 = new Repository("log2.txt");
        Controller controller2 = new Controller(repository2);
        controller2.add(example2);

        IStatement example3 = new CompoundStatement(
                new DeclarationStatement(new BoolType(), "a"), new CompoundStatement(
                new DeclarationStatement(new IntType(), "v"), new CompoundStatement(
                new AssignStatement("a", new ValueExpression(new BoolValue(true))), new CompoundStatement(
                new IfStatement(new VariableExpression("a"), new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                new PrintStatement(new VariableExpression("v")))))
        );
        IRepository repository3 = new Repository("log3.txt");
        Controller controller3 = new Controller(repository3);
        controller3.add(example3);

        IStatement example4 = new CompoundStatement(new DeclarationStatement(new StringType(), "varf"),
                new CompoundStatement(new AssignStatement("varf", new ValueExpression(new StringValue("D:\\Faculta\\MAP\\MAP2022-2023\\Interpretersmr\\src\\View\\test.in"))),
                        new CompoundStatement(new OpenReadFile(new VariableExpression("varf")), new CompoundStatement(
                                new DeclarationStatement(new IntType(), "varc"), new CompoundStatement(
                                new ReadFile(new VariableExpression("varf"), "varc"),
                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")), new CompoundStatement(
                                        new ReadFile(new VariableExpression("varf"), "varc"), new CompoundStatement(
                                        new PrintStatement(new VariableExpression("varc")), new CloseReadFile(new VariableExpression("varf"))))))))));
        IRepository repository4 = new Repository("log4.txt");
        Controller controller4 = new Controller(repository4);
        controller4.add(example4);


        IStatement example5 = new CompoundStatement(new DeclarationStatement(new ReferenceType(new IntType()),"v"),
                new CompoundStatement(new NewStatement("v", new ValueExpression(new IntValue(20))), new CompoundStatement(
                        new DeclarationStatement(new ReferenceType(new ReferenceType(new IntType())), "a"), new CompoundStatement(
                        new NewStatement("a", new VariableExpression("v")), new CompoundStatement(new PrintStatement(new ReadHeap(new VariableExpression("v"))),
                                new PrintStatement(new ArithmeticExpression("+", new ReadHeap(new ReadHeap(new VariableExpression("a"))), new ValueExpression(new IntValue(5)))))))));
        IRepository repository5 = new Repository("log5.txt");
        Controller controller5 = new Controller(repository5);
        controller5.add(example5);

        IStatement example6 = new CompoundStatement(new DeclarationStatement(new ReferenceType(new IntType()), "v"), new CompoundStatement(
                new NewStatement("v", new ValueExpression(new IntValue(20))), new CompoundStatement(
                        new PrintStatement(new ReadHeap(new VariableExpression("v"))), new CompoundStatement(new WriteHeap("v", new ValueExpression(new IntValue(30))),
                                new PrintStatement(new ArithmeticExpression("+", new ReadHeap(new VariableExpression("v")), new ValueExpression(new IntValue(5))))))));
        IRepository repository6 = new Repository("log6.txt");
        Controller controller6 = new Controller(repository6);
        controller6.add(example6);

        IStatement example7 = new CompoundStatement(new DeclarationStatement(new IntType(), "v"), new CompoundStatement(
                new AssignStatement("v", new ValueExpression(new IntValue(4))), new CompoundStatement(
                        new WhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(0))),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignStatement("v",new ArithmeticExpression("-", new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                new PrintStatement(new VariableExpression("v")))));

        IRepository repository7 = new Repository("log7.txt");
        Controller controller7 = new Controller(repository7);
        controller7.add(example7);

        IStatement example8 = new CompoundStatement(new DeclarationStatement(new ReferenceType(new IntType()), "v"), new CompoundStatement(
                new NewStatement("v", new ValueExpression(new IntValue(20))), new CompoundStatement(
                        new DeclarationStatement(new ReferenceType(new ReferenceType(new IntType())), "a"), new CompoundStatement(
                                new NewStatement("a", new VariableExpression("v")), new CompoundStatement(
                                        new NewStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ReadHeap(new ReadHeap(new VariableExpression("a")))))))));
        IRepository repository8 = new Repository("log8.txt");
        Controller controller8 = new Controller(repository8);
        controller8.add(example8);

        IStatement example9 = new CompoundStatement(new DeclarationStatement(new IntType(), "v"), new CompoundStatement(
                new DeclarationStatement(new ReferenceType(new IntType()),"a"), new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(10))), new CompoundStatement(
                                new NewStatement("a", new ValueExpression(new IntValue(22))), new CompoundStatement(
                                        new ForkStatement(new CompoundStatement(new WriteHeap("a", new ValueExpression(new IntValue(30))), new CompoundStatement(
                                                new AssignStatement("v", new ValueExpression(new IntValue(32))), new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeap(new VariableExpression("a"))))))), new CompoundStatement(
                                                                new PrintStatement(new VariableExpression("v")), new PrintStatement(new ReadHeap(new VariableExpression("a")))))))));
        IRepository repository9 = new Repository("log9.txt");
        Controller controller9 = new Controller(repository9);
        controller9.add(example9);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", View.getProg1(), controller1));
        menu.addCommand(new RunExample("2", View.getProg2(), controller2));
        menu.addCommand(new RunExample("3", View.getProg3(), controller3));
        menu.addCommand(new RunExample("4", View.getProg4(), controller4));
        menu.addCommand(new RunExample("5", View.getProg5(), controller5));
        menu.addCommand(new RunExample("6", View.getProg6(), controller6));
        menu.addCommand(new RunExample("7", View.getProg7(), controller7));
        menu.addCommand(new RunExample("8", View.getProg8(), controller8));
        menu.addCommand(new RunExample("9", View.getProg9(), controller9));
        menu.show();
      }
}
