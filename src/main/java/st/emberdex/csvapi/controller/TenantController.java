package st.emberdex.csvapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tenant")
public class TenantController {

  @GetMapping
  public ResponseEntity<Void> test() {

    return ResponseEntity.ok().build();
  }

}
