package rocketseat.com.passin.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import rocketseat.com.passin.domain.event.Event;

public interface EventRepository extends JpaRepository<Event, String> {
    Optional<Event> findBySlug(String slug);
}
