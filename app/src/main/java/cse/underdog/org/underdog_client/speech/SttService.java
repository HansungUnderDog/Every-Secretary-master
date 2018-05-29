package cse.underdog.org.underdog_client.speech;

import android.content.Intent;
import android.speech.RecognizerIntent;

import java.util.ArrayList;
import java.util.Locale;


// This Class need Test //complete
public class SttService {
    private Intent i;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    public SttService() {
        i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "xx 검색해줘 라고 말해주세요.");
    }
    public Intent getIntent() {
        return i;
    }

    public int getREQ() {
        return REQ_CODE_SPEECH_INPUT;
    }

    public String getResult(int requestCode, int resultCode, int RESULT_OK, Intent data) {
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> results = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    return results.get(0);
                }
                break;
            }
        }
        return "Stt error";
    }

}

/*

// *************************
//  Use Manual in Activity
// *************************
    String result; // Create String Object to receive return Object of stt
    SttService stt = new SttService(); // Create Stt object
    startActivityForResult(stt.getIntent(), stt.getREQ()); // start stt

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        result = stt.getResult(requestCode, resultCode, RESULT_OK, data); // in 'onActivityResult' method : run this method to return value of stt
    }

 */
