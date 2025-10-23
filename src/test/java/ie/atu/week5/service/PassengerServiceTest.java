package ie.atu.week5.service;

import ie.atu.week5.model.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

class PassengerServiceTest {

    private PassengerService service;

    @BeforeEach
    void setUp() { service = new PassengerService(); }

    @Test
    void createThenFindById() {
        Passenger p = Passenger.builder()
                .passengerId("P1")
                .name("Mia")
                .email("mia@atu.ie")
                .build();

        service.create(p);

        Optional<Passenger> found = service.findById("P1");
        assertTrue(found.isPresent());
        assertEquals("Mia", found.get().getName());
    }

    @Test
    void duplicateIdThrows() {
        service.create(Passenger.builder()
                .passengerId("P2")
                .name("Bob")
                .email("b@atu.ie")
                .build());

        assertThrows(IllegalStateException.class, () ->
                service.create(Passenger.builder()
                        .passengerId("P2")
                        .name("Bobby")
                        .email("bob@ex.com")
                        .build()));
    }

    // Update Success
    @Test
    void updateExistingPassenger() {
        Passenger p = Passenger.builder()
                .passengerId("P3")
                .name("Abigail")
                .email("abigail@atu.ie")
                .build();

        service.create(p);

        Passenger updated = Passenger.builder()
                .passengerId("P3")
                .name("Abi")
                .email("abi@atu.ie")
                .build();

        Optional<Passenger> result = service.update("P3", updated);
        assertTrue(result.isPresent());

        Optional<Passenger> found = service.findById("P3");
        assertTrue(found.isPresent());
        assertEquals("Abi", found.get().getName());
        assertEquals("abi@atu.ie", found.get().getEmail());
    }

    // Update Not Found
    @Test
    void updateNonExistingPassengerReturnFalse() {
        Passenger updated = Passenger.builder()
                .passengerId("P10")
                .name("Random")
                .email("random@atu.ie")
                .build();

        Optional<Passenger> result = service.update("P10", updated);
        assertTrue(result.isEmpty());
    }

    // Delete Success
    @Test
    void deleteExistingPassenger() {
        Passenger p = Passenger.builder()
                .passengerId("P4")
                .name("Isabel")
                .email("isabel@atu.ie")
                .build();

        service.create(p);

        boolean removed = service.deleteById("P4");
        assertTrue(removed);
        assertTrue(service.findById("P4").isEmpty());
    }

    // Delete Not Found
    @Test
    void deleteNonExistingPassengerReturnFalse() {
        boolean removed = service.deleteById("P10");
        assertFalse(removed);
    }
}