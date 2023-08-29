package st.emberdex.csvapi.model.enums.v2;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum IgnitionState {

  UNDEFINED("Undefined"),
  LOCKED("Locked"),
  OFF("Off"),
  ACCESSORIES_AVAILABLE("Accessory"),
  ON("On"),
  ENGINE_STARTING("Started");

  private final String apiValue;

  @JsonValue
  public String getApiValue() {
    return apiValue;
  }
}
