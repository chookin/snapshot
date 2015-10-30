package cmri.snapshot.api.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by zhuyin on 10/28/15.
 */
@Entity
public class User implements Serializable{
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getMobile() {
        return mobile;
    }
    public String getEmail() {
        return email;
    }
    // must have a default constructor, or else throw exception: "nested exception is org.hibernate.InstantiationException: No default constructor for entity".
    public User(){}
    public User(String name, String password, String mobile, String email){
        this.name = name;
        this.password = password;
        this.mobile = mobile;
        this.email = email;
    }
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String password;
    private String mobile;
    private String email;
}
