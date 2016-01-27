package cmri.snapshot.api.domain;

import cmri.snapshot.api.helper.ModelHelper;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhuyin on 1/17/16.
 */
@Entity
@Table(name = "shot_release")
public class ShotRelease {
    private long id;
    private long grapherId;
    private String location;
    private Timestamp createTime;
    private int appointmentCount;
    private static final AtomicLong idGen = new AtomicLong();

    static {
        Long id = ModelHelper.getMaxId("shot_release");
        if (id != null) idGen.set(id);
    }

    public static ShotRelease newOne() {
        ShotRelease shot = new ShotRelease();
        shot.setId(nextId());
        shot.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return shot;
    }

    public static long nextId() {
        return idGen.incrementAndGet();
    }
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
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "appointment_count")
    public int getAppointmentCount() {
        return appointmentCount;
    }

    public void setAppointmentCount(int appointmentCount) {
        this.appointmentCount = appointmentCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShotRelease that = (ShotRelease) o;

        if (id != that.id) return false;
        if (grapherId != that.grapherId) return false;
        if (appointmentCount != that.appointmentCount) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (grapherId ^ (grapherId >>> 32));
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + appointmentCount;
        return result;
    }
}
