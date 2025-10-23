package ie.atu.week5.service;

import ie.atu.week5.model.Passenger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {
    // In-memory storage for passengers
    private final List<Passenger> store = new ArrayList<>();

    // Retrieve all passengers
    public List<Passenger> findAll() {
        return new ArrayList<>(store); // defensive copy
    }

    // Find a passenger by ID
    // Optional<T> represents “a value that may or may not be present,” avoiding raw null.
    public Optional<Passenger> findById(String id) {
        for (Passenger p : store) {
            if (p.getPassengerId().equals(id)) { // Check each passenger's ID
                return Optional.of(p); // Return passenger wrapped in Optional
            }
        }
        return Optional.empty(); // Not found
    }

    // Create a new passenger
    public Passenger create(Passenger p) {
        if (findById(p.getPassengerId()).isPresent()) {
            throw new IllegalStateException("passengerId already exists");
        }
        store.add(p); // Add passenger to the store
        return p; // Return the created passenger
    }

    // Update an existing passenger by ID
    // Returns Optional containing the updated passenger if found, or empty if not
    public Optional<Passenger> update(String id, Passenger updated) {
        return (findById(id).map(existing -> {
            existing.setName(updated.getName()); // Update name
            existing.setEmail(updated.getEmail()); // Update email
            return existing; // return updated passenger
        }));
    }

    // Delete a passenger by ID
    // Returns true if a passenger was removed, false otherwise
    public boolean deleteById(String id) {
        return store.removeIf(p -> p.getPassengerId().equals(id)); // Remove matching passenger(s)
    }
}