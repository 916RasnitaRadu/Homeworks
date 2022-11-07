public class NegativeWeightException extends Exception{
    private String message;
    public NegativeWeightException(String s) {
        this.message = s;
    }

    String get_message() { return message;}

}
