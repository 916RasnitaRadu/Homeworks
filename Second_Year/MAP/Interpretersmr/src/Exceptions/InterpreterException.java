package Exceptions;

public class InterpreterException extends Exception{
    private String msg;

    public InterpreterException(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return this.msg;
    }
}
