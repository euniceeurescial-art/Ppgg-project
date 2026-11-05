package ph.ibarangay.model;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;

@Entity @Table(name="document_requests")
public class DocumentRequest {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="request_id") public Long id;
 @Column(name="citizen_id") public Long citizenId;
 @Column(name="document_type_id") public Long documentTypeId;
 @Column(name="request_date") public LocalDateTime requestDate;
 public String purpose;
 public String status;
 public String remarks;
 @Column(name="approved_by") public Long approvedBy;
 @Column(name="processed_at") public LocalDateTime processedAt;
 @Column(name="released_at") public LocalDateTime releasedAt;
}
