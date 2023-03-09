package model.statements;

import exceptions.InterpreterException;
import model.adts.IDictionary;
import model.adts.IStack;
import model.expressions.Expression;
import model.expressions.RelationalExpression;
import model.programState.ProgramState;
import model.types.Type;

import java.io.IOException;

public class SwitchStatement implements IStatement{
    private final Expression mainExpression;
    private final Expression expression1;
    private final IStatement statement1;
    private final Expression expression2;
    private final IStatement statement2;
    private final IStatement defaultStatement;

    public SwitchStatement(Expression mainExpression, Expression expression1, IStatement statement1, Expression expression2, IStatement statement2, IStatement defaultStatement) {
        this.mainExpression = mainExpression;
        this.expression1 = expression1;
        this.statement1 = statement1;
        this.expression2 = expression2;
        this.statement2 = statement2;
        this.defaultStatement = defaultStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException, IOException {
        IStack<IStatement> executionStack = state.getExecutionStack();
        IStatement convertedStatement = new IfStatement(new RelationalExpression("==",mainExpression,expression1) ,statement1,
                new IfStatement(new RelationalExpression("==",mainExpression,expression2),statement2, defaultStatement));
        executionStack.push(convertedStatement);
        state.setExecutionStack(executionStack);
        return state;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws InterpreterException {
        Type mainType = mainExpression.typeCheck(typeTable);
        Type typeExpression1 = expression1.typeCheck(typeTable);
        Type typeExpression2 = expression2.typeCheck(typeTable);

        if (!mainType.equals(typeExpression1) || !mainType.equals(typeExpression2)) {
            throw new InterpreterException("ERROR: The expression types don't match in the switch statement.");
        }
        statement1.typeCheck(typeTable.copy());
        statement2.typeCheck(typeTable.copy());
        defaultStatement.typeCheck(typeTable.copy());
        return typeTable;
    }

    @Override
    public IStatement deepCopy() {
        return new SwitchStatement(mainExpression.deepCopy(),expression1.deepCopy(),statement1.deepCopy(),expression2.deepCopy(),statement2.deepCopy(),defaultStatement.deepCopy());
    }

    @Override
    public String toString()
    {
        return String.format("switch(%s)\n{\n(case(%s): %s) \n(case(%s): %s)\n (default: %s)}",mainExpression,expression1,statement1,expression2,statement2,defaultStatement);
    }
}
