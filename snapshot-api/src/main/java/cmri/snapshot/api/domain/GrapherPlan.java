package cmri.snapshot.api.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zhuyin on 12/3/15.
 */
@Entity
@Table(name = "grapher_plan", schema = "", catalog = "snapshot")
public class GrapherPlan {
    private long id;
    private long userId;
    private Integer shootNum;
    private Integer shootHour;
    private Integer truingNum;
    private Integer printNum;
    private String clothing;
    private String makeup;
    private Timestamp createTime;

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
    @Column(name = "shoot_num")
    public Integer getShootNum() {
        return shootNum;
    }

    public void setShootNum(Integer shootNum) {
        this.shootNum = shootNum;
    }

    @Basic
    @Column(name = "shoot_hour")
    public Integer getShootHour() {
        return shootHour;
    }

    public void setShootHour(Integer shootHour) {
        this.shootHour = shootHour;
    }

    @Basic
    @Column(name = "truing_num")
    public Integer getTruingNum() {
        return truingNum;
    }

    public void setTruingNum(Integer truingNum) {
        this.truingNum = truingNum;
    }

    @Basic
    @Column(name = "print_num")
    public Integer getPrintNum() {
        return printNum;
    }

    public void setPrintNum(Integer printNum) {
        this.printNum = printNum;
    }

    @Basic
    @Column(name = "clothing")
    public String getClothing() {
        return clothing;
    }

    public void setClothing(String clothing) {
        this.clothing = clothing;
    }

    @Basic
    @Column(name = "makeup")
    public String getMakeup() {
        return makeup;
    }

    public void setMakeup(String makeup) {
        this.makeup = makeup;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GrapherPlan that = (GrapherPlan) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (shootNum != null ? !shootNum.equals(that.shootNum) : that.shootNum != null) return false;
        if (shootHour != null ? !shootHour.equals(that.shootHour) : that.shootHour != null) return false;
        if (truingNum != null ? !truingNum.equals(that.truingNum) : that.truingNum != null) return false;
        if (printNum != null ? !printNum.equals(that.printNum) : that.printNum != null) return false;
        if (clothing != null ? !clothing.equals(that.clothing) : that.clothing != null) return false;
        if (makeup != null ? !makeup.equals(that.makeup) : that.makeup != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (shootNum != null ? shootNum.hashCode() : 0);
        result = 31 * result + (shootHour != null ? shootHour.hashCode() : 0);
        result = 31 * result + (truingNum != null ? truingNum.hashCode() : 0);
        result = 31 * result + (printNum != null ? printNum.hashCode() : 0);
        result = 31 * result + (clothing != null ? clothing.hashCode() : 0);
        result = 31 * result + (makeup != null ? makeup.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
