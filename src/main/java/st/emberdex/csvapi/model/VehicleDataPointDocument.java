package st.emberdex.csvapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDataPointDocument {

  @Id
  private String id;

  private String tenantName;

  private VehicleDataPoint dataPoint;
}
