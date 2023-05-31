package st.emberdex.csvapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import st.emberdex.csvapi.model.Tenant;
import st.emberdex.csvapi.model.request.CreateTenantRequest;
import st.emberdex.csvapi.model.request.UpdateTenantAccessKeyRequest;
import st.emberdex.csvapi.model.response.TenantResponse;
import st.emberdex.csvapi.service.TenantService;

import static java.util.Objects.nonNull;

@Slf4j
@RestController
@RequestMapping("/tenant")
@RequiredArgsConstructor
public class TenantController {

  private final TenantService tenantService;

  @GetMapping
  @Operation(
      summary = "Fetch tenant information",
      description = "Fetch information about the specified tenant."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "The tenant information was fetched successfully."),
      @ApiResponse(responseCode = "401", description = "The user is not authorised."),
      @ApiResponse(responseCode = "403", description = "The user is logged in as a tenant, and therefore not allowed to fetch tenant information.")
  })
  public ResponseEntity<TenantResponse> lookupTenant(
      @RequestParam(required = false) String tenantId,
      @RequestParam(required = false) String tenantName
  ) {

    if (nonNull(tenantId)) {
      log.info("Looking up tenant {} by ID", tenantId);

      return ResponseEntity.ok(
          TenantResponse.fromTenant(
              tenantService.lookupTenantById(tenantId)));
    } else if (nonNull(tenantName)) {
      log.info("Looking up tenant {} by name", tenantName);

      return ResponseEntity.ok(
          TenantResponse.fromTenant(
              tenantService.lookupTenantByName(tenantName)));
    } else {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "Neither tenantId nor tenantName was specified in lookup request GET params."
      );
    }
  }

  @PutMapping
  @Operation(
      summary = "Create new tenant",
      description = "Create a new tenant. This tenant can then log data points via the webhook."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "The tenant was created successfully."),
      @ApiResponse(responseCode = "401", description = "The user is not authorised."),
      @ApiResponse(responseCode = "403", description = "The user is logged in as a tenant, and therefore not allowed to create a tenant."),
      @ApiResponse(responseCode = "409", description = "A tenant with the specified name already exists.")
  })
  public ResponseEntity<TenantResponse> createTenant(
      @RequestBody CreateTenantRequest requestBody
  ) {

    log.info("Creating new tenant with name {}", requestBody.getTenantName());

    Tenant newTenant = tenantService.createTenant(
        requestBody.getTenantName(),
        requestBody.getAccessKey()
    );

    return ResponseEntity.ok(
        TenantResponse.fromTenant(newTenant)
    );
  }

  @DeleteMapping
  @Operation(
      summary = "Delete tenant",
      description = "Delete the specified tenant."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "The tenant was deleted successfully."),
      @ApiResponse(responseCode = "401", description = "The user is not authorised."),
      @ApiResponse(responseCode = "403", description = "The user is logged in as a tenant, and therefore not allowed to delete tenants.")
  })
  public ResponseEntity<Void> deleteTenant(
      @RequestParam String tenantId
  ) {

    log.info("Deleting tenant {} by ID", tenantId);

    tenantService.deleteTenant(tenantId);

    return ResponseEntity.ok(null);
  }

  @PostMapping("/update-key")
  @Operation(
      summary = "Update a tenant's access key",
      description = "Update the access key for the specified tenant."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "The tenant's access key was updated successfully'."),
      @ApiResponse(responseCode = "401", description = "The user is not authorised."),
      @ApiResponse(responseCode = "403", description = "The user is logged in as a tenant, and therefore not allowed to update tenant access keys.")
  })
  public ResponseEntity<Void> updateTenantAccessKey(
      @RequestParam String tenantId,
      @RequestBody UpdateTenantAccessKeyRequest requestBody
  ) {

    log.info("Updating access key of tenant ID {}", tenantId);

    tenantService.updateTenantAccessKey(
        tenantId,
        requestBody.getNewAccessKey()
    );

    return ResponseEntity.ok(null);
  }
}
