package st.emberdex.csvapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import st.emberdex.csvapi.model.document.VehicleDataPointDocument;
import st.emberdex.csvapi.service.VehicleDataPointService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/query")
@RequiredArgsConstructor
public class DataQueryController {

  private final VehicleDataPointService vehicleDataPointService;

  @GetMapping("/all")
  public ResponseEntity<Map<String, Object>> getDataPoints(Principal principal) {

    List<VehicleDataPointDocument> results = vehicleDataPointService.getDataPointsForTenant(principal.getName());

    Map<String, Object> responseBody = Map.of(
        "count", results.size(),
        "results", results
    );

    return ResponseEntity.ok(responseBody);
  }

  @DeleteMapping("/all")
  public ResponseEntity<Void> purgeDataPointsForTenant(Principal principal) {

    vehicleDataPointService.purgeTenantDataPoints(principal.getName());

    return ResponseEntity.ok(null);
  }
}
