package View;

import Controller.Controller;
import Exceptions.InterpreterException;

import java.io.IOException;

public class RunExample extends Command{
    private Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.allStep();
        } catch (InterpreterException | IOException ie) {
            System.out.println(ie.getMessage());
        }
    }
}