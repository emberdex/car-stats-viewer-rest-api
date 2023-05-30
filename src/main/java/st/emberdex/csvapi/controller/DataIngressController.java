package st.emberdex.csvapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import st.emberdex.csvapi.model.VehicleDataPointRequest;
import st.emberdex.csvapi.service.VehicleDataPointService;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class DataIngressController {

  private final VehicleDataPointService vehicleDataPointService;

  @PostMapping
  public ResponseEntity<Void> logDataPoint(@RequestBody VehicleDataPointRequest dataPoint, Principal principal) {

    vehicleDataPointService.save(dataPoint, principal.getName());

    return ResponseEntity.ok().build();
  }
}
