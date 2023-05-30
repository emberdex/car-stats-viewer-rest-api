package st.emberdex.csvapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import st.emberdex.csvapi.model.request.VehicleDataPointRequest;
import st.emberdex.csvapi.model.document.VehicleDataPointDocument;
import st.emberdex.csvapi.repository.VehicleDataPointRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleDataPointService {

  private final VehicleDataPointRepository vehicleDataPointRepository;

  /**
   * Save a new data point to the database.
   * @param dataPointRequest The data point to save.
   * @param tenantName The name of the tenant who submitted this request.
   *                   This will be stored alongside the transformed data point.
   */
  public void saveNewDataPoint(VehicleDataPointRequest dataPointRequest, String tenantName) {

    VehicleDataPointDocument document = VehicleDataPointDocument.builder()
        .tenantName(tenantName)
        .dataPoint(dataPointRequest.toDataPoint())
        .build();

    vehicleDataPointRepository.save(document);
  }

  /**
   * Purge all tenant data points.
   * @param tenantName The tenant name for which all data points should be purged.
   */
  public void purgeTenantDataPoints(String tenantName) {
    vehicleDataPointRepository.deleteAllByTenantName(tenantName);
  }
}
