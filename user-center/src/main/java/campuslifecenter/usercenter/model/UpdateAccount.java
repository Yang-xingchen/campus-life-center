package campuslifecenter.usercenter.model;

import campuslifecenter.usercenter.entry.Account;

public class UpdateAccount extends Account {
    private String originalPassword;

    public String getOriginalPassword() {
        return originalPassword;
    }

    public UpdateAccount setOriginalPassword(String originalPassword) {
        this.originalPassword = originalPassword;
        return this;
    }
}
