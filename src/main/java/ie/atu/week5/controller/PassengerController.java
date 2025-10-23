package ie.atu.week5.controller;

import ie.atu.week5.model.Passenger;
import ie.atu.week5.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI; // For building location URIs
import java.util.Optional;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService service; // Constructor DI

    // Constructor-based dependency injection
    public PassengerController(PassengerService service){ this.service = service; }

    // Retrieve a single passenger by id
    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getOne(@PathVariable String id){
        Optional<Passenger> maybe = service.findById(id); // Call service to find passenger
        if (maybe.isPresent()){
            return ResponseEntity.ok(maybe.get()); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    // Create a new passenger
    @PostMapping
    public ResponseEntity<Passenger> create(@Valid @RequestBody Passenger p){
        Passenger created =  service.create(p); // Call service to create passenger
        return ResponseEntity
                .created(URI.create("/api/passengers/" + created.getPassengerId())) // 201 created with location header
                .body(created); // Include the newly created passenger in the response body
    }

    // PUT /api/passengers/{id} - update name/email if id exists; return 404 otherwise.
    @PutMapping("/{id}")
    public ResponseEntity<Passenger> update(@PathVariable String id, @Valid @RequestBody Passenger p) {
        Optional<Passenger> updated = service.update(id, p); // Call service to update passenger
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get()); // 200 OK with updated passenger in body
        }
        return ResponseEntity.notFound().build();  // 404 Not Found if passenger doesn't exist
    }

    // DELETE /api/passengers/{id} - 204 if removed; 404 otherwise.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = service.deleteById(id); // Call service to delete passenger
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content for successful deletion
        }
        return ResponseEntity.notFound().build(); // 404 Not Found if passenger doesn't exist
    }
}