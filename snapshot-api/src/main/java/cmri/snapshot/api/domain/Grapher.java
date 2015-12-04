package cmri.snapshot.api.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zhuyin on 12/3/15.
 */
@Entity
public class Grapher {
    private long userId;
    private int price;
    private int rating;
    private String region;
    private String desire;
    private byte status;
    private Timestamp createTime;

    @Id
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
    @Column(name = "rating")
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "region")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Basic
    @Column(name = "desire")
    public String getDesire() {
        return desire;
    }

    public void setDesire(String desire) {
        this.desire = desire;
    }

    @Basic
    @Column(name = "status")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
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

        Grapher grapher = (Grapher) o;

        if (userId != grapher.userId) return false;
        if (price != grapher.price) return false;
        if (rating != grapher.rating) return false;
        if (status != grapher.status) return false;
        if (region != null ? !region.equals(grapher.region) : grapher.region != null) return false;
        if (desire != null ? !desire.equals(grapher.desire) : grapher.desire != null) return false;
        if (createTime != null ? !createTime.equals(grapher.createTime) : grapher.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + price;
        result = 31 * result + rating;
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (desire != null ? desire.hashCode() : 0);
        result = 31 * result + (int) status;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
