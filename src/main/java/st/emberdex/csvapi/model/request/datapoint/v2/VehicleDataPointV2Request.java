package st.emberdex.csvapi.model.request.datapoint.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import st.emberdex.csvapi.model.VehicleDataPoint;
import st.emberdex.csvapi.model.VehicleDataPointV2;
import st.emberdex.csvapi.model.enums.v2.IgnitionState;
import st.emberdex.csvapi.model.enums.v2.SelectedGear;
import st.emberdex.csvapi.model.request.datapoint.VehicleDataPointRequest;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static java.util.Objects.nonNull;

/**
 * Data point request for application versions which use an apiVersion of 2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_EMPTY)
public class VehicleDataPointV2Request extends VehicleDataPointRequest {

  /**
   * Debugging information about ABRP used by the CSV app
   */
  private String abrpPackage;

  /**
   * The ambient temperature around the vehicle.
   */
  private Float ambientTemperature;

  /**
   * The API version.
   */
  private Integer apiVersion;

  /**
   * The version of Car Stats Viewer which sent this request.
   */
  private String appVersion;

  /**
   * The current battery level, in Wh.
   */
  private BigDecimal batteryLevel;

  /**
   * Flag set when the charge port is connected.
   */
  private Boolean chargePortConnected;

  /**
   * The state of the vehicle ignition.
   */
  private IgnitionState ignitionState;

  /**
   * Rate of charge/discharge in mWh.
   */
  private Float power;

  /**
   * The selected gear.
   */
  private SelectedGear selectedGear;

  /**
   * The speed of the vehicle, in km/h.
   */
  private Float speed;

  /**
   * The state of charge, expressed where 1 == full and 0 == empty
   */
  private Float stateOfCharge;

  /**
   * GPS latitude of the vehicle when this data point was recorded.
   */
  @JsonProperty("lat")
  private Double latitude;

  /**
   * GPS longitude of the vehicle when this data point was recorded.
   */
  @JsonProperty("lon")
  private Double longitude;

  /**
   * GPS altitude of the vehicle when this data point was recorded, in metres.
   */
  @JsonProperty("alt")
  private Double altitude;

  public VehicleDataPoint toDataPoint() {
    VehicleDataPointV2 dataPoint = new VehicleDataPointV2();
    BeanUtils.copyProperties(this, dataPoint);

    if (coordinateIsValid(longitude) && coordinateIsValid(latitude)) {
      GeoJsonPoint location = new GeoJsonPoint(longitude, latitude);
      dataPoint.setLocation(location);
    }

    return dataPoint;
  }

  private boolean coordinateIsValid(Double value) {
    return nonNull(value) && value != 0.0;
  }
}
