package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADTs.IDictionary;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.Value;

public class IfStatement implements IStatement{
    private Expression expression;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(Expression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IDictionary<String, Value> symTable = state.getSymbolTable();

        Value value = expression.eval(symTable);
        if (value.getType().equals(new BoolType()))
        {
            BoolValue condition_result = (BoolValue)value;
            if (condition_result.getValue())
            {
                state.getExecutionStack().push(thenStatement);
            }
            else {
                state.getExecutionStack().push(elseStatement);
            }
            return state;
        }
        throw new InterpreterException(String.format("ERROR: %s is not of boolean type inside the if statement", value.toString()));
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("if (%s) {\n\t%s\n}else{\n\t%s\n}", expression.toString(),thenStatement.toString(),elseStatement.toString());
    }

    public Expression getExpression() {
        return expression;
    }

    public IStatement getThenStatement() {
        return thenStatement;
    }

    public IStatement getElseStatement() {
        return elseStatement;
    }
}
