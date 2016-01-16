package cmri.snapshot.api.domain;

import cmri.snapshot.api.helper.ModelHelper;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhuyin on 1/16/16.
 */
@Entity
@Table(name = "special_shot", schema = "", catalog = "snapshot")
public class SpecialShot {
    private long id;
    private int price;
    private Timestamp time;
    private String location;
    private String title;
    private String intro;
    private String summary;
    private String service;
    private String sculpt;
    private long creator;
    private Timestamp createTime;
    private int likeCount;
    private int commentCount;
    private static final AtomicLong idGen = new AtomicLong();

    static {
        Long id = ModelHelper.getMaxId("works");
        if (id != null) idGen.set(id);
    }

    public static SpecialShot newOne() {
        SpecialShot shot = new SpecialShot();
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
    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
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
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "intro")
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Basic
    @Column(name = "summary")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Basic
    @Column(name = "service")
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Basic
    @Column(name = "sculpt")
    public String getSculpt() {
        return sculpt;
    }

    public void setSculpt(String sculpt) {
        this.sculpt = sculpt;
    }

    @Basic
    @Column(name = "creator")
    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
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
    @Column(name = "like_count")
    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    @Basic
    @Column(name = "comment_count")
    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpecialShot that = (SpecialShot) o;

        if (id != that.id) return false;
        if (price != that.price) return false;
        if (creator != that.creator) return false;
        if (likeCount != that.likeCount) return false;
        if (commentCount != that.commentCount) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (intro != null ? !intro.equals(that.intro) : that.intro != null) return false;
        if (summary != null ? !summary.equals(that.summary) : that.summary != null) return false;
        if (service != null ? !service.equals(that.service) : that.service != null) return false;
        if (sculpt != null ? !sculpt.equals(that.sculpt) : that.sculpt != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + price;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (intro != null ? intro.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (service != null ? service.hashCode() : 0);
        result = 31 * result + (sculpt != null ? sculpt.hashCode() : 0);
        result = 31 * result + (int) (creator ^ (creator >>> 32));
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + likeCount;
        result = 31 * result + commentCount;
        return result;
    }
}
