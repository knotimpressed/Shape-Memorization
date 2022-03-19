package com.example.a1022memorization;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.sql.SQLOutput;

/*
Bugs List:
- Difficulty Button takes two clicks to change off of the first Easy (does not even show up in debug)


Possible Improvements (They work now but might want to change):
- Do the better practice of changing the difficulty buttons text properly






 */

public class MainActivity extends AppCompatActivity {
    //Global Stuff
    //Keeps track of difficulty
    int diffCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Makes the app full screen because the wifi and other icons are annoying
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        //Switch From Main Menu to Game Screen
        Button button = (Button) findViewById(R.id.buttonStart);
        button.setOnClickListener(v -> {

            Intent intent = new Intent(this,GameActivity.class);

            //Parses in the difficulty to the game
            intent.putExtra("diffCount",diffCount);
            //Starts the game
            startActivity(intent);

        });
        diffButton(null);// run this once to set up the diff button
    }

    public void leaderPop(View view) {// makes leaderboard popup
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.leader_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public void helpPop(View view) {// makes help popup
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.help_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void diffButton(View view){
        //Difficult Button swapping text and value
        Button diffButton = (Button) findViewById(R.id.diffButton);
        diffButton.setOnClickListener(d ->{
            Log.i("test", "diffpress");
            //Moves to Next Difficulty
            diffCount++;
            //Resets back to easy
            if(diffCount > 2){
                diffCount = 0;
            }
            //Easy
            if(diffCount == 0){
                diffButton.setText("Diff: Easy");
            }
            //Medium
            else if(diffCount == 1){
                diffButton.setText("Diff: Medium");
            }
            //Hard
            else{
                diffButton.setText("Diff: Hard");
            }

        });
    }
}