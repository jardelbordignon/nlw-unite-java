package rocketseat.com.passin.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.domain.attendee.Attendee;
import rocketseat.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import rocketseat.com.passin.domain.checkin.CheckIn;
import rocketseat.com.passin.dto.attendee.AttendeeBadgeResponseDTO;
import rocketseat.com.passin.dto.attendee.AttendeeDetailsDTO;
import rocketseat.com.passin.dto.attendee.AttendeesListResponseDTO;
import rocketseat.com.passin.dto.attendee.AttendeeBadgeDTO;
import rocketseat.com.passin.repositories.AttendeeRepository;

@Service
@RequiredArgsConstructor
public class AttendeeService {
	private final AttendeeRepository attendeeRepository;
	private final CheckInService checkInService;

	public List<Attendee> getAllAttendeesFromEvent(String eventId) {
		return this.attendeeRepository.findByEventId(eventId);
	}

	public AttendeesListResponseDTO getEventsAttendee(String eventId) {
		List<Attendee> attendees = this.getAllAttendeesFromEvent(eventId);

		List<AttendeeDetailsDTO> attendeeDetailsList = attendees.stream().map(attendee -> {
			Optional<CheckIn> checkIn = this.checkInService.getCheckIn(attendee.getId());
			LocalDateTime checkedInATime = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;
			return new AttendeeDetailsDTO(attendee.getId(), attendee.getName(), attendee.getEmail(),
					attendee.getCreatedAt(), checkedInATime);
		}).toList();

		return new AttendeesListResponseDTO(attendeeDetailsList);
	}

	public void verifyAttendeeSubscription(String email, String eventId) {
		Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);
		if (isAttendeeRegistered.isPresent())
			throw new RuntimeException("Attendee already registered");
	}

	public Attendee registerAttendee(Attendee attendee) {
		this.attendeeRepository.save(attendee);
		return attendee;
	}

	public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
		Attendee attendee = this.getAttendeeById(attendeeId);

		var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri()
				.toString();

		AttendeeBadgeDTO badgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri,
				attendee.getEvent().getId());
		return new AttendeeBadgeResponseDTO(badgeDTO);
	}

	public void checkInAttendee(String attendeeId) {
		Attendee attendee = this.getAttendeeById(attendeeId);
		this.checkInService.registerCheckIn(attendee);
	}

	private Attendee getAttendeeById(String attendeeId) {
		return this.attendeeRepository
				.findById(attendeeId)
				.orElseThrow(() -> new AttendeeNotFoundException("Attendee not found With ID: " + attendeeId));
	}
}
