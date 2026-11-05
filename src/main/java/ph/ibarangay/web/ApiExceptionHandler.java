package ph.ibarangay.web;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(IllegalArgumentException.class)
  ResponseEntity<Map<String,String>> bad(IllegalArgumentException e){ return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<Map<String,String>> invalid(MethodArgumentNotValidException e){
    var f = e.getBindingResult().getFieldError();
    return ResponseEntity.badRequest().body(Map.of("error", f == null ? "Invalid input" : f.getField() + ": " + f.getDefaultMessage())); }
}
