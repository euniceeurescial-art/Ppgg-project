package ph.ibarangay.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import ph.ibarangay.model.*;
import java.util.*;
public interface HistoryRepo extends JpaRepository<RequestStatusHistory,Long>{ List<RequestStatusHistory> findByRequestIdOrderByChangedAtAsc(Long requestId); }
