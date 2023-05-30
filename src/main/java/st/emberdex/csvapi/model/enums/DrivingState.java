package st.emberdex.csvapi.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DrivingState {

  UNKNOWN(-1),
  PARKED(0),
  DRIVE(1),
  CHARGE(2);

  private final int apiValue;

  @JsonValue
  public int getApiValue() {
    return apiValue;
  }
}
