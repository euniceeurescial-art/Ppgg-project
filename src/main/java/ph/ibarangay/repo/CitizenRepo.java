package ph.ibarangay.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import ph.ibarangay.model.*;
import java.util.*;
public interface CitizenRepo extends JpaRepository<Citizen,Long>{ Optional<Citizen> findByEmail(String email); }
