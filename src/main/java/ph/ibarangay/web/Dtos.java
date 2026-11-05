package ph.ibarangay.web;
import jakarta.validation.constraints.*;
import java.time.*;
public final class Dtos {
 public record RegisterDto(@NotBlank @Size(max=50) String firstName, @Size(max=50) String middleName, @NotBlank @Size(max=50) String lastName,
   LocalDate dateOfBirth, @NotBlank @Size(max=255) String address, @NotBlank @Size(max=20) String contactNo,
   @NotBlank @Email @Size(max=100) String email, @NotBlank @Size(min=8, message="Password must be at least 8 characters") String password) {}
 public record SubmitDto(@NotNull Long documentTypeId, @NotBlank @Size(max=255) String purpose) {}
 public record StatusDto(@NotBlank String status, String remarks) {}
 public record RequestView(Long requestId, String citizenName, String documentType, String purpose, String status, String remarks,
   LocalDateTime requestDate, LocalDateTime processedAt, LocalDateTime releasedAt) {}
}
