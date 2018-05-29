package cse.underdog.org.underdog_client.memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

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

public class MemoActivity extends AppCompatActivity {
    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
    private FloatingActionButton mFabAdd;
    private static final int DELETE_ID = Menu.FIRST;
    private int mNoteNumber = 1;
    private NetworkService service;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    ArrayList<MemoInfo> memos= new ArrayList<MemoInfo>();

    ListView list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_memo);


        service = ApplicationController.getInstance().getNetworkService();

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
                        Intent intent1 = new Intent(MemoActivity.this, TimelineActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_books:
                        Intent intent2 = new Intent(MemoActivity.this, AllInOneActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_center_focus:

                        break;

                    case R.id.ic_backup:
                        Intent intent4 = new Intent(MemoActivity.this, EtcActivity.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMemo);
        setSupportActionBar(toolbar);


/*        mFabAdd = (FloatingActionButton) findViewById(R.id.fab);
        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*createEvent()*//*;
            }
        });
        //noinspection ConstantConditions
        mFabAdd.hide();*/

        getMemo();

        list = (ListView)findViewById(R.id.commentslist);
        /*list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        MemoAdapter memoAdapter = new MemoAdapter(this,R.layout.list_entry,memos);
        list.setAdapter(memoAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();

        getMemo();

        list = (ListView)findViewById(R.id.commentslist);
        /*list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        MemoAdapter memoAdapter = new MemoAdapter(this,R.layout.list_entry,memos);
        list.setAdapter(memoAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_memo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_new:
                Intent openCreateNote = new Intent(MemoActivity.this, CreateNote.class);
                startActivity(openCreateNote);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void getMemo() {
        //MemoInfo info = new MemoInfo();
        Call<MemoResult> showMemo = service.showMemo();

        showMemo.enqueue(new Callback<MemoResult>() {
            @Override
            public void onResponse(Call<MemoResult> call, Response<MemoResult> response) {
                if (response.isSuccessful()) {
                    System.out.println("response.body" + response.body().stat);
                    if (response.body().stat.equals("success")) {
                        System.out.println("여기메모");

                        for(int i=0; i<response.body().data.size(); i++){
                            memos.add(response.body().data.get(i));
                            System.out.println("메모있나" + memos.get(i).context);
                        }
                    }
                }else{
                    Toast.makeText(getBaseContext(), "메모 로딩 실패", Toast.LENGTH_SHORT).show();
                }
            }



            @Override
            public void onFailure(Call<MemoResult> call, Throwable t) {
                Toast.makeText(getBaseContext(), "통신 에러MemoActivity", Toast.LENGTH_SHORT).show();
                Log.e("fail", t.getMessage());
            }
        });
    }
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            Intent intent = new Intent(this, TimelineActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "뒤로 가기 키를 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
