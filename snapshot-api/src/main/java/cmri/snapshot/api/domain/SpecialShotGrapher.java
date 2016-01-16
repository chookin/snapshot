package cmri.snapshot.api.domain;

import javax.persistence.*;

/**
 * Created by zhuyin on 1/15/16.
 */
@Entity
@Table(name = "special_shot_grapher", schema = "", catalog = "snapshot")
public class SpecialShotGrapher {
    private long id;
    private long shotId;
    private long grapherId;

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
    @Column(name = "grapher_id")
    public long getGrapherId() {
        return grapherId;
    }

    public void setGrapherId(long grapherId) {
        this.grapherId = grapherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpecialShotGrapher that = (SpecialShotGrapher) o;

        if (id != that.id) return false;
        if (shotId != that.shotId) return false;
        if (grapherId != that.grapherId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (shotId ^ (shotId >>> 32));
        result = 31 * result + (int) (grapherId ^ (grapherId >>> 32));
        return result;
    }
}
