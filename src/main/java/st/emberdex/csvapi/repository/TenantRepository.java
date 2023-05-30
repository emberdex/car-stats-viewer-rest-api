package st.emberdex.csvapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import st.emberdex.csvapi.model.Tenant;

import java.util.Optional;

public interface TenantRepository extends MongoRepository<Tenant, String> {

  boolean existsTenantByTenantName(String tenantName);

  Optional<Tenant> findTenantByTenantName(String tenantName);
}
