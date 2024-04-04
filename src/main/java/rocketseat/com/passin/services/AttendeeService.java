package rocketseat.com.passin.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.domain.attendee.Attendee;
import rocketseat.com.passin.domain.checkin.CheckIn;
import rocketseat.com.passin.dto.attendee.AttendeeDetailsDTO;
import rocketseat.com.passin.dto.attendee.AttendeesListResponseDTO;
import rocketseat.com.passin.repositories.AttendeeRepository;
import rocketseat.com.passin.repositories.CheckinRepository;

@Service
@RequiredArgsConstructor
public class AttendeeService {
	private final AttendeeRepository attendeeRepository;
	private final CheckinRepository checkinRepository;

	public List<Attendee> getAllAttendeesFromEvent(String eventId) {
		return this.attendeeRepository.findByEventId(eventId);
	}
	
	public AttendeesListResponseDTO getEventsAttendee(String eventId) {
		List<Attendee> attendees = this.getAllAttendeesFromEvent(eventId);
		
		List<AttendeeDetailsDTO> attendeeDetailsList = attendees.stream().map(attendee -> {
			Optional<CheckIn> checkIn = this.checkinRepository.findByAttendeeId(attendee.getId());
			LocalDateTime checkedInATime = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;
			return new AttendeeDetailsDTO(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInATime);
		}).toList();
		
		return new AttendeesListResponseDTO(attendeeDetailsList);
	}
	
}
