package ph.ibarangay.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import ph.ibarangay.model.*;
import java.util.*;
public interface DocumentRequestRepo extends JpaRepository<DocumentRequest,Long>{ List<DocumentRequest> findByCitizenIdOrderByIdDesc(Long citizenId); List<DocumentRequest> findAllByOrderByIdDesc(); }
