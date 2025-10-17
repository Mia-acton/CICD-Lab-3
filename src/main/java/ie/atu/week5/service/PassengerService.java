package ie.atu.week5.service;

import ie.atu.week5.model.Passenger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {
    private final List<Passenger> store = new ArrayList<>();

    public List<Passenger> findAll() {
        return new ArrayList<>(store); // defensive copy
    }

    // Optional<T> represents “a value that may or may not be present,” avoiding raw null.
    public Optional<Passenger> findById(String id) {
        for (Passenger p : store) {
            if (p.getPassengerId().equals(id)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public Passenger create(Passenger p) {
        if (findById(p.getPassengerId()).isPresent()) {
            throw new IllegalStateException("passengerId already exists");
        }
        store.add(p);
        return p;
    }

    // PUT
    public Optional<Passenger> update(String id, Passenger updated) {
        return (findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setEmail(updated.getEmail());
            return existing;
        }));
    }

    // DELETE
    public boolean deleteById(String id) {
        return store.removeIf(p -> p.getPassengerId().equals(id));
    }
}