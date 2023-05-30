package st.emberdex.csvapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import st.emberdex.csvapi.model.VehicleDataPointRequest;
import st.emberdex.csvapi.model.VehicleDataPointDocument;
import st.emberdex.csvapi.repository.VehicleDataPointRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleDataPointService {

  private final VehicleDataPointRepository repository;

  public void save(VehicleDataPointRequest dataPointRequest, String tenantName) {

    VehicleDataPointDocument document = VehicleDataPointDocument.builder()
        .tenantName(tenantName)
        .dataPoint(dataPointRequest.toDataPoint())
        .build();

    repository.save(document);
  }
}
