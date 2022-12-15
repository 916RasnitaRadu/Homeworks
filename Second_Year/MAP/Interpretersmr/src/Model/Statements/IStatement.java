package model.statements;

import model.programState.ProgramState;
import model.types.Type;
import model.adts.IDictionary;
import exceptions.InterpreterException;

import java.io.IOException;

public interface IStatement {
    ProgramState execute(ProgramState state) throws InterpreterException, IOException;
    IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeTable) throws InterpreterException;

    IStatement deepCopy();
}
