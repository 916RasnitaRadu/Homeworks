package Controller;

import Exceptions.InterpreterException;
import Model.ADTs.*;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Values.Value;
import Repository.IRepository;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;

public class Controller {
    IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public IRepository getRepository() {
        return repository;
    }

    public ProgramState oneStep(ProgramState state) throws InterpreterException, IOException {
        IStack<IStatement> stack = state.getExecutionStack();

        if (stack.isEmpty()) { throw new InterpreterException("ERROR: Program State stack is empty.");}
        IStatement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    public void allStep() throws InterpreterException, IOException {
        ProgramState currentProgramState = repository.getCurrentProgram();
        displayProgramState(currentProgramState);
        repository.logProgramStateExecution(currentProgramState);
        while (!currentProgramState.getExecutionStack().isEmpty())
        {
            this.oneStep(currentProgramState);
            displayProgramState(currentProgramState);
            repository.logProgramStateExecution(currentProgramState);
        }
    }

    public void displayProgramState(ProgramState state)
    {
        System.out.println(state.toString());
    }

    public void add(IStatement statement) {
        IStack<IStatement> executionStack = new MyStack<IStatement>();
        IDictionary<String, Value> symTable = new MyDictionary<String, Value>();
        IList<Value> output = new MyList<Value>();
        IDictionary<String, BufferedReader> fileTable = new MyDictionary<>();
        executionStack.push(statement);
        ProgramState newProgState = new ProgramState(executionStack, symTable, output, fileTable);
        this.repository.setCurrentProgram(newProgState);
    }

    public ProgramState getProgramState() { return repository.getCurrentProgram(); }

}

