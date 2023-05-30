package st.emberdex.csvapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import st.emberdex.csvapi.model.Tenant;
import st.emberdex.csvapi.repository.TenantRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantService {

  private final TenantRepository tenantRepository;

  private final PasswordEncoder keyEncoder;

  /**
   * Save a new {@link Tenant} to the database.
   * @param tenantName The name of the new tenant to create.
   * @param accessKey The access key which the new tenant will use to authenticate against the API.
   * @return The newly-created tenant.
   */
  public Tenant createTenant(String tenantName, String accessKey) {

    if (tenantRepository.existsTenantByTenantName(tenantName)) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT,
          "Tenant with the specified name already exists."
      );
    }

    Tenant newTenant = Tenant.builder()
        .tenantName(tenantName)
        .accessKey(encodeAccessKey(accessKey))
        .build();

    newTenant = tenantRepository.insert(newTenant);

    return newTenant;
  }

  /**
   * Lookup a {@link Tenant} by name.
   * If a tenant with the specified name is not found, a ResponseStatusException is thrown.
   * This service method is intended for API use only, and should not be used for internal tenant lookups. Instead, consider looking up the tenant by their ID.
   * @param tenantName The name of the tenant to look up.
   * @return The tenant, if found.
   */
  public Tenant lookupTenantByName(String tenantName) {

    return tenantRepository.findTenantByTenantName(tenantName)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "A tenant with the specified name was not found."
        ));
  }

  /**
   * Lookup a {@link Tenant} by ID.
   * If a tenant with the specified ID is not found, a ResponseStatusException is thrown.
   * @param tenantId The ID of the tenant to look up.
   * @return The tenant, if found.
   */
  public Tenant lookupTenantById(String tenantId) {

    return tenantRepository.findTenantById(tenantId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "A tenant with the specified ID was not found."
        ));
  }

  /**
   * Update a {@link Tenant}'s access key.
   * @param tenantId The ID of the tenant whose access key should be updated.
   * @param newAccessKey The new access key.
   */
  public void updateTenantAccessKey(String tenantId, String newAccessKey) {

    Tenant tenant = this.lookupTenantById(tenantId);

    tenant.setAccessKey(encodeAccessKey(newAccessKey));

    tenantRepository.save(tenant);
  }

  /**
   * Delete a {@link Tenant} from the database.
   * Once deleted, any data points submitted by this tenant will remain unless otherwise purged.
   * Furthermore, if a new tenant with the same name is created, their data points are indistinguishable from those of the previous tenant.
   * Use {@link VehicleDataPointService#purgeTenantDataPoints(String) the purgeTenantDataPoints method} to delete a tenant's data points, even after the tenant has been deleted.
   * @param tenantId The ID of the tenant to delete.
   */
  public void deleteTenant(String tenantId) {

    Tenant tenant = this.lookupTenantById(tenantId);

    tenantRepository.delete(tenant);
  }

  /**
   * Use the provided KeyEncoder to encode an access key.
   * The algorithm by which the key is encoded depends purely on which KeyEncoder is provided at runtime.
   * @param accessKey The access key to encode.
   * @return The encoded access key.
   */
  private String encodeAccessKey(String accessKey) {

    return keyEncoder.encode(accessKey);
  }
}
