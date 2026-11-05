package ph.ibarangay.config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ph.ibarangay.model.*;
import ph.ibarangay.repo.*;

/** Creates FICTIONAL demo accounts on first run. Set app.seed-demo=false to disable. */
@Component @ConditionalOnProperty(name = "app.seed-demo", havingValue = "true")
public class DataSeeder implements CommandLineRunner {
  private final AdminRepo admins; private final CitizenRepo citizens; private final PasswordEncoder enc;
  public DataSeeder(AdminRepo a, CitizenRepo c, PasswordEncoder e){ admins=a; citizens=c; enc=e; }
  public void run(String... args){
    if (admins.findByUsername("admin").isEmpty()) { admin("System Admin","admin","ADMIN","Admin@123"); admin("Staff Member","staff","STAFF","Staff@123"); }
    if (citizens.findByEmail("juan@example.com").isEmpty()) {
      Citizen c = new Citizen(); c.firstName="Juan"; c.lastName="Dela Cruz"; c.address="123 Sample St."; c.contactNo="09170000000";
      c.email="juan@example.com"; c.passwordHash=enc.encode("Citizen@123"); citizens.save(c); }
  }
  private void admin(String name, String user, String role, String pw){
    Admin a = new Admin(); a.fullName=name; a.username=user; a.role=role; a.passwordHash=enc.encode(pw); admins.save(a); }
}
