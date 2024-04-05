package rocketseat.com.passin.services;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.domain.attendee.Attendee;
import rocketseat.com.passin.domain.event.Event;
import rocketseat.com.passin.domain.event.exceptions.EventMaximumAttendeesException;
import rocketseat.com.passin.domain.event.exceptions.EventNotFoundException;
import rocketseat.com.passin.domain.event.exceptions.EventSlugAlreadyRegisteredException;
import rocketseat.com.passin.dto.attendee.AttendeeIdDTO;
import rocketseat.com.passin.dto.attendee.AttendeeRequestDTO;
import rocketseat.com.passin.dto.event.EventIdDTO;
import rocketseat.com.passin.dto.event.EventRequestDTO;
import rocketseat.com.passin.dto.event.EventResponseDTO;
import rocketseat.com.passin.repositories.EventRepository;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;
	private final AttendeeService attendeeService;

	public List<EventResponseDTO> getAllEvents() {
		List<Event> events = this.eventRepository.findAll();
		return events.stream()
				.map(event -> new EventResponseDTO(event,
						this.attendeeService.getAllAttendeesFromEvent(event.getId()).size()))
				.collect(Collectors.toList());
	}

	public EventResponseDTO getEventDetail(String eventId) {
		Event event = this.getEventById(eventId);

		List<Attendee> attendees = this.attendeeService.getAllAttendeesFromEvent(eventId);

		return new EventResponseDTO(event, attendees.size());
	}

	public EventIdDTO createEvent(EventRequestDTO eventDTO) {
		String slug = this.slugfy(eventDTO.title());

		// Optional<Event> event = this.eventRepository.findBySlug(slug);
		// if (event.isPresent())
		// throw new EventSlugAlreadyRegisteredException("Event slug already
		// registered");
		if (this.eventRepository.findBySlug(slug).isPresent())
			throw new EventSlugAlreadyRegisteredException("Event with '" + slug + "' slug already registered");

		Event newEvent = new Event();
		newEvent.setTitle(eventDTO.title());
		newEvent.setDetails(eventDTO.details());
		newEvent.setMaximumAttendees(eventDTO.maximumAttendees());
		newEvent.setSlug(slug);

		this.eventRepository.save(newEvent);

		return new EventIdDTO(newEvent.getId());
	}

	public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeDTO) {
		this.attendeeService.verifyAttendeeSubscription(attendeeDTO.email(), eventId);

		Event event = this.getEventById(eventId);
		List<Attendee> attendees = this.attendeeService.getAllAttendeesFromEvent(eventId);

		if (attendees.size() >= event.getMaximumAttendees())
			throw new EventMaximumAttendeesException("Maximum attendees reached");

		Attendee newAttendee = new Attendee();
		newAttendee.setName(attendeeDTO.name());
		newAttendee.setEmail(attendeeDTO.email());
		newAttendee.setEvent(event);
		newAttendee.setCreatedAt(LocalDateTime.now());

		this.attendeeService.registerAttendee(newAttendee);

		return new AttendeeIdDTO(newAttendee.getId());
	}

	private Event getEventById(String eventId) {
		return this.eventRepository
				.findById(eventId)
				.orElseThrow(() -> new EventNotFoundException("Event not found with ID:" + eventId));
	}

	private String slugfy(String text) {
		return Normalizer
				.normalize(text, Normalizer.Form.NFD)
				.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "") // remove accents
				.replaceAll("[^\\w\\s]", "") // remove non alphanumeric
				.replaceAll("\\s+", "-") // replace spaces to -
				.toLowerCase();
	}
}
