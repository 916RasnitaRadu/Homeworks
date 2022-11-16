package Repository;

import Exceptions.InterpreterException;
import Model.ProgramState.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    void setCurrentProgram(ProgramState currentProgram);

    ProgramState getCurrentProgram();

    void logProgramStateExecution(ProgramState programState) throws InterpreterException, IOException;

}
