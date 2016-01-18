package cmri.snapshot.api.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zhuyin on 1/18/16.
 */
@Entity
@Table(name = "grapher_price_history", schema = "", catalog = "snapshot")
public class GrapherPriceHistory {
    private long id;
    private long grapherId;
    private int price;
    private Integer month;
    private Timestamp time;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    @Column(name = "month")
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GrapherPriceHistory that = (GrapherPriceHistory) o;

        if (id != that.id) return false;
        if (grapherId != that.grapherId) return false;
        if (price != that.price) return false;
        if (month != null ? !month.equals(that.month) : that.month != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (grapherId ^ (grapherId >>> 32));
        result = 31 * result + price;
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
