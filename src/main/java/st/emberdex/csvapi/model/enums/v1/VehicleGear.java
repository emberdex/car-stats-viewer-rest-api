package st.emberdex.csvapi.model.enums.v1;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

/**
 * A copy of the enumeration from Android Automotive that maps integral values for the vehicle gear
 */
@RequiredArgsConstructor
public enum VehicleGear {
  GEAR_DRIVE(8),
  GEAR_EIGHTH(2048),
  GEAR_FIFTH(256),
  GEAR_FIRST(16),
  GEAR_FOURTH(128),
  GEAR_NEUTRAL(1),
  GEAR_NINTH(4096),
  GEAR_PARK(4),
  GEAR_REVERSE(2),
  GEAR_SECOND(32),
  GEAR_SEVENTH(1024),
  GEAR_SIXTH(512),
  GEAR_THIRD(64),
  GEAR_UNKNOWN(0);
  
  public final int apiValue;

  @JsonValue
  public int getApiValue() {
    return apiValue;
  }
}
