package app.components.exception;

public class NotExistCityException extends RuntimeException {

    public NotExistCityException(String message) {
        super(message);
    }
}
