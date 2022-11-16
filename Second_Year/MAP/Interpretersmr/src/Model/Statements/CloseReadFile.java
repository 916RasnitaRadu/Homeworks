package Model.Statements;

import Exceptions.InterpreterException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.StringType;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFile implements IStatement{
    private final Expression expression;

    public CloseReadFile(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException, IOException {
        Value expressionValue = expression.eval(state.getSymbolTable());
        if (expressionValue.getType().equals(new StringType()))
        {
            StringValue toStr = (StringValue) expressionValue;
            String stringVal = toStr.getValue();

            if (state.getFileTable().contains_key(stringVal))
            {
                BufferedReader fileDescriptor = state.getFileTable().get(stringVal);
                fileDescriptor.close();

                state.getFileTable().remove(stringVal);
                return state;
            }
            else throw new InterpreterException("ERROR: File name does not exist.");
        }
        throw new InterpreterException("ERROR: The expression does not result in a string.");
    }

    @Override
    public IStatement deepCopy() {
        return new CloseReadFile(expression.deepCopy());
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "close(" + expression.toString() + ")";
    }
}
