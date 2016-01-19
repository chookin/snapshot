package cmri.snapshot.api.domain;

import cmri.snapshot.api.helper.ModelHelper;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhuyin on 12/3/15.
 */
@Entity
@Table(name = "user_comment")
public class UserComment {
    private long id;
    private long commentatorId;
    private long object;
    private long parent;
    private String content;
    private Timestamp time;
    private static final AtomicLong idGen = new AtomicLong();
    static {
        Long id = ModelHelper.getMaxId("works");
        if(id != null) idGen.set(id);
    }
    public static UserComment newOne(){
        return new UserComment()
                .setId(nextId())
                .setTime(new Timestamp(System.currentTimeMillis()))
                ;
    }
    public static long nextId(){
        return idGen.incrementAndGet();
    }
    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public UserComment setId(long id) {
        this.id = id;
        return this;
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

    public UserComment setTime(Timestamp time) {
        this.time = time;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserComment that = (UserComment) o;

        if (id != that.id) return false;
        if (commentatorId != that.commentatorId) return false;
        if (object != that.object) return false;
        if (parent != that.parent) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (commentatorId ^ (commentatorId >>> 32));
        result = 31 * result + (int) (object ^ (object >>> 32));
        result = 31 * result + (int) (parent ^ (parent >>> 32));
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
