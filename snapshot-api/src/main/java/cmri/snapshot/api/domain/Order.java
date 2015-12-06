package cmri.snapshot.api.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by feifei on 15/12/5.
 */
@Entity
@Table(name = "shot_order", schema = "snapshot", catalog = "")
public class Order {
    private long id;
    private long userId;
    private long grapherId;
    private int price;
    private Timestamp startTime;
    private Timestamp createTime;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "grapher_id")
    public long getGrapherId() {
        return grapherId;
    }

    public void setGrapherId(long grapherId) {
        this.grapherId = grapherId;
    }

    @Basic
    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Basic
    @Column(name = "start_time")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (userId != order.userId) return false;
        if (grapherId != order.grapherId) return false;
        if (price != order.price) return false;
        if (startTime != null ? !startTime.equals(order.startTime) : order.startTime != null) return false;
        if (createTime != null ? !createTime.equals(order.createTime) : order.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (grapherId ^ (grapherId >>> 32));
        result = 31 * result + price;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
