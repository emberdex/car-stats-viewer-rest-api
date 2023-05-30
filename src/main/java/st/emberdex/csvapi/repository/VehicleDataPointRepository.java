package st.emberdex.csvapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import st.emberdex.csvapi.model.VehicleDataPointDocument;

public interface VehicleDataPointRepository extends MongoRepository<VehicleDataPointDocument, String> {
}
