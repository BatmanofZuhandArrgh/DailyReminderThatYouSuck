package com.example.dailyreminderthatyousuck;

import java.lang.Thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String[] Quotes_input = {"Great","Good","NotsoGood"};
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    public void generate_quote(View v){
        Button button = (Button) v; //Casting it to class Button
        wait(1000);

        button.setText("Generating...");
        wait(1000);

        TextView genQuote = findViewById(R.id.QuoteOutput);
        genQuote.setText("Bitch ass motherfucker");

        wait(1000);
        button.setText("Generate2");

    }
}