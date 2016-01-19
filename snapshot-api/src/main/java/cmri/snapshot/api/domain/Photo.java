package cmri.snapshot.api.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zhuyin on 12/4/15.
 */
@Entity
public class Photo {
    private long id;
    private long userId;
    private Long workId;
    private String photo;
    private int likeCount;
    private int commentCount;
    private Timestamp time;

    public static Photo newOne(){
        return new Photo()
                .setTime(new Timestamp(System.currentTimeMillis()))
                ;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

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
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "photo")
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public Photo setTime(Timestamp time) {
        this.time = time;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo1 = (Photo) o;

        if (id != photo1.id) return false;
        if (userId != photo1.userId) return false;
        if (likeCount != photo1.likeCount) return false;
        if (commentCount != photo1.commentCount) return false;
        if (photo != null ? !photo.equals(photo1.photo) : photo1.photo != null) return false;
        if (time != null ? !time.equals(photo1.time) : photo1.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + likeCount;
        result = 31 * result + commentCount;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "work_id")
    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }
}
