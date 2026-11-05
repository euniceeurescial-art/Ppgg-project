package ph.ibarangay.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import ph.ibarangay.model.*;
import java.util.*;
public interface AdminRepo extends JpaRepository<Admin,Long>{ Optional<Admin> findByUsername(String username); }
