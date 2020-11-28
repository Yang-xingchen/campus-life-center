package campuslifecenter.usercenter.service;

import campuslifecenter.usercenter.entry.SignInLog;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.SignInType;

public interface AccountService {

    String signInId();

    SignInType signIn(String aid, String pwd, SignInLog sign);

    boolean signOut(String aid);

    boolean checkSignInId(String uuid);

    boolean startSecurity(String aid, String securityPwd, String key);

    boolean exitSecurity(String aid);

    AccountInfo getAccountInfo(String cookie);

}
