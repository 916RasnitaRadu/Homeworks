package model.expressions;

import model.adts.IHeap;
import model.adts.MyDictionary;
import model.values.Value;
import model.adts.IDictionary;
import model.types.Type;
import exceptions.InterpreterException;

public interface Expression {
    Value eval(IDictionary<String, Value> table, IHeap<Value> heap) throws InterpreterException;

    Type typeCheck(IDictionary<String, Type> typeEnv) throws InterpreterException;

    Expression deepCopy();
}
