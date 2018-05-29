package cse.underdog.org.underdog_client.network;

import cse.underdog.org.underdog_client.login.LoginInfo;
import cse.underdog.org.underdog_client.login.LoginResult;
import cse.underdog.org.underdog_client.login.LogoutResult;
import cse.underdog.org.underdog_client.login.SignupInfo;
import cse.underdog.org.underdog_client.login.SignupResult;
import cse.underdog.org.underdog_client.memo.MemoInfo;
import cse.underdog.org.underdog_client.memo.MemoResult;
import cse.underdog.org.underdog_client.schedule.ScheduleResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NetworkService {
    //ToDo: api 문서보고 url 맞추기
    @POST("/login/signin")
    Call<LoginResult> checkLogin(@Body LoginInfo loginInfo);

    @POST("/login/signup")
    Call<SignupResult> checkSignup(@Body SignupInfo signupInfo);

    @GET("/login/logout")
    Call<LogoutResult> checkLogout();

    @POST("/memo/creatememo")
    Call<MemoResult> createMemo(@Body MemoInfo memoInfo);

    @POST("/memo/deletememo")
    Call<MemoResult> deleteMemo(@Body MemoInfo memoInfo);

    @POST("/memo/updatememo")
    Call<MemoResult> updateMemo(@Body MemoInfo memoInfo);

    @POST("/memo/showmemo")
    Call<MemoResult> showMemo();

    @POST("/schedule/showschedule")
    Call<ScheduleResult> getSchedule();

   /* @POST("/logout/logout")
    Call<LogoutResult> logout();

    @POST("/signup/up")
    Call<JoinResult> getJoinResult(@Body JoinInfo joinInfo);*/


}
