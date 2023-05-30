package st.emberdex.csvapi.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import st.emberdex.csvapi.service.TenantDetailsService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final TenantDetailsService tenantDetailsService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {

    return security
        .csrf().disable()
        .authorizeHttpRequests()
        .requestMatchers("/").hasRole("TENANT")
        .requestMatchers("/tenant/**").hasRole("ADMIN")
        .anyRequest().authenticated()
        .and()
        .httpBasic(Customizer.withDefaults())
        .userDetailsService(tenantDetailsService)
        .build();
  }
}
