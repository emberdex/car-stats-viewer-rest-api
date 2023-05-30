package st.emberdex.csvapi.controller;

import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/tenant")
@RequiredArgsConstructor
public class TenantController {

  private final TenantService tenantService;

  @GetMapping
  public ResponseEntity<TenantResponse> lookupTenant(
      @RequestParam(required = false) String tenantId,
      @RequestParam(required = false) String tenantName
  ) {

    if (nonNull(tenantId)) {
      return ResponseEntity.ok(
          TenantResponse.fromTenant(
              tenantService.lookupTenantById(tenantId)));
    } else if (nonNull(tenantName)) {
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
  public ResponseEntity<TenantResponse> createTenant(
      @RequestBody CreateTenantRequest requestBody
  ) {

    Tenant newTenant = tenantService.createTenant(
        requestBody.getTenantName(),
        requestBody.getAccessKey()
    );

    return ResponseEntity.ok(
        TenantResponse.fromTenant(newTenant)
    );
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteTenant(
      @RequestParam String tenantId
  ) {

    tenantService.deleteTenant(tenantId);

    return ResponseEntity.ok(null);
  }

  @PostMapping("/update-key")
  public ResponseEntity<Void> updateTenantAccessKey(
      @RequestBody UpdateTenantAccessKeyRequest requestBody
  ) {

    tenantService.updateTenantAccessKey(
        requestBody.getTenantId(),
        requestBody.getNewAccessKey()
    );

    return ResponseEntity.ok(null);
  }
}
