package ph.ibarangay.config;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }

  @Bean SecurityFilterChain chain(HttpSecurity http) throws Exception {
    http.csrf(c -> c.disable()) // simplified for the class project; enable CSRF tokens for real deployment
      .authorizeHttpRequests(a -> a
        .requestMatchers("/", "/*.html", "/css/**", "/js/**", "/error", "/api/register", "/api/document-types").permitAll()
        .requestMatchers("/api/admin/**").hasAnyRole("ADMIN", "STAFF")   // enforced on the backend, not in JavaScript
        .requestMatchers("/api/citizen/**").hasRole("CITIZEN")
        .anyRequest().authenticated())
      .formLogin(f -> f.loginProcessingUrl("/api/login")
        .successHandler((req, res, auth) -> { res.setContentType("application/json");
          res.getWriter().write("{\"role\":\"" + auth.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "") + "\"}"); })
        .failureHandler((req, res, ex) -> res.sendError(401, "Invalid username or password")))
      .logout(l -> l.logoutUrl("/api/logout").logoutSuccessHandler((req, res, a) -> res.setStatus(200)))
      .exceptionHandling(e -> e.authenticationEntryPoint((req, res, ex) -> res.sendError(401)));
    return http.build();
  }
}
