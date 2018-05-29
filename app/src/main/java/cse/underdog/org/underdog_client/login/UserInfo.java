package cse.underdog.org.underdog_client.login;

public class UserInfo {
    public int user_id;
    public String nickname;
    public String email;

    public UserInfo(){

    }

    public UserInfo(int user_id, String nickname, String email){
        this.user_id = user_id;
        this.nickname = nickname;
        this.email = email;
    }
}
