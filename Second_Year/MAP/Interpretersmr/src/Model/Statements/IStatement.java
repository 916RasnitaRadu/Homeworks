package model.statements;

import model.programState.ProgramState;
import exceptions.InterpreterException;

import java.io.IOException;

public interface IStatement {
    ProgramState execute(ProgramState state) throws InterpreterException, IOException;
  //  IDictionary<String, Type> type_check(IDictionary<String, Type> type_table) throws InterpreterException;\

    IStatement deepCopy();
}
