package cmri.snapshot.api.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by zhuyin on 1/19/16.
 */
@Entity
public class Likes {
    private long id;
    private long objectId;
    private long commentatorId;
    private byte type;
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
    @Column(name = "object_id")
    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    @Basic
    @Column(name = "commentator_id")
    public long getCommentatorId() {
        return commentatorId;
    }

    public void setCommentatorId(long commentatorId) {
        this.commentatorId = commentatorId;
    }

    @Basic
    @Column(name = "type")
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
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

        Likes likes = (Likes) o;

        if (id != likes.id) return false;
        if (objectId != likes.objectId) return false;
        if (commentatorId != likes.commentatorId) return false;
        if (type != likes.type) return false;
        if (time != null ? !time.equals(likes.time) : likes.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (objectId ^ (objectId >>> 32));
        result = 31 * result + (int) (commentatorId ^ (commentatorId >>> 32));
        result = 31 * result + (int) type;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
