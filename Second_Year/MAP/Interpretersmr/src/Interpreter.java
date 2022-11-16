import Controller.Controller;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.ValueExpression;
import Model.Expressions.VariableExpression;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Repository.IRepository;
import Repository.Repository;
import View.*;

public class Interpreter {

    public static void main(String[] args) {

        IRepository repository = new Repository("ceva.txt");
        Controller controller = new Controller(repository);
        View ui = new View(controller);
        ui.run();

        IStatement example1 = new CompoundStatement(new DeclarationStatement( new IntType(),"v"),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
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

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",View.getProg1(),controller1));
        menu.addCommand(new RunExample("2",View.getProg2(), controller2));
        menu.addCommand(new RunExample("3",View.getProg3(), controller3));
        menu.show();
      }
}
