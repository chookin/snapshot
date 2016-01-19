package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.Photo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zhuyin on 12/4/15.
 */
public interface PhotoRepository extends CrudRepository<Photo, Long> {
    @Query("select p from Photo as p where p.userId=? order by p.time desc ")
    List<Photo> findByUserId(long userId);

    List<Photo> findByWorkId(long workId);

    void deleteByIdAndWorkId(long id, long workId);
}
