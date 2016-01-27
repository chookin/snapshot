package cmri.snapshot.api.domain;

import javax.persistence.*;

/**
 * Created by zhuyin on 1/27/16.
 */
@Entity
@Table(name = "comment_stat", schema = "", catalog = "snapshot")
public class CommentStat {
    private long id;
    private long objectId;
    private byte type;
    private long count;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "type")
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Basic
    @Column(name = "count")
    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentStat that = (CommentStat) o;

        if (id != that.id) return false;
        if (objectId != that.objectId) return false;
        if (type != that.type) return false;
        if (count != that.count) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (objectId ^ (objectId >>> 32));
        result = 31 * result + (int) type;
        result = 31 * result + (int) (count ^ (count >>> 32));
        return result;
    }
}
