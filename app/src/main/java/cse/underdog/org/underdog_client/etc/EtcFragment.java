package cse.underdog.org.underdog_client.etc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cse.underdog.org.underdog_client.R;
import cse.underdog.org.underdog_client.speech.SttService;
/*
public class EtcFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(), EtcActivity.class);
        startActivity(intent);
    }
}*/

public class EtcFragment extends Fragment {
    String result;
    SttService stt;
    String search;

    @BindView(R.id.searchBtn)
    Button searchButton;

    @BindView(R.id.musicBtn)
    Button musicButton;

    @BindView(R.id.weatherBtn)
    Button weatherButton;

    @BindView(R.id.goBtn)
    Button goButton;

    @BindView(R.id.tv)
    TextView tv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_etc, null);
        ButterKnife.bind(this, view);

        stt = new SttService();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(stt.getIntent(), stt.getREQ());
            }
        });

        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(stt.getIntent(), stt.getREQ());
            }
        });

        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(stt.getIntent(), stt.getREQ());
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("search", search);
                Fragment searchFragment = new SearchFragment();
                searchFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.etcLayout, searchFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        result = stt.getResult(requestCode, resultCode, resultCode, data);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
