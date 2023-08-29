package st.emberdex.csvapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import st.emberdex.csvapi.model.document.VehicleDataPointDocument;
import st.emberdex.csvapi.model.request.datapoint.VehicleDataPointRequest;
import st.emberdex.csvapi.model.request.datapoint.v2.VehicleDataPointV2Request;
import st.emberdex.csvapi.repository.VehicleDataPointRepository;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

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

    VehicleDataPointDocument.VehicleDataPointDocumentBuilder documentBuilder = VehicleDataPointDocument.builder();

    documentBuilder
        .tenantName(tenantName)
        .dataPoint(dataPointRequest.toDataPoint())
        .storedAt(Instant.now().atOffset(ZoneOffset.UTC).toInstant());

    if (dataPointRequest instanceof VehicleDataPointV2Request v2Request) {
      documentBuilder
          .tenantApiVersion(v2Request.getApiVersion())
          .tenantAppVersion(v2Request.getAppVersion());
    } else {
      documentBuilder
          .tenantApiVersion(1)
          .tenantAppVersion("UNKNOWN");
    }

    VehicleDataPointDocument document = documentBuilder.build();

    vehicleDataPointRepository.save(document);
  }

  public List<VehicleDataPointDocument> getDataPointsForTenant(String tenantName) {

    return vehicleDataPointRepository.getAllByTenantName(tenantName);
  }

  /**
   * Purge all tenant data points.
   * @param tenantName The tenant name for which all data points should be purged.
   */
  public void purgeTenantDataPoints(String tenantName) {
    vehicleDataPointRepository.deleteAllByTenantName(tenantName);
  }
}
