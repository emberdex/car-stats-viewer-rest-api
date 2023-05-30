package st.emberdex.csvapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vehicle_data_point")
public class VehicleDataPointDocument {

  @Id
  private String id;

  @Indexed
  private String tenantName;

  private VehicleDataPoint dataPoint;
}
