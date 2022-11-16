package Model.Statements;

import Exceptions.InterpreterException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.*;
import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStatement{
    private final Expression expression;
    private final String variableName;

    public ReadFile(Expression _expression, String _variableName) {
        this.expression = _expression;
        this.variableName = _variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException, IOException {
        if (state.getSymbolTable().contains_key(variableName))
        {
            if (state.getSymbolTable().get(variableName).getType().equals(new IntType()))
            {
                Value value = expression.eval(state.getSymbolTable());
                if (value.getType().equals(new StringType()))
                {
                    StringValue toStr = (StringValue) value;
                    String stringVal = toStr.getValue();

                    if (state.getFileTable().contains_key(stringVal))
                    {
                        BufferedReader fileDescriptor = state.getFileTable().get(stringVal);
                        String line = fileDescriptor.readLine();
                        IntValue readedValue = null;

                        if (line == null) { readedValue = new IntValue(0); }
                        else { readedValue = new IntValue(Integer.parseInt(line));}

                        state.getSymbolTable().put(variableName, readedValue);
                        return state;
                    }
                    else throw new InterpreterException("ERROR: The string value is not a key in the FileTable");
                }
                else throw new InterpreterException("ERROR: The expression does not result in a string");
            }
            else throw new InterpreterException("ERROR: Associated value is not an integer.");
        }
        throw new InterpreterException("ERROR: Variable name is not a key in the symbol table.");
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFile(expression.deepCopy(), variableName);
    }

    @Override
    public String toString() {
        return "read("+expression.toString() + "," + variableName + ")";
    }

    public Expression getExpression() {
        return expression;
    }

    public String getVariableName() {
        return variableName;
    }
}
