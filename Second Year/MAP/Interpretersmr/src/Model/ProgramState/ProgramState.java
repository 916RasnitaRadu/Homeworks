package Model.ProgramState;

import Exceptions.InterpreterException;
import Model.ADTs.*;
import Model.Statements.IStatement;
import Model.Values.Value;

public class ProgramState {
    private IStack<IStatement> executionStack;
    private IDictionary<String, Value> symbolTable;
    private IList<Value> output;
    private IStatement statement;

    public ProgramState(IStack<IStatement> executionStack, IDictionary<String, Value> symbolTable, IList<Value> output) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
    //    this.executionStack.push(statement);
    }

    public ProgramState(IStatement originalProgram) {
        executionStack = new MyStack<>();
        symbolTable = new MyDictionary<>();
        output = new MyList<>();
    }

    public ProgramState(IStack<IStatement> executionStack, IDictionary<String, Value> symbolTable, IList<Value> output, IStatement statement) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.statement = statement.deepCopy();
        this.executionStack.push(this.statement);
    }

    public boolean isCompleted() {
        return executionStack.isEmpty(); // verifies if the program is completed aka the execution stack is empty
    }

    public String outToString() {
        StringBuilder outputStringBuilder = new StringBuilder();
        for (Value obj : output) {
            outputStringBuilder.append(obj.toString()).append("\n");
        }
        return outputStringBuilder.toString();
    }

    public String symbolTableToString() {
        StringBuilder symTableStringBuilder = new StringBuilder();
        for (String key : this.symbolTable.get_key_set()) {
            symTableStringBuilder.append(String.format("%s : %s\n", key, symbolTable.get(key).toString()));
        }
        return symTableStringBuilder.toString();
    }

    public String exeStackToString() {
        return executionStack.toString();
    }


    public IStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public void setExecutionStack(IStack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public IDictionary<String, Value> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(IDictionary<String, Value> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public IList<Value> getOutput() {
        return output;
    }

    public void setOutput(IList<Value> output) {
        this.output = output;
    }


    @Override
    public String toString() {
        return String.format("======EXE_STACK======\n%s\n======SYM_TABLE======\n%s======OUT======\n%s",
                exeStackToString(),
                symbolTableToString(),
                outToString()
        );
    }

    private IStatement deepCopy(IStatement statement)
    {
        IList<Value> out2 = new MyList<>();
        for (int i = 0 ; i < output.getSize(); i++)
        {
            out2.add(output.get(i));
        }

        return null;
    }
}
