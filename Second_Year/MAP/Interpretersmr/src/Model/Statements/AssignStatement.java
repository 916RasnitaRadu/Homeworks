package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADTs.IDictionary;
import Model.ADTs.IStack;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Values.Value;
import Model.Types.Type;

public class AssignStatement implements IStatement{
    private String key;
    private Expression expression;

    public AssignStatement(String key, Expression expression) {
        this.key = key;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IStack<IStatement> exeStack = state.getExecutionStack();
        IDictionary<String, Value> symTable = state.getSymbolTable();

        if (symTable.contains_key(key)) {
            Value value = expression.eval(symTable);
            Type type = symTable.get(key).getType();
            if (value.getType().equals(type))
            {
                symTable.put(key, value);
            }
            else throw new InterpreterException(String.format("ERROR: %s is not compatible with %s", value.toString(), type.toString()));
        }
        else throw new InterpreterException("The used variable " + key + " was not declared before.");
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(key, expression.deepCopy());
    }

    @Override
    public String toString()
    {
        return key + " = " + expression.toString();
    }

    public String getKey() {
        return key;
    }

    public Expression getExpression() {
        return expression;
    }
}
