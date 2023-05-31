package st.emberdex.csvapi.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import st.emberdex.csvapi.config.security.service.TenantDetailsService;

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
        .requestMatchers("/swagger-ui/**").permitAll()    // Swagger
        .requestMatchers("/v3/**").permitAll()            // Swagger
        .requestMatchers("/ingress/**").hasRole("TENANT") // Data ingress
        .requestMatchers("/query/**").hasRole("TENANT")   // Data query
        .requestMatchers("/tenant/**").hasRole("ADMIN")   // Tenant management
        .anyRequest().authenticated()
        .and()
        .httpBasic(Customizer.withDefaults())
        .userDetailsService(tenantDetailsService)
        .build();
  }
}
