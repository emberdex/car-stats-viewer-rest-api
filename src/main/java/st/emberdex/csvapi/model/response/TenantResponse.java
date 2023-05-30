package st.emberdex.csvapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import st.emberdex.csvapi.model.Tenant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantResponse {

  private String id;

  private String tenantName;

  /**
   * Construct a TenantResponse from an existing {@link Tenant}.
   * @param tenant The {@link Tenant} to construct the response from.
   * @return A TenantResponse object containing the details of the provided {@link Tenant}.
   */
  public static TenantResponse fromTenant(Tenant tenant) {
    return TenantResponse.builder()
        .id(tenant.getId())
        .tenantName(tenant.getTenantName())
        .build();
  }
}
