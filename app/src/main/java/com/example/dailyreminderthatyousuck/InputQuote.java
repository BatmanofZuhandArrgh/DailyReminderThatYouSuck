package com.example.dailyreminderthatyousuck;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InputQuote extends AppCompatActivity {

    List<String> QuoteRepo = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_quote);
        QuoteRepo.add("Insult Repository:");
    }

    public void inputText(View v) {
        //Get new input from the input_blank
        TextView t = findViewById(R.id.input_text);
        String input = t.getText().toString();

        //Add input to repo
        QuoteRepo.add(input);
        String result = String.join("\n", QuoteRepo);

//        Show all submitted input
        TextView QuoteRepoShow = findViewById(R.id.InsultRepo);
        QuoteRepoShow.setText(result);
    }

    public void back_MainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}