package st.emberdex.csvapi.model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import st.emberdex.csvapi.model.VehicleDataPoint;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vehicle_data_point")
public class VehicleDataPointDocument {

  @Id
  private String id;

  /**
   * The name of the tenant who submitted this data point.
   */
  @Indexed
  private String tenantName;

  /**
   * The UTC timestamp at which this data point was stored.
   * This may differ from the time it was recorded by the vehicle; please reference {@link VehicleDataPoint#getTimestamp()}.
   */
  private Instant storedAt;

  /**
   * The vehicle data point.
   */
  private VehicleDataPoint dataPoint;
}
