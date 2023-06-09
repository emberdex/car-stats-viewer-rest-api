package st.emberdex.csvapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import st.emberdex.csvapi.model.document.VehicleDataPointDocument;

import java.util.List;

public interface VehicleDataPointRepository extends MongoRepository<VehicleDataPointDocument, String> {

  void deleteAllByTenantName(String tenantName);

  List<VehicleDataPointDocument> getAllByTenantName(String tenantName);
}
