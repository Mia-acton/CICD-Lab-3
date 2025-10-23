package ie.atu.week5.controller;

import ie.atu.week5.model.Passenger;
import ie.atu.week5.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService service; // Constructor DI

    public PassengerController(PassengerService service){ this.service = service; }

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getOne(@PathVariable String id){
        Optional<Passenger> maybe = service.findById(id);
        if (maybe.isPresent()){
            return ResponseEntity.ok(maybe.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Passenger> create(@Valid @RequestBody Passenger p){
        Passenger created =  service.create(p);
        return ResponseEntity
                .created(URI.create("/api/passengers/" + created.getPassengerId()))
                .body(created);
    }

    // PUT /api/passengers/{id} - update name/email if id exists; return 404 otherwise.
    @PutMapping("/{id}")
    public ResponseEntity<Passenger> update(@PathVariable String id, @Valid @RequestBody Passenger p) {
        Optional<Passenger> updated = service.update(id, p);
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get()); // 204
        }
        return ResponseEntity.notFound().build();  // 404
    }

    // DELETE /api/passengers/{id} - 204 if removed; 404 otherwise.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = service.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build(); // 404
    }
}