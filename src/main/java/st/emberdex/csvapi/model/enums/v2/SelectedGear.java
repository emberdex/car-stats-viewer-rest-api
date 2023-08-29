package st.emberdex.csvapi.model.enums.v2;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SelectedGear {

  UNKNOWN("UNKNOWN"),
  PARK("P"),
  REVERSE("R"),
  NEUTRAL("N"),
  DRIVE("D");

  public final String apiValue;

  @JsonValue
  public String getApiValue() {
    return apiValue;
  }
}
