package st.emberdex.csvapi.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import st.emberdex.csvapi.model.VehicleDataPoint;
import st.emberdex.csvapi.model.enums.DrivingState;
import st.emberdex.csvapi.model.enums.VehicleGear;
import st.emberdex.csvapi.model.enums.VehicleIgnitionState;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static java.util.Objects.nonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_EMPTY)
public class VehicleDataPointRequest {

  /**
   * The ambient temperature around the vehicle.
   */
  private Float ambientTemperature;

  /**
   * The average energy consumption during the current trip.
   */
  @JsonProperty("avgConsumption")
  private Float averageConsumption;

  /**
   * The average speed during the current trip.
   */
  @JsonProperty("avgSpeed")
  private Float averageSpeed;

  /**
   * The current state of charge, expressed as a percentage.
   */
  private Float batteryLevel;

  /**
   * Flag set when the charge port is connected.
   */
  private Boolean chargePortConnected;

  /**
   * Date and time at which the charging cable was plugged in.
   */
  // Sep 17, 2023 3:39:21 PM
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM d, yyyy h:m:s")
  private Date chargeStartDate;

  /**
   * Time the vehicle has been charging for, in ms
   */
  private Long chargeTime;

  /**
   * Amount of energy the battery has been recharged by, in Wh
   */
  private Float chargedEnergy;

  /**
   * Currently selected drive mode.
   */
  private VehicleGear currentGear;

  /**
   * Current ignition state.
   */
  private VehicleIgnitionState currentIgnitionMode;

  /**
   * Current power consumption, in kW.
   */
  @JsonProperty("currentPower")
  private Float currentPowerConsumption;

  /**
   * Current vehicle speed, in km/h.
   */
  private Float currentSpeed;

  /**
   * Current driving state.
   */
  private DrivingState drivingState;

  /**
   * Flag set if the vehicle is charging.
   */
  private Boolean isCharging;

  /**
   * Flag set if the vehicle is DC fast charging.
   */
  private Boolean isFastCharging;

  /**
   * Flag set if the vehicle is parked.
   */
  private Boolean isParked;

  /**
   * Maximum battery level reported by the vehicle, in Wh.
   */
  @JsonProperty("maxBatteryLevel")
  private Float maximumBatteryLevel;

  /**
   * Current state of charge as reported by the vehicle.
   * Calculated as the percentage of the max battery energy versus current battery energy.
   */
  private Integer stateOfCharge;

  /**
   * Timestamp, as a Unix epoch, that this data point was captured.
   */
  private Long timestamp;

  /**
   * Travel time, in milliseconds.
   */
  private Long travelTime;

  /**
   * Distance travelled, in km.
   */
  private Float traveledDistance;

  /**
   * Date and time at which the current trip was started.
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM d, yyyy h:m:s")
  private Date tripStartDate;

  /**
   * Amount of energy used during the current trip, in Wh.
   */
  private Float usedEnergy;

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
    VehicleDataPoint dataPoint = new VehicleDataPoint();
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
