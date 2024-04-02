package rocketseat.com.passin.domain.checkin;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

import rocketseat.com.passin.domain.attendee.Attendee;

@Entity
@Table(name = "check_ins")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Checkin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@OneToOne
	@JoinColumn(name = "attendee_id", nullable = false)
	private Attendee attendee;
}
