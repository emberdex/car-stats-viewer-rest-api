package st.emberdex.csvapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
  @Operation(
      summary = "Fetch data points for tenant",
      description = "Fetch all data points submitted by the currently authenticated tenant."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "The data points were fetched successfully."),
      @ApiResponse(responseCode = "401", description = "The user is not authorised."),
      @ApiResponse(responseCode = "403", description = "The user is logged in as a tenant admin, and therefore not allowed to fetch data points.")
  })
  public ResponseEntity<Map<String, Object>> getDataPoints(Principal principal) {

    log.info("Fetching all data points for tenant {}", principal.getName());

    List<VehicleDataPointDocument> results = vehicleDataPointService.getDataPointsForTenant(principal.getName());

    Map<String, Object> responseBody = Map.of(
        "count", results.size(),
        "results", results
    );

    return ResponseEntity.ok(responseBody);
  }

  @DeleteMapping("/all")
  @Operation(
      summary = "Delete all data points for tenant",
      description = "Delete all data points submitted by the currently authenticated tenant."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "The data points were deleted successfully."),
      @ApiResponse(responseCode = "401", description = "The user is not authorised."),
      @ApiResponse(responseCode = "403", description = "The user is logged in as a tenant admin, and therefore not allowed to fetch data points.")
  })
  public ResponseEntity<Void> purgeDataPointsForTenant(Principal principal) {

    log.info("Purging all data points for tenant {}", principal.getName());

    vehicleDataPointService.purgeTenantDataPoints(principal.getName());

    return ResponseEntity.ok(null);
  }
}
