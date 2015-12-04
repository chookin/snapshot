package cmri.snapshot.api.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zhuyin on 12/3/15.
 */
@Entity
@Table(name = "user_comment", schema = "", catalog = "snapshot")
public class UserComment {
    private long id;
    private long userId;
    private long object;
    private long parent;
    private String content;
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
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "object")
    public long getObject() {
        return object;
    }

    public void setObject(long object) {
        this.object = object;
    }

    @Basic
    @Column(name = "parent")
    public long getParent() {
        return parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

        UserComment that = (UserComment) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (object != that.object) return false;
        if (parent != that.parent) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (object ^ (object >>> 32));
        result = 31 * result + (int) (parent ^ (parent >>> 32));
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
