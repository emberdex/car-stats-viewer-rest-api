package st.emberdex.csvapi.config.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import st.emberdex.csvapi.model.Tenant;
import st.emberdex.csvapi.repository.TenantRepository;

@Service
@RequiredArgsConstructor
public class TenantDetailsService implements UserDetailsService {

  private final TenantRepository tenantRepository;

  private final PasswordEncoder passwordEncoder;

  @Value("${tenancy.admin-username}")
  private String tenancyAdminUsername;

  @Value("${tenancy.admin-password}")
  private String tenancyAdminPassword;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    if (username.equals(tenancyAdminUsername)) {
      return User
          .withUsername(username)
          .password(passwordEncoder.encode(tenancyAdminPassword))
          .roles("ADMIN")
          .build();
    }

    final Tenant tenant = tenantRepository.findTenantByTenantName(username)
        .orElseThrow(() -> new UsernameNotFoundException("Invalid tenant name or key."));

    return User
        .withUsername(username)
        .password(tenant.getAccessKey())
        .roles("TENANT")
        .build();
  }
}
