package st.emberdex.csvapi.model.enums.v1;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum VehicleIgnitionState {

  UNKNOWN(0),
  LOCK(1),
  OFF(2),
  ACCESSORY(3),
  ON(4),
  START(5);

  public final int apiValue;

  @JsonValue
  public int getApiValue() {
    return apiValue;
  }
}
