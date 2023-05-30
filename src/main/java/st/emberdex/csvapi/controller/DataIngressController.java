package st.emberdex.csvapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import st.emberdex.csvapi.model.CarStatsViewerDataPoint;

@Slf4j
@RestController
@RequestMapping("/")
public class DataIngressController {

  @PostMapping
  public ResponseEntity<Void> logDataPoint(@RequestBody CarStatsViewerDataPoint dataPoint) {

    log.info("Logging data point received at {}", dataPoint.getTimestamp());

    return ResponseEntity.ok().build();
  }
}
