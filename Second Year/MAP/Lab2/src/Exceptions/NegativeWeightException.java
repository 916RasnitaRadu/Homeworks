package Exceptions;

public class NegativeWeightException extends Exception{
    private String message;
    public NegativeWeightException(String s) {
        this.message = s;
    }

    public String get_message() { return message;}
}
