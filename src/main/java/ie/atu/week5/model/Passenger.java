package ie.atu.week5.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

// @Builder generates the Builder pattern for your class, so you can construct objects with named, chainable setters instead of a long constructor.
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Passenger {
    @NotBlank @Size(max = 40)
    private String passengerId;

    @NotBlank @Size(max = 60)
    private String name;

    @NotBlank @Email
    private String email;
}
