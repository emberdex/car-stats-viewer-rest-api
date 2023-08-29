package st.emberdex.csvapi.model;

import lombok.Getter;

@Getter
public class VehicleDataPoint {

  /**
   * Timestamp, as a Unix epoch, that this data point was captured.
   */
  private Long timestamp;
}
