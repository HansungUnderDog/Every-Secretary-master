package cse.underdog.org.underdog_client.login;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import cse.underdog.org.underdog_client.AllInOneActivity;
import cse.underdog.org.underdog_client.LoginUserInfo;
import cse.underdog.org.underdog_client.R;
import cse.underdog.org.underdog_client.alarm.CallingService;
import cse.underdog.org.underdog_client.application.ApplicationController;
import cse.underdog.org.underdog_client.guide.GuideActivity;
import cse.underdog.org.underdog_client.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

//    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private NetworkService service;
    private Handler handler;

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 5469;
    public static int  MY_PERMISSIONS_REQUEST_CALL_PHONE = 3379;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e("퍼미션받아옴","ㄱㄱ");
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else Log.e("퍼미션else","실행");


        if (!Settings.canDrawOverlays(this)) {
            testPermission();
        } else {
            Log.e("스플래쉬Result","서비스 실행");
            handler = new Handler() {
                public void handleMessage(Message msg) {
                    SharedPreferences userInfo;
                    userInfo = getSharedPreferences("users", MODE_PRIVATE);
                    String remainEmail = userInfo.getString("email", null);
                    if (remainEmail == null) {
                        Intent mintent = new Intent(getBaseContext(), GuideActivity.class); //LoginActivity 로 다시 바꿔야함
                        startActivity(mintent);
                        finish();
                    } else {
                        checkLogin(remainEmail, userInfo.getString("pwd", null));
                    }
                }
            };

            handler.sendEmptyMessageDelayed(0, 3000);
        }

        service = ApplicationController.getInstance().getNetworkService();

        /*new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                *//* 다음액티비티를 실행하고 로딩화면을 죽인다.*//*
                Intent intent = new Intent(SplashActivity.this,GuideActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);*/


    }

    @TargetApi(Build.VERSION_CODES.M)
    public void testPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
        }
    }

    public void checkLogin(String email, String pwd) {
        LoginInfo info = new LoginInfo(email, pwd);
        //회원 체크
        Call<LoginResult> checkLogin = service.checkLogin(info);
        checkLogin.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.isSuccessful()) {
                    if (response.body().stat.equals("success")) {
                        //로그인 성공 시
                        LoginResult userObj = new LoginResult(response.body().data, response.body().stat);
                        LoginUserInfo.getInstance().setUserInfo(userObj.data);
                        SharedPreferences userInfo;
                        userInfo = getSharedPreferences("users", MODE_PRIVATE);

                        SharedPreferences.Editor editor = userInfo.edit();

                        //sharedPreferences에 유저정보 객체로 저장


                        Intent intent = new Intent(getBaseContext(), AllInOneActivity.class);
                        finish();


                        //activity stack 비우고 새로 시작하기
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                            //안드로이드 버전이 진저브레드가 아니면,
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        } else {
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        }

                        startActivity(intent);
                    } else{
                        Toast.makeText(getBaseContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {

            }
        });
    }

    public void onBackPressed() {

        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            this.finish();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "뒤로 가기 키를 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        LinearLayout layoutMainView = (LinearLayout) this.findViewById(R.id.asdf);

        Log.w("Layout Width - ", String.valueOf(layoutMainView.getWidth()));
        Log.w("Layout Height - ", String.valueOf(layoutMainView.getHeight()));
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                // You have permission
                handler = new Handler() {
                    public void handleMessage(Message msg) {
                        SharedPreferences userInfo;
                        userInfo = getSharedPreferences("users", MODE_PRIVATE);
                        String remainEmail = userInfo.getString("email", null);
                        if (remainEmail == null) {
                            Intent mintent = new Intent(getBaseContext(), GuideActivity.class); //LoginActivity 로 다시 바꿔야함
                            startActivity(mintent);
                            finish();
                        } else {
                            checkLogin(remainEmail, userInfo.getString("pwd", null));
                        }
                    }
                };

                handler.sendEmptyMessageDelayed(0, 3000);
            }
        }
    }
}
