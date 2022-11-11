package Model.Expressions;

import Exceptions.InterpreterException;
import Model.ADTs.IDictionary;
import Model.Values.Value;

public class VariableExpression implements Expression {
    private String key;

    public VariableExpression(String k) {
        this.key = k;
    }

    @Override
    public Value eval(IDictionary<String, Value> table) throws InterpreterException {
        return table.get(key);
    }

    @Override
    public Expression deepCopy() {
        return new VariableExpression(key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString()
    {
        return String.format("VariableExpression(%s)", key);
    }
}
