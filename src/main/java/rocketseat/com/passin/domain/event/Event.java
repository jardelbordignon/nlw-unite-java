package rocketseat.com.passin.domain.event;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(nullable = false)
	private String id;
	
	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String details;

	@Column(nullable = false, unique = true)
	private String slug;

	@Column(nullable = false, name = "maximum_attendees")
	private Integer maximumAttendees;
}
