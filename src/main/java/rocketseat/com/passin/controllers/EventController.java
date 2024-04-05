package rocketseat.com.passin.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.dto.attendee.AttendeeIdDTO;
import rocketseat.com.passin.dto.attendee.AttendeeRequestDTO;
import rocketseat.com.passin.dto.attendee.AttendeesListResponseDTO;
import rocketseat.com.passin.dto.event.EventIdDTO;
import rocketseat.com.passin.dto.event.EventRequestDTO;
import rocketseat.com.passin.dto.event.EventResponseDTO;
import rocketseat.com.passin.services.AttendeeService;
import rocketseat.com.passin.services.EventService;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
	private final EventService eventService;
	private final AttendeeService attendeeService;

	@PostMapping
	public ResponseEntity<EventIdDTO> createEvent(
			@RequestBody EventRequestDTO body,
			UriComponentsBuilder uriComponentsBuilder) {
		EventIdDTO eventIdDTO = this.eventService.createEvent(body);
		var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.eventId()).toUri();
		return ResponseEntity.created(uri).body(eventIdDTO);
	}

	@PostMapping("/{eventId}/attendees")
	public ResponseEntity<AttendeeIdDTO> registerParticipant(
			@PathVariable String eventId,
			@RequestBody AttendeeRequestDTO body,
			UriComponentsBuilder uriComponentsBuilder) {
		AttendeeIdDTO attendeeIdDTO = this.eventService.registerAttendeeOnEvent(eventId, body);
		var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeIdDTO.attendeeId())
				.toUri();
		return ResponseEntity.created(uri).body(attendeeIdDTO);
	}

	@GetMapping
	public ResponseEntity<List<EventResponseDTO>> getEvents() {
		List<EventResponseDTO> events = this.eventService.getAllEvents();
		return ResponseEntity.ok(events);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id) {
		EventResponseDTO event = this.eventService.getEventDetail(id);
		return ResponseEntity.ok(event);
	}

	@GetMapping("/attendees/{id}")
	public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(@PathVariable String id) {
		AttendeesListResponseDTO attendees = this.attendeeService.getEventsAttendee(id);
		return ResponseEntity.ok(attendees);
	}

}
