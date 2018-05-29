package cse.underdog.org.underdog_client.etc;

import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cse.underdog.org.underdog_client.R;
import cse.underdog.org.underdog_client.speech.SttService;

public class MusicFragment extends Fragment {
    String result;
    SttService stt;
    String search;


    @BindView(R.id.musicWeb)
    WebView musicWebView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // stt = new SttService();
        // startActivityForResult(stt.getIntent(), stt.getREQ());

        getSearch();

        System.out.println("뮤직프래그먼트위"+search);
        View view=inflater.inflate(R.layout.fragment_music, container, false);
        ButterKnife.bind(this, view);

        Bundle extara = getArguments();
        search = extara.getString("search");

        musicWebView.loadUrl("https://m.youtube.com/watch?v=" + search);

        // Enable Javascript
        WebSettings webSettings = musicWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        musicWebView.setWebViewClient(new WebViewClient());
        System.out.println("아래에서 호출됨");



        return view;
    }


    public void getSearch(){
        search = getArguments().getString("search");
        System.out.println("메소드에선됨?" + search);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        result = stt.getResult(requestCode, resultCode, resultCode, data);
        //tv.setText(result);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
