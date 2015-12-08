package cmri.snapshot.api.domain;

import javax.persistence.*;

/**
 * Created by zhuyin on 12/8/15.
 */
@Entity
@Table(name = "group_shot_poster")
public class GroupShotPoster {
    private long id;
    private long shotId;
    private String photo;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "shot_id")
    public long getShotId() {
        return shotId;
    }

    public void setShotId(long shotId) {
        this.shotId = shotId;
    }

    @Basic
    @Column(name = "photo")
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupShotPoster that = (GroupShotPoster) o;

        if (id != that.id) return false;
        if (shotId != that.shotId) return false;
        if (photo != null ? !photo.equals(that.photo) : that.photo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (shotId ^ (shotId >>> 32));
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        return result;
    }
}
