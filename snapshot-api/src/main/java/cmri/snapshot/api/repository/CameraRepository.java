package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.Camera;
import cmri.snapshot.api.domain.GrapherPlan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zhuyin on 12/3/15.
 */
public interface CameraRepository extends CrudRepository<Camera, Long>{
    List<Camera> findByUserId(long id);
}
