package com.example.dailyreminderthatyousuck;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Random;

public class Text2Speech extends Service implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {
    public static TextToSpeech mTts;
    private String spokenText_quote;
    private String spokenText;
    public static final String filenameInternal = "QuoteRepo.txt"; //Simple text file containing all quotes input


    @Override
    public void onCreate() {
        super.onCreate();
        mTts = new TextToSpeech(this, this);
        spokenText_quote = get_random_quote();
        for (int i = 0; i < 10; i++) {
            spokenText = spokenText + ". " + spokenText_quote;
        }
//        Toast.makeText(this, spokenText, Toast.LENGTH_SHORT).show();
    }

    public String load() {
        FileInputStream fis = null;
        String result = "";
        try {
            fis = openFileInput(filenameInternal);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append('\n');
            }
            result = result + sb.toString();
//            Log.i("showing Scroll View full text", sb.toString().trim());

        } catch (FileNotFoundException e) {
            Log.e("Exception", e.toString());
        } catch (IOException e) {
            Log.e("Exception", e.toString());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    Log.e("Exception", e.toString());
                }
            }
        }
        return result.trim();
    }

    public String[] get_all_quotes(){
        //Loading quotes from repo, and return them in a list
        String text = load();

        String[] individual_texts = text.split("\n"); //Returns  [""] if empty string
        if(text.length() == 0){
            individual_texts = new String[]{} ;
        }

        return individual_texts;
    }

    public int get_random_number(int maxvalue){
        Random ran = new Random();
        int x = ran.nextInt( maxvalue + 1 ); //NextInt generate number from 0 to maxvalue - 1
        return x;
    }

    public String get_random_quote() {
        String[] individual_texts = get_all_quotes();

        if (individual_texts.length == 0) {
            return "";
        }

        int random_index = get_random_number(individual_texts.length - 1);
        String quote_generated = individual_texts[random_index];

        if (quote_generated.charAt(quote_generated.length() - 1) == '.') {
            return quote_generated.substring(0,quote_generated.length() - 1);
        }

        return quote_generated;
    }

    @Override
    public void onInit(int status) {
        Toast.makeText(this, "init", Toast.LENGTH_SHORT).show();
        if (status == TextToSpeech.SUCCESS) {
            int result = mTts.setLanguage(Locale.US);
            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                mTts.speak(spokenText, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    @Override
    public void onUtteranceCompleted(String uttId) {
        stopSelf();
    }

    @Override
    public void onDestroy() {
        if (mTts != null) {
            mTts.stop();
            mTts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
//        Toast.makeText(this, "obBind", Toast.LENGTH_SHORT).show();
        return null;
    }

}
