package cmri.snapshot.api.domain;

import javax.persistence.*;

/**
 * Created by zhuyin on 1/21/16.
 */
@Entity
@Table(name = "group_shot_still", schema = "", catalog = "snapshot")
public class GroupShotStill {
    private long id;
    private long shotId;
    private String pic;

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
    @Column(name = "shot_id")
    public long getShotId() {
        return shotId;
    }

    public void setShotId(long shotId) {
        this.shotId = shotId;
    }

    @Basic
    @Column(name = "pic")
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupShotStill that = (GroupShotStill) o;

        if (id != that.id) return false;
        if (shotId != that.shotId) return false;
        if (pic != null ? !pic.equals(that.pic) : that.pic != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (shotId ^ (shotId >>> 32));
        result = 31 * result + (pic != null ? pic.hashCode() : 0);
        return result;
    }
}
