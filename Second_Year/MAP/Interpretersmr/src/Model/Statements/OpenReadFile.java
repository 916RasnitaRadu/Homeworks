package Model.Statements;

import Exceptions.InterpreterException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.StringType;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenReadFile implements IStatement{
    private final Expression expression;

    public OpenReadFile(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException, FileNotFoundException {
        Value expressionValue = expression.eval(state.getSymbolTable());
        if (expressionValue.getType().equals(new StringType()))
        {
            StringValue toStr = (StringValue) expressionValue;
            String key = toStr.getValue();

            if (!state.getSymbolTable().contains_key(key))
            {
                BufferedReader fileDescriptor = new BufferedReader(new FileReader(key));
                state.getFileTable().put(key, fileDescriptor);
                return state;
            }
            else throw new InterpreterException("ERROR: The filename already exists!");
        }
        throw new InterpreterException("ERROR: Expression does not result into a string.");
    }

    @Override
    public IStatement deepCopy() {
        return new OpenReadFile(expression.deepCopy());
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString()
    {
        return "open(" + expression.toString() + ")";
    }
}
