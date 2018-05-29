package cse.underdog.org.underdog_client.timeline;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cse.underdog.org.underdog_client.R;
import cse.underdog.org.underdog_client.TabPagerAdapter;
import cse.underdog.org.underdog_client.location_GPS.Gps;
import cse.underdog.org.underdog_client.speech.SttService;
import cse.underdog.org.underdog_client.speech.TtsService;

public class TimelineFragment extends Fragment {

    // gps, stt, tts Test code
    private String result;
    private Gps gps;
    private SttService stt;
    private TtsService tts;
    private Button sttBtn;
    private Button ttsBtn;
    private Button gpsBtn;
    private TextView tv;

    private TextView txtLat;
    private TextView txtLon;
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

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
        View view = inflater.inflate(R.layout.fragment_timeline, null);

        sttBtn = (Button) view.findViewById(R.id.stt_btn);
        ttsBtn = (Button) view.findViewById(R.id.tts_btn);
        gpsBtn = (Button) view.findViewById(R.id.gps_btn);
        txtLat = (TextView) view.findViewById(R.id.tv_latitude);
        txtLon = (TextView) view.findViewById(R.id.tv_longtitude);
        tv = (TextView) view.findViewById(R.id.tv);
        stt = new SttService();
        tts = new TtsService(getActivity());

        gpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPermission) {
                    callPermission();
                    return;
                }

                gps = new Gps(TimelineFragment.this.getActivity());

                if(gps.isGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longtitude = gps.getLongitude();

                    txtLat.setText(String.valueOf(latitude));
                    txtLon.setText(String.valueOf(longtitude));

                    Toast.makeText(getActivity().getApplicationContext(), "당신의 위치 - \n위도: " + Double.toString(latitude) + "\n경도: " + Double.toString(longtitude), Toast.LENGTH_LONG).show();
                } else {gps.showSettingsAlert();}
            }
        });

        callPermission();

        ttsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.ttsStart(tv.getText().toString());
            }
        });
        sttBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(stt.getIntent(), stt.getREQ());
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    // 전화번호 권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && this.getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && this.getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        result = stt.getResult(requestCode, resultCode, resultCode, data);
        tv.setText(result);
    }

    @Override
    public void onDestroy() {
        tts.ttsExit();
        super.onDestroy();
    }
}