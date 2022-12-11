package model.expressions;

import model.adts.IHeap;
import model.values.Value;
import model.adts.IDictionary;
import exceptions.InterpreterException;

public interface Expression {
    Value eval(IDictionary<String, Value> table, IHeap<Value> heap) throws InterpreterException;

    Expression deepCopy();
}
