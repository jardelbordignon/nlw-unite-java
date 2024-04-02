package rocketseat.com.passin.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

	@GetMapping
	public ResponseEntity<String> getTest() {
		return ResponseEntity.ok("events success!");
	}

}
