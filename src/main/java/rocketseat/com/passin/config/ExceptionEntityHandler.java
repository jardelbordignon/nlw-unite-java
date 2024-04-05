package rocketseat.com.passin.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import rocketseat.com.passin.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import rocketseat.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import rocketseat.com.passin.domain.checkin.exceptions.CheckInAlreadyRegisteredException;
import rocketseat.com.passin.domain.event.exceptions.EventMaximumAttendeesException;
import rocketseat.com.passin.domain.event.exceptions.EventNotFoundException;
import rocketseat.com.passin.domain.event.exceptions.EventSlugAlreadyRegisteredException;
import rocketseat.com.passin.dto.common.ErrorResponseDTO;

@ControllerAdvice // indica que a classe irá capturar as exceções lançadas pelos controllers
public class ExceptionEntityHandler {

	@ExceptionHandler(EventNotFoundException.class)
	public ResponseEntity<?> handleEventNotFound(EventNotFoundException exception) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(EventMaximumAttendeesException.class)
	public ResponseEntity<ErrorResponseDTO> handleEventMaximumAttendeesException(
			EventMaximumAttendeesException exception) {
		return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
	}

	@ExceptionHandler(EventSlugAlreadyRegisteredException.class)
	public ResponseEntity<ErrorResponseDTO> handleEventSlugAlreadyRegisteredException(
			EventSlugAlreadyRegisteredException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(exception.getMessage()));
	}

	@ExceptionHandler(AttendeeNotFoundException.class)
	public ResponseEntity<?> handleAttendeeNotFound(AttendeeNotFoundException exception) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(AttendeeAlreadyRegisteredException.class)
	public ResponseEntity<?> handleAttendeeAlreadyRegisteredException(AttendeeAlreadyRegisteredException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}

	@ExceptionHandler(CheckInAlreadyRegisteredException.class)
	public ResponseEntity<?> handleCheckInAlreadyRegisteredException(CheckInAlreadyRegisteredException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
}
