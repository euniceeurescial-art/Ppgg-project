package ph.ibarangay.model;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;

@Entity @Table(name="request_status_history")
public class RequestStatusHistory {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="history_id") public Long id;
 @Column(name="request_id") public Long requestId;
 @Column(name="admin_id") public Long adminId;
 @Column(name="old_status") public String oldStatus;
 @Column(name="new_status") public String newStatus;
 @Column(name="changed_at") public LocalDateTime changedAt;
 public String remarks;
}
