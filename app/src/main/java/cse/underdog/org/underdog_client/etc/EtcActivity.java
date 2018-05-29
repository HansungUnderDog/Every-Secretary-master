package cse.underdog.org.underdog_client.etc;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.api.services.youtube.YouTube;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cse.underdog.org.underdog_client.AllInOneActivity;
import cse.underdog.org.underdog_client.BottomNavigationViewBehavior;
import cse.underdog.org.underdog_client.BottomNavigationViewHelper;
import cse.underdog.org.underdog_client.R;
import cse.underdog.org.underdog_client.memo.MemoActivity;
import cse.underdog.org.underdog_client.speech.SttActivity;
import cse.underdog.org.underdog_client.speech.SttService;
import cse.underdog.org.underdog_client.timeline.TimelineActivity;

public class EtcActivity extends AppCompatActivity{
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    private Thread th;
    private Thread th2;

    private String youtubeAddress = null;
    String result;
    String search;

    @BindView(R.id.imageView5)
    ImageView searchButton;

    @BindView(R.id.imageView)
    ImageView musicButton;

    @BindView(R.id.imageView4)
    ImageView memoButton;

    @BindView(R.id.imageView2)
    ImageView weatherButton;

    @BindView(R.id.imageView3)
    ImageView scheduleButton;



    private static final String PROPERTIES_FILENAME = "youtube.properties";

    private static final long NUMBER_OF_VIDEOS_RETURNED = 1;

    private static YouTube youtube;
    private Intent sttIntent;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etc);

        ButterKnife.bind(this);
        sttIntent = new Intent(this, SttActivity.class);
        search = "";

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                th2 = new Thread(new Runnable() {
                    @Override
                    synchronized public void run() {
                        startActivityForResult(sttIntent, 200);
                        try {
                            Log.e("쓰레드안wait전","쓰레드안wait전");
                            wait();
                            Log.e("쓰레드안wait후후","쓰레드안wait후후");
                        } catch (InterruptedException e) {
                            Bundle bundle = new Bundle();
                            bundle.putString("search", search);
                            Fragment searchFragment = new SearchFragment();
                            searchFragment = new SearchFragment();
                            searchFragment.setArguments(bundle);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.etcLayout, searchFragment, searchFragment.getClass().getSimpleName()).addToBackStack(null).commit();
                        }
                    }
                });
                th2.start();
            }
        });

        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                th = new Thread(new Runnable() {
                    @Override
                    synchronized public void run() {
                        startActivityForResult(sttIntent, 100);
                        try {
                            Log.e("쓰레드안wait전","쓰레드안wait전");
                            wait();
                            Log.e("쓰레드안wait후후","쓰레드안wait후후");
                        } catch (InterruptedException e) {
                            try {
                                youtubeAddress = getAddress();
                                Bundle bundle = new Bundle();
                                System.out.println("서치에서위" + youtubeAddress);
                                bundle.putString("search", youtubeAddress);
                                System.out.println("서치에서아래" + youtubeAddress);
                                Fragment musicFragment = new MusicFragment();
                                musicFragment = new MusicFragment();
                                musicFragment.setArguments(bundle);
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.etcLayout, musicFragment, musicFragment.getClass().getSimpleName()).addToBackStack(null).commit();
                            } catch (IOException e1) {
                                e.printStackTrace();
                            } catch (JSONException e1) {
                                e.printStackTrace();
                            }
                            System.out.println("유튜브 주소" + youtubeAddress);
                        }
                    }
                });
                th.start();
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_android:
                        Intent intent1 = new Intent(EtcActivity.this, TimelineActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_books:
                        Intent intent2 = new Intent(EtcActivity.this, AllInOneActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_center_focus:
                        Intent intent3 = new Intent(EtcActivity.this, MemoActivity.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_backup:
                        break;
                }


                return false;
            }
        });

    }

    public String getAddress() throws IOException, JSONException {
        String address=null;
        System.out.println("겟어드레스 실행됨?");
        search = search.replace(" ", "+");

        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&order=viewCount&q=" + search + "&key=AIzaSyAQ5Ekb03JzWae-HV_diC6FiLtkLuf7co0";

        System.out.println("url까지실행됨?" + url);

        Document doc = Jsoup.connect(url).ignoreHttpErrors(true).ignoreContentType(true).timeout(10 * 1000).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();
        System.out.println("DOC까진실행됨?");
        String getJson = doc.text();
        System.out.println("제이슨이다"+getJson);
        JSONObject jsonObject = (JSONObject) new JSONTokener(getJson ).nextValue();

        List<String> list = new ArrayList<String>();
        JSONArray array = jsonObject.getJSONArray("items");
        address = array.getJSONObject(0).getJSONObject("id").getString("videoId");
        System.out.println("비디오아이디" + address);

        System.out.println("getAddress 안에서 주소"+address);

        return address;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Log.e("inActivityResult", "in requestCode");
            if (resultCode != RESULT_CANCELED) {
                Log.e("inActivityResult", "in resultCode");
                result = data.getStringExtra("result");
                Log.e("dataResult", result);
                if (result.contains("검색")) {
                    int idx = result.indexOf("검색");
                    System.out.println("인덱" + idx);
                    search = result.substring(0, idx);
                    System.out.println("서치" + search);
                    th.interrupt();
                } else {       }
            } else {
                Log.e("inActivityResult", "in resultCode error");
                result = data.getStringExtra("result");
                Log.e("dataResult", result);
                if (result.contains("검색")) {
                    int idx = result.indexOf("검색");
                    System.out.println("인덱" + idx);
                    search = result.substring(0, idx);
                    System.out.println("서치" + search);
                    th.interrupt();
                }
            }
        } else Log.e("inActivityResult", "in requestCode error");

        if (requestCode == 200) {
            if(resultCode != RESULT_CANCELED) {
                result = data.getStringExtra("result");
                if (result.contains("검색")) {
                    int idx = result.indexOf("검색");
                    System.out.println("인덱" + idx);
                    search = result.substring(0, idx);
                    System.out.println("서치" + search);
                    th2.interrupt();
                } else {       }
            } else {
                result = data.getStringExtra("result");
                if (result.contains("검색")) {
                    int idx = result.indexOf("검색");
                    System.out.println("인덱" + idx);
                    search = result.substring(0, idx);
                    System.out.println("서치" + search);
                    th2.interrupt();
                }
            }
        }

    }

    public void onBackPressed() {


        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            Intent intent = new Intent(this, AllInOneActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "뒤로 가기 키를 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
