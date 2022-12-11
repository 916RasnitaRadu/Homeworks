package model.statements;

import exceptions.InterpreterException;
import model.expressions.Expression;
import model.programState.ProgramState;

public class PrintStatement implements IStatement{
    private Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        state.getOutput().add(
                expression.eval(state.getSymbolTable(), state.getHeap())
        );
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("Print{%s}",expression.toString());
    }
}
