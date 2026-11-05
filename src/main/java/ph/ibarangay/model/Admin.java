package ph.ibarangay.model;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;

@Entity @Table(name="admins")
public class Admin {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="admin_id") public Long id;
 @Column(name="full_name") public String fullName;
 public String username;
 public String role;
 @Column(name="is_active") public boolean active = true;
 @JsonIgnore @Column(name="password_hash") public String passwordHash;
}
