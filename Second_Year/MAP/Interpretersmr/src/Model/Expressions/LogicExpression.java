package Model.Expressions;

import Exceptions.InterpreterException;
import Model.ADTs.IDictionary;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExpression implements Expression{
    Expression expression1, expression2;
    int operation; // 1 - and, 2 - or, 3 - not

    public LogicExpression(int operator,Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operator;
    }

    @Override
    public Value eval(IDictionary<String, Value> table) throws InterpreterException {
        Value value1 = null, value2 = null;
        value1 = expression1.eval(table);
        if (value1.getType().equals(new BoolType())) {
            value2 = expression2.eval(table);
            if (value2.getType().equals(new BoolType()))
            {
                BoolValue bool_value1 = (BoolValue) value1;
                BoolValue bool_value2 = (BoolValue) value2;
                boolean boolean1 = bool_value1.getValue();
                boolean boolean2 = bool_value2.getValue();
                if (this.operation == 1)
                {
                    return new BoolValue(boolean1 && boolean2);
                }
                if (this.operation == 2)
                {
                    return new BoolValue(boolean1 || boolean2);
                }
            }
            else {
                throw new InterpreterException("ERROR: the second operand is not a logic operand");
            }
        }

        throw new InterpreterException("ERROR: first operand is not a logic operand");
    }

    @Override
    public Expression deepCopy() {
        return new LogicExpression(operation, expression1.deepCopy(), expression2.deepCopy());
    }

    public Expression getExpression1() {
        return expression1;
    }

    public void setExpression1(Expression expression1) {
        this.expression1 = expression1;
    }

    public Expression getExpression2() {
        return expression2;
    }

    public void setExpression2(Expression expression2) {
        this.expression2 = expression2;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operator) {
        this.operation = operator;
    }

    @Override
    public String toString() {
        return "LogicExpression{" +
                "expression1=" + expression1 +
                ", expression2=" + expression2 +
                ", operation=" + operation +
                '}';
    }
}
