package CarRental.exception;

public class OutOfBand extends RuntimeException{
    public OutOfBand(String message) {
        super(message);
    }
}
