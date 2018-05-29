package cse.underdog.org.underdog_client.login;

public class SignupResult {
    UserInfo data = new UserInfo();
    String stat;

    public SignupResult(String stat){
        this.stat = stat;
    }

    public SignupResult(UserInfo data, String stat){
        this.data = data;
        this.stat = stat;
    }
}
