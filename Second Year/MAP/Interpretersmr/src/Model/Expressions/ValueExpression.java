package Model.Expressions;

import Exceptions.InterpreterException;
import Model.ADTs.IDictionary;
import Model.Values.Value;

public class ValueExpression implements Expression {
    private Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public Value eval(IDictionary<String, Value> table) throws InterpreterException {
        return value;
    }

    @Override
    public Expression deepCopy() {
        return new ValueExpression(value.clone());
    }

    @Override
    public String toString()
    {
        return String.format("ValueExpression(%s)", value.toString());
    }
}
