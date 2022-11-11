package Repository;

import Model.ProgramState.ProgramState;

public class Repository implements IRepository {
    private ProgramState currentProgram;

    @Override
    public void setCurrentProgram(ProgramState currentProgram) {
        this.currentProgram = currentProgram;
    }

    @Override
    public ProgramState getCurrentProgram() {
        return this.currentProgram;
    }

}
