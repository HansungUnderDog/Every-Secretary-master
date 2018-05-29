package cse.underdog.org.underdog_client.memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import cse.underdog.org.underdog_client.AllInOneActivity;
import cse.underdog.org.underdog_client.BottomNavigationViewHelper;
import cse.underdog.org.underdog_client.R;
import cse.underdog.org.underdog_client.application.ApplicationController;
import cse.underdog.org.underdog_client.etc.EtcActivity;
import cse.underdog.org.underdog_client.network.NetworkService;
import cse.underdog.org.underdog_client.timeline.TimelineActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNote extends AppCompatActivity {

    private NetworkService service;

    EditText editText_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        service = ApplicationController.getInstance().getNetworkService();

        editText_context = (EditText)findViewById(R.id.editText_context) ;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMemoCreate);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.ic_android:
                        Intent intent1 = new Intent(CreateNote.this, TimelineActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_books:
                        Intent intent2 = new Intent(CreateNote.this, AllInOneActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_center_focus:
                        Intent intent3 = new Intent(CreateNote.this, MemoActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_backup:
                        Intent intent4 = new Intent(CreateNote.this, EtcActivity.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, MemoActivity.class);
        startActivity(setIntent);
    }

    /*void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_save:
                createMemo();
                Intent intent = new Intent(CreateNote.this, MemoActivity.class);
                startActivity(intent);

                return true;
                /*cv.put(mDbHelper.TITLE, title);
                cv.put(mDbHelper.DETAIL, detail);
                cv.put(mDbHelper.TYPE, type);
                cv.put(mDbHelper.TIME, getString(R.string.Not_Set));*/


            case R.id.action_back:
                Intent openMainActivity = new Intent(CreateNote.this, MemoActivity.class);
                startActivity(openMainActivity);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void createMemo() {
        MemoInfo info = new MemoInfo(editText_context.getText().toString());
        System.out.println("인포가뭐람"+info);
        Call<MemoResult> createMemo = service.createMemo(info);
        createMemo.enqueue(new Callback<MemoResult>() {
            @Override
            public void onResponse(Call<MemoResult> call, Response<MemoResult> response) {
                if (response.isSuccessful()) {
                    System.out.println("response.body" + response.body().stat);
                    if (response.body().stat.equals("success")) {
                        System.out.println("여기메모");
                    }
                }else{
                    Toast.makeText(getBaseContext(), "메모 로딩 실패", Toast.LENGTH_SHORT).show();
                }
            }



            @Override
            public void onFailure(Call<MemoResult> call, Throwable t) {
                Toast.makeText(getBaseContext(), "통신 에러CreateNote", Toast.LENGTH_SHORT).show();
                Log.e("fail", t.getMessage());
            }
        });
    }
}
