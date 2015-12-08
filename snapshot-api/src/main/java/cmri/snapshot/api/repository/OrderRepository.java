package cmri.snapshot.api.repository;

import cmri.snapshot.api.domain.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zhuyin on 15/12/5.
 */
public interface OrderRepository extends CrudRepository<Order, Long> {
    @Query("select e from Order e where e.userId = ? order by e.id ")
    List<Order> findUser(long uid);

    @Query("select e from Order e where e.grapherId = ?")
    List<Order> findGrapher(long uid);

    @Query("select count(*) from Order e where e.grapherId =?")
    int countUserOrders(long uid);

    @Query("select count(*) from Order e where e.grapherId =?")
    int countGrapherOrders(long uid);
}
