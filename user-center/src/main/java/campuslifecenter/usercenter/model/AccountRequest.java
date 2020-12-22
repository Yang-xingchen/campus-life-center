package campuslifecenter.usercenter.model;

import campuslifecenter.usercenter.entry.Account;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AccountRequest {

    @ApiModelProperty("token")
    private String token;
    @ApiModelProperty("账户列表")
    private List<Account> accounts;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
