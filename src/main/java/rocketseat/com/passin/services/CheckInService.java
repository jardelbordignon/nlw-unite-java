package rocketseat.com.passin.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.domain.attendee.Attendee;
import rocketseat.com.passin.domain.checkin.CheckIn;
import rocketseat.com.passin.domain.checkin.exceptions.CheckInAlreadyRegisteredException;
import rocketseat.com.passin.repositories.CheckinRepository;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final CheckinRepository checkinRepository;

    public void registerCheckIn(Attendee attendee) {
        this.verifyAttendeeAlreadyCheckedIn(attendee.getId());

        CheckIn checkIn = new CheckIn();
        checkIn.setAttendee(attendee);
        checkIn.setCreatedAt(LocalDateTime.now());

        this.checkinRepository.save(checkIn);
    }

    private void verifyAttendeeAlreadyCheckedIn(String attendeeId) {
        Optional<CheckIn> checkIn = this.getCheckIn(attendeeId);
        if (checkIn.isPresent())
            throw new CheckInAlreadyRegisteredException("Attendee already checked in");
    }

    public Optional<CheckIn> getCheckIn(String attendeeId) {
        return this.checkinRepository.findByAttendeeId(attendeeId);
    }
}
