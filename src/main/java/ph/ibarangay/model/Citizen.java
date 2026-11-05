package ph.ibarangay.model;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;

@Entity @Table(name="citizens")
public class Citizen {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="citizen_id") public Long id;
 @Column(name="first_name") public String firstName;
 @Column(name="middle_name") public String middleName;
 @Column(name="last_name") public String lastName;
 @Column(name="date_of_birth") public LocalDate dateOfBirth;
 public String address;
 @Column(name="contact_no") public String contactNo;
 public String email;
 @Column(name="created_at",insertable=false,updatable=false) public LocalDateTime createdAt;
 @JsonIgnore @Column(name="password_hash") public String passwordHash;
}
