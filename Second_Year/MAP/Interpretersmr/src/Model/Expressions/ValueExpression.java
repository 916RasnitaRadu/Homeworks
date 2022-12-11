package model.expressions;

import exceptions.InterpreterException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.values.Value;

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
    public Value eval(IDictionary<String, Value> table, IHeap<Value> heap) throws InterpreterException {
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
