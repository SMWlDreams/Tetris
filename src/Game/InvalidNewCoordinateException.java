package Game;

public class InvalidNewCoordinateException extends RuntimeException {
    public InvalidNewCoordinateException(String message) {
        super(message);
    }

    public InvalidNewCoordinateException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
