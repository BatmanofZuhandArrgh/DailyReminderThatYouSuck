package com.example.dailyreminderthatyousuck;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileOutputStream;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class InputQuote extends AppCompatActivity {

    private static final String filenameInternal = "QuoteRepo.txt";
    EditText mEditText;
    ScrollView scrollView;
    LinearLayout scrollViewLayOut;

//    public static final int MODE_PRIVATE = 0x0000;
//    public static final int MODE_APPEND = 0x8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_quote);

        // Setting view as input_text id
        mEditText = findViewById(R.id.input_text);

        // Displaying result repository
        scrollView = findViewById(R.id.scrollView);
        scrollViewLayOut = scrollView.findViewById(R.id.inside_scrollview);
        show_scrollview();
    }

    public void show_scrollview(){
        String text = load();
        String[] individual_texts = text.split("\n");
        Log.i("batman_test_tag", "This is my message");

        for (int i = 0; i < individual_texts.length; i = i + 1) {
            add_button(individual_texts[i], scrollView, i);
        }
    }

    public void save(String text, int mode) {
        FileOutputStream fos = null;
        text =  text + '\n';

        try {
            fos = openFileOutput(filenameInternal, mode);
            fos.write(text.getBytes());
            mEditText.getText().clear();

//            Toast.makeText(this, "save to " + getFilesDir() + "/" + filenameInternal, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Log.e("Exception", e.toString());
        } catch (IOException e) {
            Log.e("Exception", e.toString());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    Log.e("Exception", e.toString());
                }
            }
        }
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
        return result;
    }



    public void inputText(View view) {
        //Get new input from the input_blank
        String input = mEditText.getText().toString();
        if (!input.isEmpty()) {
            save(input, MODE_APPEND);

            String text = load();
            String[] individual_texts = text.split("\n");

            String latest_text = individual_texts[individual_texts.length - 1];
            add_button(latest_text, scrollView, individual_texts.length);
        }
    }

    public String[] remove_array(String[] array, int id){
        String[] copy = new String[array.length - 1];

        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != id) {
                copy[j++] = array[i];
            }
        }
        return copy;
    }

    public void remove_button(int id, String str){
        View command = scrollViewLayOut.findViewById(id);
        Log.i("Removing"+String.valueOf(id), String.valueOf(id));
        scrollViewLayOut.removeView(command);
        String s=String.valueOf(id);
        Toast.makeText(this, "Remove button " + s, Toast.LENGTH_SHORT).show();

        String text = load();
        String[] individual_texts = text.split("\n");

        String[] copy = remove_array(individual_texts, id);

        String new_repo = TextUtils.join("\n", copy);
        save(new_repo, MODE_PRIVATE);
    }

    public void add_button(String str, ScrollView scrollView, int id){
        if (!str.isEmpty()) {
            Button newButton = new Button(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            newButton.setLayoutParams(lp);
            newButton.setTextColor( Color.YELLOW );
            newButton.setGravity( Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL );
            newButton.setText( str );
            newButton.setId(id);

            String s=String.valueOf(id);
            Toast.makeText(this, "Adding button " + s, Toast.LENGTH_SHORT).show();

            // Add button element to the LinearLayout element
            scrollViewLayOut.addView(newButton);

            // Register the onClick listener with the implementation above
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove_button(id, str);
                }
            }
            );
        }
    }

    public void back_MainActivity(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//        overridePendingTransition(R.anim.exit_right, R.anim.enter_right);
    }
}