package ie.atu.week5.service;

import ie.atu.week5.model.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

class PassengerServiceTest {

    private PassengerService service;

    // Runs before each test to ensure a fresh, empty service
    @BeforeEach
    void setUp() { service = new PassengerService(); }

    // Test creating a passenger and then receiving it by ID
    @Test
    void createThenFindById() {
        Passenger p = Passenger.builder()
                .passengerId("P1")
                .name("Mia")
                .email("mia@atu.ie")
                .build();

        service.create(p); // Add passenger

        Optional<Passenger> found = service.findById("P1"); // Retrieve passenger
        assertTrue(found.isPresent()); // Ensure it exists
        assertEquals("Mia", found.get().getName()); // Ensure name matches
    }

    // Test that creating a passenger with a duplicate ID throws an exception
    @Test
    void duplicateIdThrows() {
        service.create(Passenger.builder()
                .passengerId("P2")
                .name("Bob")
                .email("b@atu.ie")
                .build());

        assertThrows(IllegalStateException.class, () ->
                service.create(Passenger.builder()
                        .passengerId("P2") // Duplicate ID
                        .name("Bobby")
                        .email("bob@ex.com")
                        .build()));
    }

    // Update Success Test
    @Test
    void updateExistingPassenger() {
        Passenger p = Passenger.builder()
                .passengerId("P3")
                .name("Abigail")
                .email("abigail@atu.ie")
                .build();

        service.create(p); // Add passenger

        Passenger updated = Passenger.builder()
                .passengerId("P3")
                .name("Abi")
                .email("abi@atu.ie")
                .build();

        Optional<Passenger> result = service.update("P3", updated); // Perform update
        assertTrue(result.isPresent()); // Ensure update succeeded

        Optional<Passenger> found = service.findById("P3"); // Retrieve updated passenger
        assertTrue(found.isPresent());
        assertEquals("Abi", found.get().getName()); // Name updated
        assertEquals("abi@atu.ie", found.get().getEmail()); // Email updated
    }

    // Update Not Found Test
    @Test
    void updateNonExistingPassengerReturnFalse() {
        Passenger updated = Passenger.builder()
                .passengerId("P10")
                .name("Random")
                .email("random@atu.ie")
                .build();

        Optional<Passenger> result = service.update("P10", updated);
        assertTrue(result.isEmpty()); // Update should fail since passenger does not exist
    }

    // Delete Success Test
    @Test
    void deleteExistingPassenger() {
        Passenger p = Passenger.builder()
                .passengerId("P4")
                .name("Isabel")
                .email("isabel@atu.ie")
                .build();

        service.create(p); // Add passenger

        boolean removed = service.deleteById("P4"); // Delete by ID
        assertTrue(removed); // Ensure deletion succeeded
        assertTrue(service.findById("P4").isEmpty()); // Ensure passenger no longer exists
    }

    // Delete Not Found Test
    @Test
    void deleteNonExistingPassengerReturnFalse() {
        boolean removed = service.deleteById("P10"); // Attempt to delete non-existing passenger
        assertFalse(removed); // Ensure deletion fails
    }
}