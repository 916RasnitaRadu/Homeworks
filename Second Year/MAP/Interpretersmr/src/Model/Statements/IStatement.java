package Model.Statements;

import Model.ProgramState.ProgramState;
import Exceptions.InterpreterException;
import Model.ADTs.IDictionary;
import Model.Types.Type;

public interface IStatement {
    ProgramState execute(ProgramState state)  throws InterpreterException;
  //  IDictionary<String, Type> type_check(IDictionary<String, Type> type_table) throws InterpreterException;\

    IStatement deepCopy();
}
