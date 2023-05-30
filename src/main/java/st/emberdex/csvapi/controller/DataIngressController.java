package st.emberdex.csvapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import st.emberdex.csvapi.model.document.VehicleDataPointDocument;
import st.emberdex.csvapi.model.request.VehicleDataPointRequest;
import st.emberdex.csvapi.service.VehicleDataPointService;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class DataIngressController {

  private final VehicleDataPointService vehicleDataPointService;

  @PostMapping
  public ResponseEntity<Void> logVehicleDataPoint(
      @RequestBody VehicleDataPointRequest dataPoint,
      Principal principal
  ) {

    vehicleDataPointService.saveNewDataPoint(dataPoint, principal.getName());

    return ResponseEntity.ok().build();
  }

  @GetMapping("/all")
  public ResponseEntity<List<VehicleDataPointDocument>> getDataPoints(Principal principal) {

    return ResponseEntity.ok(
        vehicleDataPointService.getDataPointsForTenant(
            principal.getName()));
  }

  @DeleteMapping("/all")
  public ResponseEntity<Void> purgeDataPointsForTenant(Principal principal) {

    vehicleDataPointService.purgeTenantDataPoints(principal.getName());

    return ResponseEntity.ok(null);
  }
}
