package cmri.snapshot.api.domain;

import cmri.snapshot.api.helper.ModelHelper;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zhuyin on 1/21/16.
 */
@Entity
@Table(name = "group_shot", schema = "", catalog = "snapshot")
public class GroupShot {
    private long id;
    private int price;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp registrationDeadline;
    private String title;
    private String intro;
    private String summary;
    private String location;
    private String service;
    private int minNumber;
    private int maxNumber;
    private int enrolledNumber;
    private byte status;
    private long creator;
    private Timestamp createTime;
    private static final AtomicLong idGen = new AtomicLong();

    static {
        Long id = ModelHelper.getMaxId("special_shot");
        if (id != null) idGen.set(id);
    }

    public static GroupShot newOne() {
        GroupShot shot = new GroupShot();
        shot.setId(nextId());
        shot.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return shot;
    }

    public static long nextId() {
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
    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Basic
    @Column(name = "start_time")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "registration_deadline")
    public Timestamp getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(Timestamp registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "intro")
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Basic
    @Column(name = "summary")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Basic
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "service")
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Basic
    @Column(name = "min_number")
    public int getMinNumber() {
        return minNumber;
    }

    public void setMinNumber(int minNumber) {
        this.minNumber = minNumber;
    }

    @Basic
    @Column(name = "max_number")
    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    @Basic
    @Column(name = "enrolled_number")
    public int getEnrolledNumber() {
        return enrolledNumber;
    }

    public void setEnrolledNumber(int enrolledNumber) {
        this.enrolledNumber = enrolledNumber;
    }

    @Basic
    @Column(name = "status")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "creator")
    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
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

        GroupShot groupShot = (GroupShot) o;

        if (id != groupShot.id) return false;
        if (price != groupShot.price) return false;
        if (minNumber != groupShot.minNumber) return false;
        if (maxNumber != groupShot.maxNumber) return false;
        if (enrolledNumber != groupShot.enrolledNumber) return false;
        if (status != groupShot.status) return false;
        if (startTime != null ? !startTime.equals(groupShot.startTime) : groupShot.startTime != null) return false;
        if (endTime != null ? !endTime.equals(groupShot.endTime) : groupShot.endTime != null) return false;
        if (registrationDeadline != null ? !registrationDeadline.equals(groupShot.registrationDeadline) : groupShot.registrationDeadline != null)
            return false;
        if (title != null ? !title.equals(groupShot.title) : groupShot.title != null) return false;
        if (intro != null ? !intro.equals(groupShot.intro) : groupShot.intro != null) return false;
        if (summary != null ? !summary.equals(groupShot.summary) : groupShot.summary != null) return false;
        if (location != null ? !location.equals(groupShot.location) : groupShot.location != null) return false;
        if (service != null ? !service.equals(groupShot.service) : groupShot.service != null) return false;
        if (createTime != null ? !createTime.equals(groupShot.createTime) : groupShot.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + price;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (registrationDeadline != null ? registrationDeadline.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (intro != null ? intro.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (service != null ? service.hashCode() : 0);
        result = 31 * result + minNumber;
        result = 31 * result + maxNumber;
        result = 31 * result + enrolledNumber;
        result = 31 * result + (int) status;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
