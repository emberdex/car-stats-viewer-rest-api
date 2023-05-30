package st.emberdex.csvapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request to update the specified tenant's access key.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTenantAccessKeyRequest {

  private String tenantId;

  private String newAccessKey;
}
