package st.emberdex.csvapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * Information about a Tenant.
 * A Tenant is someone who is authorised to submit data points to the ingress endpoints.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {

  @Id
  private String id;

  /**
   * The name of the tenant.
   */
  private String tenantName;

  /**
   * The bcrypt hash of the tenant's access key.
   */
  private String accessKey;
}
