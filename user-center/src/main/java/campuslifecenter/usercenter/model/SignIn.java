package campuslifecenter.usercenter.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

public class SignIn implements Serializable {

    @ApiModelProperty("账户id")
    private String aid;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("登录id, 实现幂等")
    private String signInId;

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

    public String getSignInId() {
        return signInId;
    }

    public SignIn setSignInId(String signInId) {
        this.signInId = signInId;
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
        return Objects.equals(signInId, signIn.signInId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(signInId);
    }

    @Override
    public String toString() {
        return "SignIn{" +
                "aid='" + aid + '\'' +
                ", signInId='" + signInId + '\'' +
                '}';
    }
}
