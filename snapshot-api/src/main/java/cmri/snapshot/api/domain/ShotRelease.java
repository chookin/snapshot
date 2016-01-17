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
    private int likeCount;
    private int commentCount;
    private Timestamp releaseTime;
    private int appointmentCount;
    private static final AtomicLong idGen = new AtomicLong();

    static {
        Long id = ModelHelper.getMaxId("shot_release");
        if (id != null) idGen.set(id);
    }

    public static ShotRelease newOne() {
        ShotRelease shot = new ShotRelease();
        shot.setId(nextId());
        shot.setReleaseTime(new Timestamp(System.currentTimeMillis()));
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
    @Column(name = "like_count")
    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int keCount) {
        this.likeCount = keCount;
    }

    @Basic
    @Column(name = "comment_count")
    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    @Basic
    @Column(name = "release_time")
    public Timestamp getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Timestamp releaseTime) {
        this.releaseTime = releaseTime;
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
        if (likeCount != that.likeCount) return false;
        if (commentCount != that.commentCount) return false;
        if (appointmentCount != that.appointmentCount) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (releaseTime != null ? !releaseTime.equals(that.releaseTime) : that.releaseTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (grapherId ^ (grapherId >>> 32));
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + likeCount;
        result = 31 * result + commentCount;
        result = 31 * result + (releaseTime != null ? releaseTime.hashCode() : 0);
        result = 31 * result + appointmentCount;
        return result;
    }
}
