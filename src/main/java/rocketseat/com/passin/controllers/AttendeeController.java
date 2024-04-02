package rocketseat.com.passin.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendees")
public class AttendeeController {
	
	@GetMapping
	public ResponseEntity<String> getTest() {
		return ResponseEntity.ok("attendees success!");
	}


}
