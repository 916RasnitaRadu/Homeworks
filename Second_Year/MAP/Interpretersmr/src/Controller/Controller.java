package Controller;

import Exceptions.InterpreterException;
import Model.ADTs.*;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Values.Value;
import Repository.IRepository;

public class Controller {
    IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public IRepository getRepository() {
        return repository;
    }

    public ProgramState oneStep(ProgramState state) throws InterpreterException
    {
        IStack<IStatement> stack = state.getExecutionStack();

        if (stack.isEmpty()) { throw new InterpreterException("ERROR: Program State stack is empty.");}
        IStatement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    public void allStep() throws InterpreterException {
        ProgramState currentProgramState = repository.getCurrentProgram();
        displayProgramState(currentProgramState);

        while (!currentProgramState.getExecutionStack().isEmpty())
        {
            this.oneStep(currentProgramState);
            displayProgramState(currentProgramState);
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
        executionStack.push(statement);
        ProgramState newProgState = new ProgramState(executionStack,symTable,output);
        this.repository.setCurrentProgram(newProgState);
    }

}

