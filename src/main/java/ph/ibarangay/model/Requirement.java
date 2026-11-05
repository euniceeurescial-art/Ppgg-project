package ph.ibarangay.model;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;

@Entity @Table(name="requirements")
public class Requirement {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="requirement_id") public Long id;
 public String name;
 public String description;
 @Column(name="is_active") public boolean active;
}
