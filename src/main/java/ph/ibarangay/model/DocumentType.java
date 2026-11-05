package ph.ibarangay.model;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;

@Entity @Table(name="document_types")
public class DocumentType {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="document_type_id") public Long id;
 public String name;
 public String description;
 @Column(name="processing_days") public int processingDays;
 public BigDecimal fee;
 @Column(name="is_active") public boolean active;
 @ManyToMany(fetch=FetchType.EAGER)
 @JoinTable(name="document_type_requirements", joinColumns=@JoinColumn(name="document_type_id"), inverseJoinColumns=@JoinColumn(name="requirement_id"))
 public List<Requirement> requirements;
}
