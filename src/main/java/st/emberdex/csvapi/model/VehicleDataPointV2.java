package st.emberdex.csvapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import st.emberdex.csvapi.model.enums.v2.IgnitionState;
import st.emberdex.csvapi.model.enums.v2.SelectedGear;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_EMPTY)
public class VehicleDataPointV2 extends VehicleDataPoint {

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
   * GPS location of this vehicle when this data point was recorded.
   */
  private GeoJsonPoint location;

  /**
   * GPS altitude of the vehicle when this data point was recorded, in metres.
   */
  private Double altitude;
}
