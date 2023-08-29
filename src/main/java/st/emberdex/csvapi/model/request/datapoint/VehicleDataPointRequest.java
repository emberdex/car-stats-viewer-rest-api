package st.emberdex.csvapi.model.request.datapoint;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import st.emberdex.csvapi.model.VehicleDataPoint;
import st.emberdex.csvapi.model.request.datapoint.v1.VehicleDataPointV1Request;
import st.emberdex.csvapi.model.request.datapoint.v2.VehicleDataPointV2Request;

/**
 * Class used as a generic type for all vehicle data point requests. Common fields included here.
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "apiVersion",
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    visible = true,
    defaultImpl = VehicleDataPointV1Request.class
)
@JsonSubTypes(value = {
    @JsonSubTypes.Type(value = VehicleDataPointV2Request.class, name = "2")
})
public abstract class VehicleDataPointRequest {

  /**
   * Timestamp, as a Unix epoch, that this data point was captured.
   */
  private Long timestamp;

  /**
   * The API version.
   */
  private Integer apiVersion;

  public abstract VehicleDataPoint toDataPoint();
}
