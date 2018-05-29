package cse.underdog.org.underdog_client;

import cse.underdog.org.underdog_client.login.UserInfo;


public class LoginUserInfo {
    private static LoginUserInfo instance = null;


    private LoginUserInfo() {

    }

    public static synchronized LoginUserInfo getInstance() {
        if (instance == null) {
            instance = new LoginUserInfo();
        }
        return instance;
    }

    private UserInfo userObject;

    public UserInfo getUserInfo() {
        return userObject;
    }

    public void setUserInfo(UserInfo userObject) {
        this.userObject = userObject;
    }
}
