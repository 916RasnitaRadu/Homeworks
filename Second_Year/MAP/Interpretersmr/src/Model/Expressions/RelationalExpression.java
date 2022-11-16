package Model.Expressions;

import Exceptions.InterpreterException;
import Model.ADTs.IDictionary;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

public class RelationalExpression implements Expression{
    private final Expression expression1, expression2;

    String operator;

    public RelationalExpression(String operator, Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    @Override
    public Value eval(IDictionary<String, Value> table) throws InterpreterException {
        Value value1 = null, value2 = null;
        value1 = expression1.eval(table);
        if (value1.getType().equals(new IntType()))
        {
            value2 = expression2.eval(table);
            if (value2.getType().equals(new IntType()))
            {
                IntValue toInt1 = (IntValue) value1;
                IntValue toInt2 = (IntValue) value2;
                int number1, number2;
                number1 = toInt1.getValue();
                number2 = toInt2.getValue();

                switch (operator)
                {
                    case "<":
                        return new BoolValue(number1 < number2);
                    case "<=":
                        return new BoolValue(number1 <= number2);
                    case "==":
                        return new BoolValue(number1 == number2);
                    case "!=":
                        return new BoolValue(number1 != number2);
                    case ">":
                        return new BoolValue(number1 > number2);
                    case ">=":
                        return new BoolValue(number1 >= number2);
                    default:
                        throw new InterpreterException("ERROR: Invalid operator.");
                }
            }
            else throw new InterpreterException("ERROR: The second expression does not result in an integer");
        }
        throw new InterpreterException("ERROR: The first expression does not result in an integer");
    }

    @Override
    public Expression deepCopy() {
        return new RelationalExpression(operator, expression1.deepCopy(), expression2.deepCopy());
    }

    @Override
    public String toString() {
        return "RelationalExpression{" +
                "expression1=" + expression1 +
                ", expression2=" + expression2 +
                ", operator='" + operator + '\'' +
                '}';
    }

    public Expression getExpression1() {
        return expression1;
    }

    public Expression getExpression2() {
        return expression2;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
