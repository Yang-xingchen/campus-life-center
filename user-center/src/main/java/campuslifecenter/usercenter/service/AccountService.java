package campuslifecenter.usercenter.service;

import campuslifecenter.usercenter.entry.Account;
import campuslifecenter.usercenter.entry.SignInLog;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.UpdateAccount;

import java.util.List;
import java.util.Map;

public interface AccountService {

    /**
     * 获取登录id
     */
    String signInId();

    /**
     * 登录
     * @param aid 账户id
     * @param pwd 账户密码
     * @param sign 登录信息
     * @return 登录状态
     */
    String signIn(String aid, String pwd, SignInLog sign);

    boolean signInByToken(String token, SignInLog sign);

    /**
     * 登出
     * @param aid 账户id
     */
    boolean signOut(String aid, String token);

    boolean signOutOther(String id, String token);

    /**
     * 检查token合法性
     * 用于记住我功能
     * @param token token
     */
    boolean checkToken(String token);

    /**
     * 获取账户信息
     * @param token token
     */
    AccountInfo getAccountInfo(String token);

    /**
     * 批量添加账户
     * @param accounts 账户信息
     * @return 添加失败列表
     */
    Map<Boolean, List<Account>> addAllAccount(List<Account> accounts);

    /**
     * 查询所有用户信息
     * @return 用户信息
     */
    List<AccountInfo> findAllAccount();

    AccountInfo getAccount(String id);

    List<AccountInfo> getAccountInfos(List<String> ids);

    boolean update(UpdateAccount account);

    List<SignInLog> signInLogs(String id);
}
