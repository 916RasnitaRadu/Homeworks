package Model.Statements;

import Model.ProgramState.ProgramState;
import Exceptions.InterpreterException;
import Model.ADTs.IDictionary;
import Model.Types.Type;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IStatement {
    ProgramState execute(ProgramState state) throws InterpreterException, IOException;
  //  IDictionary<String, Type> type_check(IDictionary<String, Type> type_table) throws InterpreterException;\

    IStatement deepCopy();
}
