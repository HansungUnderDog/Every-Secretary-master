package cse.underdog.org.underdog_client.speech;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TtsService {
    private TextToSpeech tts;

    public TtsService(Context context) {
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.KOREAN);
            }
        });
    }

    public void ttsStart(String value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(value, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak(value, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void ttsExit() {
        tts.stop();
        tts.shutdown();
    }
}

/*
**************
* Must do it *
**************
public void onDestroy()() {
    tts.ttsExit();
    super.onDestroy();
}
 */
