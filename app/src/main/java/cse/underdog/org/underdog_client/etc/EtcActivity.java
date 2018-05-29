package cse.underdog.org.underdog_client.etc;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import cse.underdog.org.underdog_client.BottomNavigationViewHelper;
import cse.underdog.org.underdog_client.R;
import cse.underdog.org.underdog_client.memo.MemoActivity;
import cse.underdog.org.underdog_client.speech.SttActivity;
import cse.underdog.org.underdog_client.speech.SttService;
import cse.underdog.org.underdog_client.timeline.TimelineActivity;

public class EtcActivity extends AppCompatActivity{
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    private String youtubeAddress = null;
    String result;
    SttService stt;
    String search;

    @BindView(R.id.searchBtn)
    Button searchButton;

    @BindView(R.id.musicBtn)
    Button musicButton;

    @BindView(R.id.goBtn1)
    Button goButton1;

    @BindView(R.id.goBtn2)
    Button goButton2;



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

        stt = new SttService();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(sttIntent, 100);
            }
        });

        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(stt.getIntent(), stt.getREQ());
            }
        });


        goButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //View search = getLayoutInflater().inflate(R.layout.fragment_etc, null);


                Bundle bundle = new Bundle();
                bundle.putString("search", search);
                Fragment searchFragment = new SearchFragment();
                searchFragment = new SearchFragment();
                searchFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.etcLayout, searchFragment, searchFragment.getClass().getSimpleName()).addToBackStack(null).commit();
            }
        });

        goButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //View search = getLayoutInflater().inflate(R.layout.fragment_etc, null);

                new Thread(){
                    public void run(){
                        System.out.println("쓰레드안에서 유튜브위");
                        try {
                            System.out.println("쓰레드 안에서 유튜브 어드레으 실행됨?");
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
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("쓰레드안에서 유튜브아래");
                        System.out.println("유튜브 주소" + youtubeAddress);
                    }
                }.start();
                System.out.println("쓰레드 아래에서");



            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
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

        /*Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                .get();*/

        Document doc = Jsoup.connect(url).ignoreHttpErrors(true).ignoreContentType(true).timeout(10 * 1000).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();
        System.out.println("DOC까진실행됨?");
        String getJson = doc.text();
        System.out.println("제이슨이다"+getJson);
        JSONObject jsonObject = (JSONObject) new JSONTokener(getJson ).nextValue();
        /*System.out.println("제이슨 오브젝트다" + jsonObject);
        System.out.println("제이슨 오브젝트 아래에서는 찎힘?" + jsonObject.getJSONObject("items").getJSONObject("id").getString("videoId"));
        address=jsonObject.getJSONObject("items").getJSONObject("id").getString("videoId");*/

        List<String> list = new ArrayList<String>();
        JSONArray array = jsonObject.getJSONArray("items");
        /*for(int i = 0 ; i < array.length() ; i++){
            list.add(array.getJSONObject(i).getString("contentDetails"));
            address = array.getJSONObject(i).getJSONObject("contentDetails").getJSONObject("id").getString("videoId");
            System.out.println("비디오아이디" + i + " " + address);
        }*/
        address = array.getJSONObject(0).getJSONObject("id").getString("videoId");
        System.out.println("비디오아이디" + address);

        System.out.println("getAddress 안에서 주소"+address);

        return address;
    }

    /*private String getSHA1(String packageName){
        try {
            Signature[] signatures = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures;
            for (Signature signature: signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA-1");
                md.update(signature.toByteArray());
                return BaseEncoding.base16().encode(md.digest());
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        result = SttActivity.RESULT;
        //int count=0;
        //String search="";
        if(result.contains("검색")){
            /*String tmp[] = result.split("");
            for(int i=0; i<tmp.length; i++){
                search+=tmp[i];
                if(tmp[i].equals("검색")||tmp[i].equals("검색해")){
                    System.out.println("브렉문"+search);
                    break;
                }
                System.out.println("포문"+search);
            }
            System.out.println("이프"+search);*/
            int idx = result.indexOf("검색");
            System.out.println("인덱"+idx);
            search = result.substring(0, idx);
            System.out.println("서치" + search);
        }else{
            //System.out.println("엘스"+search);
        }
    }
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
