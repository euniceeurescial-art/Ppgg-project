package ph.ibarangay.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ph.ibarangay.model.*;
import ph.ibarangay.repo.*;
import ph.ibarangay.web.Dtos.*;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class RequestService {
  static final Map<String,Set<String>> NEXT = Map.of(
    "PENDING", Set.of("APPROVED","REJECTED","CANCELLED"), "APPROVED", Set.of("READY","CANCELLED"), "READY", Set.of("RELEASED"));
  private final CitizenRepo citizens; private final AdminRepo admins; private final DocumentTypeRepo types;
  private final DocumentRequestRepo reqs; private final HistoryRepo history; private final PasswordEncoder enc;
  public RequestService(CitizenRepo c, AdminRepo a, DocumentTypeRepo t, DocumentRequestRepo r, HistoryRepo h, PasswordEncoder e){
    citizens=c; admins=a; types=t; reqs=r; history=h; enc=e; }

  public List<DocumentType> activeTypes(){ return types.findByActiveTrue(); }

  @Transactional public void register(RegisterDto d){
    if (citizens.findByEmail(d.email()).isPresent()) throw new IllegalArgumentException("Email is already registered");
    Citizen c = new Citizen();
    c.firstName=d.firstName(); c.middleName=d.middleName(); c.lastName=d.lastName(); c.dateOfBirth=d.dateOfBirth();
    c.address=d.address(); c.contactNo=d.contactNo(); c.email=d.email(); c.passwordHash=enc.encode(d.password());
    citizens.save(c);
  }
  private Citizen citizen(String email){ return citizens.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Citizen not found")); }

  @Transactional public RequestView submit(String email, SubmitDto d){
    Citizen c = citizen(email);
    DocumentType t = types.findById(d.documentTypeId()).filter(x -> x.active)
      .orElseThrow(() -> new IllegalArgumentException("Document type is not available"));
    DocumentRequest r = new DocumentRequest();
    r.citizenId=c.id; r.documentTypeId=t.id; r.purpose=d.purpose(); r.status="PENDING"; r.requestDate=LocalDateTime.now();
    return view(reqs.save(r));
  }
  public List<RequestView> mine(String email){ return reqs.findByCitizenIdOrderByIdDesc(citizen(email).id).stream().map(this::view).toList(); }
  public List<RequestView> all(){ return reqs.findAllByOrderByIdDesc().stream().map(this::view).toList(); }

  /** citizenEmail = null for admin/staff (may see any request). */
  public List<RequestStatusHistory> history(Long id, String citizenEmail){
    DocumentRequest r = reqs.findById(id).orElseThrow(() -> new IllegalArgumentException("Request not found"));
    if (citizenEmail != null && !r.citizenId.equals(citizen(citizenEmail).id)) throw new IllegalArgumentException("Request not found");
    return history.findByRequestIdOrderByChangedAtAsc(id);
  }

  @Transactional public RequestView update(Long id, String adminUsername, StatusDto d){
    Admin a = admins.findByUsername(adminUsername).orElseThrow(() -> new IllegalArgumentException("Admin not found"));
    DocumentRequest r = reqs.findById(id).orElseThrow(() -> new IllegalArgumentException("Request not found"));
    String ns = d.status().trim().toUpperCase();
    if (!NEXT.getOrDefault(r.status, Set.of()).contains(ns)) throw new IllegalArgumentException("Cannot change " + r.status + " to " + ns);
    boolean hasRemarks = d.remarks() != null && !d.remarks().isBlank();
    if (ns.equals("REJECTED") && !hasRemarks) throw new IllegalArgumentException("A reason is required when rejecting a request");
    RequestStatusHistory h = new RequestStatusHistory();
    h.requestId=id; h.adminId=a.id; h.oldStatus=r.status; h.newStatus=ns; h.remarks=d.remarks(); h.changedAt=LocalDateTime.now();
    history.save(h);
    r.status=ns; if (hasRemarks) r.remarks=d.remarks();
    r.processedAt=LocalDateTime.now();
    if (ns.equals("APPROVED")) r.approvedBy=a.id;
    if (ns.equals("RELEASED")) r.releasedAt=LocalDateTime.now();
    return view(reqs.save(r));
  }

  private RequestView view(DocumentRequest r){
    Citizen c = citizens.findById(r.citizenId).orElseThrow(); DocumentType t = types.findById(r.documentTypeId).orElseThrow();
    return new RequestView(r.id, c.firstName+" "+c.lastName, t.name, r.purpose, r.status, r.remarks, r.requestDate, r.processedAt, r.releasedAt);
  }
}
