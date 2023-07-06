package org.store;

import java.util.Map;

public class LoginInfo {
    private static String loginName;
    private static String loginPw;
    public String getLoginName() {

        return loginName;
    }
    public void setLoginName(String loginName) {

        this.loginName = loginName;
    }
    public String getLoginPw() {

        return loginPw;
    }
    public void setLoginPw(String loginPw) {

        this.loginPw = loginPw;
    }
    static Map<String, MemberInfo> loginMembers;
}
