package campuslifecenter.usercenter.model;

import java.io.Serializable;
import java.util.Objects;

public class SignIn implements Serializable {

    private String aid;
    private String password;
    private String cookie;

    public SignIn() {
    }

    public SignIn(String aid, String password) {
        this.aid = aid;
        this.password = password;
    }

    public String getAid() {
        return aid;
    }

    public SignIn setAid(String aid) {
        this.aid = aid;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SignIn setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getCookie() {
        return cookie;
    }

    public SignIn setCookie(String cookie) {
        this.cookie = cookie;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SignIn signIn = (SignIn) o;
        return Objects.equals(cookie, signIn.cookie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookie);
    }

    @Override
    public String toString() {
        return "SignIn{" +
                "aid='" + aid + '\'' +
                ", cookie='" + cookie + '\'' +
                '}';
    }
}
