package cmri.snapshot.api.domain;

import cmri.snapshot.api.helper.ModelHelper;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhuyin on 15/12/6.
 */
@Entity
public class Work {
    private long id;
    private long userId;
    private String name;
    private String location;
    private int likeCount;
    private int commentCount;
    private Timestamp createTime;
    private static final AtomicLong idGen = new AtomicLong();
    static {
        Long id = ModelHelper.getMaxId("work");
        if(id != null) idGen.set(id);
    }
    public static Work newOne(){
        return new Work()
                .setId(nextId())
                .setCreateTime(new Timestamp(System.currentTimeMillis()));
    }
    public static long nextId(){
        return idGen.incrementAndGet();
    }
    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public Work setId(long id) {
        this.id = id;
        return this;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public Work setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Work work = (Work) o;

        if (id != work.id) return false;
        if (userId != work.userId) return false;
        if (likeCount != work.likeCount) return false;
        if (commentCount != work.commentCount) return false;
        if (name != null ? !name.equals(work.name) : work.name != null) return false;
        if (location != null ? !location.equals(work.location) : work.location != null) return false;
        if (createTime != null ? !createTime.equals(work.createTime) : work.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + likeCount;
        result = 31 * result + commentCount;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
