package st.emberdex.csvapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import st.emberdex.csvapi.model.Tenant;
import st.emberdex.csvapi.repository.TenantRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantService {

  private final TenantRepository tenantRepository;

  private final PasswordEncoder keyEncoder;

  public void createTenant(String tenantName, String accessKey) {

    if (tenantRepository.existsTenantByTenantName(tenantName)) {
      log.warn("Tried to create tenant {} but already exists.", tenantName);
      return;
    }

    Tenant newTenant = Tenant.builder()
        .tenantName(tenantName)
        .accessKey(encodeAccessKey(accessKey))
        .build();

    tenantRepository.save(newTenant);
  }

  private String encodeAccessKey(String accessKey) {

    return keyEncoder.encode(accessKey);
  }
}
