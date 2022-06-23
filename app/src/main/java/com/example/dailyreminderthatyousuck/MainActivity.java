package com.example.dailyreminderthatyousuck;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Thread;
import java.util.Locale;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;


public class MainActivity extends AppCompatActivity {
    private static final String filenameInternal = "QuoteRepo.txt"; //Simple text file containing all quotes input
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // When initialize activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Text to speech object
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

    }

    public void disable(View v){
        v.setEnabled(false);
        Log.d("success", "Button disabled");
    }

    public void launch_inputQuote(View v){
        //Replace transitioning for inputQuote
        Intent i = new Intent(this, InputQuote.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    //TODO Refactor and remove
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

    //TODO Refactor and remove
    public String[] get_all_quotes(){
        //Loading quotes from repo, and return them in a list
        String text = load();

        String[] individual_texts = text.split("\n"); //Returns  [""] if empty string
        if(text.length() == 0){
            individual_texts = new String[]{} ;
        }

//        Log.i("showing length Scroll View", String.valueOf(individual_texts.length));
//        Log.i("showing Scroll View", TextUtils.join(",", individual_texts));

        return individual_texts;
    }

    public int get_random_number(int maxvalue){
        Random ran = new Random();
        int x = ran.nextInt( maxvalue + 1 ); //NextInt generate number from 0 to maxvalue - 1
        return x;
    }

    public int generate_quote(View v) {
        //Get button (GENERATE) and set text if pressed
        Button button = (Button) v; //Casting it to class Button
        TextView genQuote = findViewById(R.id.QuoteOutput);

        String[] individual_texts = get_all_quotes();
//        Log.i("showing Scroll View", TextUtils.join(",", individual_texts));

        if (individual_texts.length == 0) {
            return 0;
        }

        int random_index = get_random_number(individual_texts.length - 1);
//        Toast.makeText(this, "Gen random number " + String.valueOf(random_index), Toast.LENGTH_SHORT).show();

        String quote_generated = individual_texts[random_index];

        //Show text
        genQuote.setText(quote_generated);
        //Speak
        textToSpeech.speak(quote_generated,TextToSpeech.QUEUE_FLUSH,null);
        return 0;
    }

}