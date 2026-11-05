package ph.ibarangay.config;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import ph.ibarangay.repo.*;

/** Admins/staff log in with username; citizens log in with email (contains '@'). */
@Service
public class AppUserDetailsService implements UserDetailsService {
  private final AdminRepo admins; private final CitizenRepo citizens;
  public AppUserDetailsService(AdminRepo a, CitizenRepo c){ admins=a; citizens=c; }
  public UserDetails loadUserByUsername(String u) {
    if (!u.contains("@")) {
      var a = admins.findByUsername(u).filter(x -> x.active && x.passwordHash != null).orElseThrow(() -> new UsernameNotFoundException(u));
      return User.withUsername(a.username).password(a.passwordHash).roles(a.role).build();
    }
    var c = citizens.findByEmail(u).filter(x -> x.passwordHash != null).orElseThrow(() -> new UsernameNotFoundException(u));
    return User.withUsername(c.email).password(c.passwordHash).roles("CITIZEN").build();
  }
}
