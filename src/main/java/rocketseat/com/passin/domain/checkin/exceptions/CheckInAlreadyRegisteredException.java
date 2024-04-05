package rocketseat.com.passin.domain.checkin.exceptions;

public class CheckInAlreadyRegisteredException extends RuntimeException {
    public CheckInAlreadyRegisteredException(String message) {
        super(message);
    }
}
