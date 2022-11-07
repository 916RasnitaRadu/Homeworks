package Repository;

import Model.ProgramState.ProgramState;

import java.util.List;

public interface IRepository {
    void setCurrentProgram(ProgramState currentProgram);

    ProgramState getCurrentProgram();


}
