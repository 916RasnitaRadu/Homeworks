package Model.Expressions;

import Model.Values.Value;
import Model.ADTs.IDictionary;
import Exceptions.InterpreterException;

public interface Expression {
    Value eval(IDictionary<String, Value> table) throws InterpreterException;

    Expression deepCopy();
}
