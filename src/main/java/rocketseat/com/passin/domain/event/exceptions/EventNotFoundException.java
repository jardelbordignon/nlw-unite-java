package rocketseat.com.passin.domain.event.exceptions;

@SuppressWarnings("serial")
public class EventNotFoundException extends RuntimeException {
	
	public EventNotFoundException(String message) {
		super(message);
	}
}
