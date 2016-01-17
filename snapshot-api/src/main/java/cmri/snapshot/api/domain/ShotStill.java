package cmri.snapshot.api.domain;

import javax.persistence.*;

/**
 * Created by zhuyin on 1/17/16.
 */
@Entity
@Table(name = "shot_still")
public class ShotStill {
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

        ShotStill shotStill = (ShotStill) o;

        if (id != shotStill.id) return false;
        if (shotId != shotStill.shotId) return false;
        if (pic != null ? !pic.equals(shotStill.pic) : shotStill.pic != null) return false;

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
