package st.emberdex.csvapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ingress")
@RequiredArgsConstructor
public class DataIngressController {

  private final VehicleDataPointService vehicleDataPointService;

  @PostMapping
  @Operation(
      summary = "Log vehicle data point",
      description = "Log a data point from the Car Stats Viewer application. This endpoint is the webhook URL used by the in-vehicle application to store data, and is not intended to be called by any other means."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "The data point was logged successfully."),
      @ApiResponse(responseCode = "401", description = "The user is not authorised."),
      @ApiResponse(responseCode = "403", description = "The user is logged in as a tenant admin, and therefore not allowed to log data points.")
  })
  public ResponseEntity<Void> logVehicleDataPoint(
      @RequestBody VehicleDataPointRequest dataPoint,
      Principal principal
  ) {

    log.info(
        "Saving data point for tenant {} received at {}",
        principal.getName(),
        Instant.now().atOffset(ZoneOffset.UTC)
    );

    vehicleDataPointService.saveNewDataPoint(dataPoint, principal.getName());

    return ResponseEntity.ok().build();
  }
}
