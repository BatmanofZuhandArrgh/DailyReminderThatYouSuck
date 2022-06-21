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

    private static final String filenameInternal = "QuoteRepo.txt"; //Simple text file containing all quotes input
    EditText mEditText; //Edit text blank
    ScrollView scrollView; // Scroll View
    LinearLayout scrollViewLayOut; // Content inside Scroll view

//    public static final int MODE_PRIVATE = 0x0000;
//    public static final int MODE_APPEND = 0x8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_quote);

        // Setting view as input_text id
        mEditText = findViewById(R.id.input_text); // Attach Edit-text blank to layout

        // Displaying result repository
        scrollView = findViewById(R.id.scrollView); //Attach scrollView to layout
        scrollViewLayOut = scrollView.findViewById(R.id.inside_scrollview);
        show_scrollview(); //Show ScrollView on creation
    }

    private String[] get_all_quotes(){
        //Loading quotes from repo, and return them in a list
        String text = load();

        String[] individual_texts = text.split("\n"); //Returns  [""] if empty string
        if(text.length() == 0){
            individual_texts = new String[]{} ;
        }

        Log.i("showing length Scroll View", String.valueOf(individual_texts.length));
        Log.i("showing Scroll View", TextUtils.join(",", individual_texts));

        return individual_texts;
    }

    public void show_scrollview(){
        String[] individual_texts = get_all_quotes();

        // Adding buttons on Creation with all quotes saved in  filenameInternal
        for (int i = 0; i < individual_texts.length; i = i + 1) {
            add_button(individual_texts[i], scrollView, i);
        }
    }

    public void save(String text, int mode) {
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(filenameInternal, mode);
            fos.write(text.getBytes());
            mEditText.getText().clear();

            Toast.makeText(this, "save to " + getFilesDir() + "/" + filenameInternal, Toast.LENGTH_SHORT).show();
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
            result = result + sb.toString().trim();
            Log.i("showing Scroll View full text", sb.toString().trim());

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
        result = result.trim();
        return result;
    }

    public void inputText(View view) {
        //Get new input from the input_blank
        String input = mEditText.getText().toString(); //Text currently input by user

        if (!input.isEmpty()) {
            String text = load(); //Text currently in repo
            if (text.length() != 0) {
                input = '\n' + input;
            }

            save(input, MODE_APPEND);

            String[] individual_texts = get_all_quotes();
            String latest_text = individual_texts[individual_texts.length - 1];
            add_button(latest_text, scrollView, individual_texts.length-1);
        }

    }

    public String[] remove_array(String[] array, int id){
        String[] copy = new String[array.length - 1];

        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != id) {
                copy[j] = array[i];
                j++;
            }
        }
        return copy;
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

    private void remove_button(int id){
        View command = scrollViewLayOut.findViewById(id);
        Log.i("Removing"+String.valueOf(id), String.valueOf(id));
        scrollViewLayOut.removeView(command);
    }

    private void remove_all_buttons(){
        String[] individual_texts = get_all_quotes();
//        Log.i("Removing all buttons");

//        String str = "n";
        // Adding buttons on Creation with all quotes saved in  filenameInternal
        for (int i = individual_texts.length; i >= 0; i = i - 1) {
            remove_button(i);
//            str = String.valueOf(i);
        };
//        String str = String.valueOf(i);
//        Log.i("last index", str);
    }

    public void remove_button_n_quote(int id){
        //Remove both button from the layout and quote from the repo

        //Remove all buttons
        remove_all_buttons();
//        wait(100);

        //Print with Toast
        String s=String.valueOf(id);
        Toast.makeText(this, "Remove button " + s, Toast.LENGTH_SHORT).show();

        String[] individual_texts = get_all_quotes();

        String[] copy = remove_array(individual_texts, id);

        String new_repo = TextUtils.join("\n", copy);
        save(new_repo, MODE_PRIVATE);

        // Show and add all buttons again
        show_scrollview();
    }


    public void add_button(String str, ScrollView scrollView, int id){
        Button newButton = new Button(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        newButton.setLayoutParams(lp);
        newButton.setTextColor( Color.YELLOW );
        newButton.setGravity( Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL );
        newButton.setId(id);
        newButton.setText( str + ' ' + id); //FIXIT remove id from button

        String s=String.valueOf(id);
        Toast.makeText(this, "Adding button " + s, Toast.LENGTH_SHORT).show();

        // Add button element to the LinearLayout element
        scrollViewLayOut.addView(newButton);

        // Register the onClick listener with the implementation above
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove_button_n_quote(id);
            }
        }
        );
        get_all_quotes();
    }



    public void back_MainActivity(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//        overridePendingTransition(R.anim.exit_right, R.anim.enter_right);
    }
}