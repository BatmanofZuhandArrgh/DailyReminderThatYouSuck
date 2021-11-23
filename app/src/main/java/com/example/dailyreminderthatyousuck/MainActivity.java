package com.example.dailyreminderthatyousuck;

import java.lang.Thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
//import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // When intialize activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void disable(View v){
        v.setEnabled(false);
        Log.d("success", "Button disabled");
    }

    public static void wait(int ms)
    {
        //Wait ms milliseconds
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    public void launch_inputQuote(View v){
        Intent i = new Intent(this, InputQuote.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void generate_quote(View v) {
        Button button = (Button) v; //Casting it to class Button

        TextView genQuote = findViewById(R.id.QuoteOutput);
        genQuote.setText("Bitch ass motherfucker");
    }
}