package cmri.snapshot.api.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zhuyin on 11/15/15.
 */
@Entity
public class Login {
    private long id;
    private long userId;
    private String loginIp;
    private Timestamp time;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public Login setId(long id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "user_id")
    public long getUserId() {
        return userId;
    }

    public Login setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    @Basic
    @Column(name = "login_ip")
    public String getLoginIp() {
        return loginIp;
    }

    public Login setLoginIp(String loginIp) {
        this.loginIp = loginIp;
        return this;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public Login setTime(Timestamp time) {
        this.time = time;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Login login = (Login) o;

        if (id != login.id) return false;
        if (userId != login.userId) return false;
        if (loginIp != null ? !loginIp.equals(login.loginIp) : login.loginIp != null) return false;
        if (time != null ? !time.equals(login.time) : login.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (loginIp != null ? loginIp.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", userId=" + userId +
                ", loginIp='" + loginIp + '\'' +
                ", time=" + time +
                '}';
    }
}
