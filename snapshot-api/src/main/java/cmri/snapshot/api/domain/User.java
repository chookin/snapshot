package cmri.snapshot.api.domain;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by zhuyin on 10/30/15.
 */
@Entity
public class User {
    private long id;
    private String name;
    private String password;
    private String email;
    private Byte role;
    private Byte status;
    private Timestamp createTime = new Timestamp(System.currentTimeMillis());
    private Timestamp updateTime;
    private Date birthday;
    private String sex;
    private Integer area;
    private String avatar;
    private Timestamp registerTime;
    private String realName;
    private String idNum;
    private int orderCount;
    private int collectedCount;
    private Long phone;
    private String profile;
    private String idImage;
    private Integer appointmentCount;

    public User(){}

    public User(String name, String password, Long phone){
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    @Basic
    @Column(name = "role")
    public Byte getRole() {
        return role;
    }

    public User setRole(Byte role) {
        this.role = role;
        return this;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public User setStatus(Byte status) {
        this.status = status;
        return this;
    }

    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public User setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;
        if (status != null ? !status.equals(user.status) : user.status != null) return false;
        if (createTime != null ? !createTime.equals(user.createTime) : user.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(user.updateTime) : user.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    public User setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    @Basic
    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public User setSex(String sex) {
        this.sex = sex;
        return this;
    }

    @Basic
    @Column(name = "area")
    public Integer getArea() {
        return area;
    }

    public User setArea(Integer area) {
        this.area = area;
        return this;
    }

    @Basic
    @Column(name = "avatar")
    public String getAvatar() {
        return avatar;
    }

    public User setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    @Basic
    @Column(name = "register_time")
    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public User setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
        return this;
    }

    @Basic
    @Column(name = "real_name")
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Basic
    @Column(name = "id_num")
    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    @Basic
    @Column(name = "order_count")
    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    @Basic
    @Column(name = "collected_count")
    public int getCollectedCount() {
        return collectedCount;
    }

    public void setCollectedCount(int collectedCount) {
        this.collectedCount = collectedCount;
    }

    @Basic
    @Column(name = "phone")
    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "profile")
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Basic
    @Column(name = "id_image")
    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    @Basic
    @Column(name = "appointment_count")
    public Integer getAppointmentCount() {
        return appointmentCount;
    }

    public void setAppointmentCount(Integer appointmentCount) {
        this.appointmentCount = appointmentCount;
    }
}
