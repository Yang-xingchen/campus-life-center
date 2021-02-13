package campuslifecenter.usercenter.model;

import java.util.List;

public class PermissionConst {

    public static final String SYSTEM_ACCOUNT = "account";
    public static final String SYSTEM_MONITOR = "monitor";
    public static final List<String> SYSTEM_LIST = List.of(SYSTEM_ACCOUNT, SYSTEM_MONITOR);

    public static final String ORGANIZATION_INFO = "info";
    public static final String ORGANIZATION_CHILD = "child";
    public static final String ORGANIZATION_MEMBER = "member";

    public static final String NOTICE_IMPORTANCE_1 = "importance:1";
    public static final String NOTICE_IMPORTANCE_2 = "importance:2";
    public static final String NOTICE_IMPORTANCE_3 = "importance:3";
    public static final String NOTICE_IMPORTANCE_4 = "importance:4";
    public static final String NOTICE_IMPORTANCE_5 = "importance:5";

}
