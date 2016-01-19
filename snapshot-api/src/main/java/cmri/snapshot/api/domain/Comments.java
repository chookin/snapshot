package cmri.snapshot.api.domain;

import cmri.snapshot.api.helper.ModelHelper;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhuyin on 1/19/16.
 */
@Entity
public class Comments {
    private long id;
    private long objectId;
    private long commentatorId;
    private long parent;
    private String content;
    private byte type;
    private Timestamp time;
    private static final AtomicLong idGen = new AtomicLong();
    static {
        Long id = ModelHelper.getMaxId("comments");
        if(id != null) idGen.set(id);
    }
    public static Comments newOne(){
        Comments comments = new Comments();
        comments.setId(nextId());
        comments.setTime(new Timestamp(System.currentTimeMillis()));
        return comments;
    }
    public static long nextId(){
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

        Comments comments = (Comments) o;

        if (id != comments.id) return false;
        if (objectId != comments.objectId) return false;
        if (commentatorId != comments.commentatorId) return false;
        if (parent != comments.parent) return false;
        if (type != comments.type) return false;
        if (content != null ? !content.equals(comments.content) : comments.content != null) return false;
        if (time != null ? !time.equals(comments.time) : comments.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (objectId ^ (objectId >>> 32));
        result = 31 * result + (int) (commentatorId ^ (commentatorId >>> 32));
        result = 31 * result + (int) (parent ^ (parent >>> 32));
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (int) type;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
