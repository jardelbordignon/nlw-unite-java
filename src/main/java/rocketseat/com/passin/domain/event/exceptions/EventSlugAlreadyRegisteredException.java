package rocketseat.com.passin.domain.event.exceptions;

public class EventSlugAlreadyRegisteredException extends RuntimeException {
    public EventSlugAlreadyRegisteredException(String message) {
        super(message);
    }
}
