package st.emberdex.csvapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import st.emberdex.csvapi.model.VehicleDataPoint;
import st.emberdex.csvapi.model.VehicleDataPointDocument;
import st.emberdex.csvapi.repository.VehicleDataPointRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleDataPointService {

  private final VehicleDataPointRepository repository;

  public void save(VehicleDataPoint dataPoint, String tenantName) {

    VehicleDataPointDocument document = VehicleDataPointDocument.builder()
        .tenantName(tenantName)
        .dataPoint(dataPoint)
        .build();

    repository.save(document);
  }
}
