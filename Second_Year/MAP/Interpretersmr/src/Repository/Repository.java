package Repository;

import Exceptions.InterpreterException;
import Model.ProgramState.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Repository implements IRepository {
    private ProgramState currentProgram;
    private final String logFilePath;

    public Repository(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    @Override
    public void setCurrentProgram(ProgramState currentProgram) {
        this.currentProgram = currentProgram;
    }

    @Override
    public ProgramState getCurrentProgram() {
        return this.currentProgram;
    }

    @Override
    public void logProgramStateExecution(ProgramState programState) throws InterpreterException, IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(programState.toString());
        logFile.close();
    }

}
