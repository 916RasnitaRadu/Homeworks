package repository;

import exceptions.InterpreterException;
import model.programState.ProgramState;

import java.util.List;
import java.io.IOException;

public interface IRepository {
    void setProgramList(List<ProgramState> programStates);

    List<ProgramState> getProgramList();

    void logProgramStateExecution(ProgramState programState) throws InterpreterException, IOException;

    ProgramState getProgramWithId(Integer id);

    void addProgram(ProgramState newProgram);
}
