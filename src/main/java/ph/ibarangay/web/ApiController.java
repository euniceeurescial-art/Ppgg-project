package ph.ibarangay.web;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ph.ibarangay.model.*;
import ph.ibarangay.service.RequestService;
import ph.ibarangay.web.Dtos.*;
import java.util.*;

@RestController @RequestMapping("/api")
public class ApiController {
  private final RequestService svc;
  public ApiController(RequestService s){ svc=s; }

  @GetMapping("/document-types") public List<DocumentType> types(){ return svc.activeTypes(); }
  @PostMapping("/register") public Map<String,String> register(@Valid @RequestBody RegisterDto d){ svc.register(d); return Map.of("message","Registered. You can now log in with your email."); }
  @GetMapping("/me") public Map<String,String> me(Authentication a){
    return Map.of("username", a.getName(), "role", a.getAuthorities().iterator().next().getAuthority().replace("ROLE_","")); }

  @PostMapping("/citizen/requests") public RequestView submit(@Valid @RequestBody SubmitDto d, Authentication a){ return svc.submit(a.getName(), d); }
  @GetMapping("/citizen/requests") public List<RequestView> mine(Authentication a){ return svc.mine(a.getName()); }
  @GetMapping("/citizen/requests/{id}/history") public List<RequestStatusHistory> myHistory(@PathVariable Long id, Authentication a){ return svc.history(id, a.getName()); }

  @GetMapping("/admin/requests") public List<RequestView> all(){ return svc.all(); }
  @PutMapping("/admin/requests/{id}/status") public RequestView update(@PathVariable Long id, @Valid @RequestBody StatusDto d, Authentication a){ return svc.update(id, a.getName(), d); }
  @GetMapping("/admin/requests/{id}/history") public List<RequestStatusHistory> history(@PathVariable Long id){ return svc.history(id, null); }
}
