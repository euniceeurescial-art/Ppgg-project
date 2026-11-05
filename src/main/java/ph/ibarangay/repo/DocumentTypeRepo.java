package ph.ibarangay.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import ph.ibarangay.model.*;
import java.util.*;
public interface DocumentTypeRepo extends JpaRepository<DocumentType,Long>{ List<DocumentType> findByActiveTrue(); }
