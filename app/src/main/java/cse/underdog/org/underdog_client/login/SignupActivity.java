package cse.underdog.org.underdog_client.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cse.underdog.org.underdog_client.R;
import cse.underdog.org.underdog_client.application.ApplicationController;
import cse.underdog.org.underdog_client.network.NetworkService;
import cse.underdog.org.underdog_client.timeline.TimelineActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "Signup";

    @BindView(R.id.input_password)
    EditText editTextPassword;
    @BindView(R.id.input_email)
    EditText editTextEmail;
    /*@BindView(R.id.button_signup_email)
    EditText buttonEmail;*/
    @BindView(R.id.input_name)
    EditText editTextNickname;
    /*@BindView(R.id.button_signup_nickname)
    EditText buttonNickname;*/
    /*@BindView(R.id.edittext_signup_passwordre)
    EditText editTextPasswordRe;*/
    @BindView(R.id.btn_signup)
    Button buttonSignup;
    @BindView(R.id.link_login)
    TextView _loginLink;

    private NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        service = ApplicationController.getInstance().getNetworkService();

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSignup();
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        /*agreementImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, AgreeActivity.class);
                startActivity(intent);

            }
        });*/
    }

    private boolean isValid() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        /*String rePassword = editTextPasswordRe.getText().toString();*/

        String regEmail = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";

        if (email.equals("")) {
            //이메일 미입력
            Toast.makeText(getBaseContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        /*if (password.equals("") || rePassword.equals("")) {
            //비밀번호 미입력
            Toast.makeText(getBaseContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }*/

        if (!email.matches(regEmail)) {
            //이메일 형식을 맞추지 않았을 때,
            Toast.makeText(getBaseContext(), "이메일 형식을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show();
            editTextEmail.requestFocus();
            return false;
        }
        /*if (!password.equals(rePassword)) {
            //두 비밀번호가 같지 않을 때,
            Toast.makeText(getBaseContext(), "비밀번호가 일치하지 않습니다. 다시 한번 확인해주세요.", Toast.LENGTH_SHORT).show();
            editTextPasswordRe.requestFocus();
            return false;
        }*/

        return true;
    }

    public void checkSignup() {
        this.checkSignup(null, null, null,false);
    }

    public void checkSignup(String email, String pwd, String nickname, final boolean isAuto) {

        email = editTextEmail.getText().toString();
        pwd = editTextPassword.getText().toString();
        nickname = editTextNickname.getText().toString();
        /*String rePassword = editTextPasswordRe.getText().toString();*/

        String regEmail = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";

        if (email.equals("")) {
            //이메일 미입력
            Toast.makeText(getBaseContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        /*if (password.equals("") || rePassword.equals("")) {
            //비밀번호 미입력
            Toast.makeText(getBaseContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }*/

        if (!email.matches(regEmail)) {
            //이메일 형식을 맞추지 않았을 때,
            Toast.makeText(getBaseContext(), "이메일 형식을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show();
            editTextEmail.requestFocus();
        }
        /*if (!password.equals(rePassword)) {
            //두 비밀번호가 같지 않을 때,
            Toast.makeText(getBaseContext(), "비밀번호가 일치하지 않습니다. 다시 한번 확인해주세요.", Toast.LENGTH_SHORT).show();
            editTextPasswordRe.requestFocus();
            return false;
        }*/

        SignupInfo info = new SignupInfo(email, pwd, nickname);
        //회원 체크
        Call<SignupResult> checkSignup = service.checkSignup(info);
        checkSignup.enqueue(new Callback<SignupResult>() {
            @Override
            public void onResponse(Call<SignupResult> call, Response<SignupResult> response) {
                if (response.isSuccessful()) {
                    System.out.println("response.body" + response.body().stat);
                    if (response.body().stat.equals("success")) {
                        System.out.println("hihihihi");
/*                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(intent);*/
                        /*Fragment fragment = new Fragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_timeline, fragment);
                        fragmentTransaction.commit();*/
                    }
                }else{
                    Toast.makeText(getBaseContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                    /*System.out.println("failfaifli");
                    System.out.println(response.body());
                    if (response.body().stat.equals("nonidfail")) {
                        Toast.makeText(getBaseContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    } else if (response.body().stat.equals("pwdfail")) {
                        Toast.makeText(getBaseContext(), "회원가입되지 않은 회원 정보입니다.", Toast.LENGTH_SHORT).show();
                    } */
                }
            }



            @Override
            public void onFailure(Call<SignupResult> call, Throwable t) {
                Toast.makeText(getBaseContext(), "통신 에러", Toast.LENGTH_SHORT).show();
                Log.e("fail", t.getMessage());
            }
        });
    }

}
