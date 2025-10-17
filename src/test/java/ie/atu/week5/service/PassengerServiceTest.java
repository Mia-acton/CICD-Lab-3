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

    // Update Test
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

        Optional<Passenger> existing = service.findById("P3");
        assertTrue(existing.isPresent());
        assertEquals("Abigail", existing.get().getName());
    }
}