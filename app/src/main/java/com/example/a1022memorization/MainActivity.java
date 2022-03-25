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
import java.util.ArrayList;

/*
Bugs List:
- Difficulty Button takes two clicks to change off of the first Easy (does not even show up in debug)
- Not really a bug but make better object names
- Seed random numbers, otherwise the first table is the same


Possible Improvements (They work now but might want to change):
- Do the better practice of changing the difficulty buttons text properly

Todo:
- make entire game lol
    -make default start table
        -add colour cycling
    -game table random generation
    -different game sizes (REMEMBER WHEN DOING ABOVE STUFF)
    -guess validation
    -time tracking
    -leaderboard implementation





 */

public class MainActivity extends AppCompatActivity {
    //Global Stuff
    //Keeps track of difficulty
    int diffCount = 0;// shouldnt this be public static?

    // leaderboard variables
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> level = new ArrayList<>();
    ArrayList<Integer> sTot = new ArrayList<>();
    ArrayList<Integer> mins = new ArrayList<>();
    ArrayList<Integer> secs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: check if extras were added (from the game), and if so pass to addScore.

        //TODO: Causes the entire thing to crash off booting
        /*
        String nameIn = getIntent().getExtras().getString("name", null);
        if(nameIn != null){
            //parse in the other stuff, then do addScore.
        }

         */
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
            //intent.putExtra("diffCount",diffCount);

            intent.putExtra("diffCount", diffCount);

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
        //Difficulty Button swapping text and value
        Button diffButton = (Button) findViewById(R.id.diffButton);
        diffButton.setOnClickListener(d ->{
            //Log.i("test", "diffpress");// this is a debug log message, if anyone wants to use it @hliwudnew
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

    // any score can be passed in, if it doesnt place it will be added then removed
    public void addScore (String nameIn, int levelIn, int sTotIn, int minIn, int secIn){
        int spots = 5;// max number of scores to store
        int place = 0;
        for(int i = 0; i < spots; i++){// could probably do this faster with .get
            // i think this should work? idk, just get the place/index of where the new score goes
            if(levelIn == level.get(i)){// there is a tie based on level, go to time
                if(sTotIn < sTot.get(i)){//total seconds is smaller, take its place
                    place = i;
                    break;
                }
                else if(sTotIn == sTot.get(i)){//total seconds is exact same, place after
                    place = i+1;
                    break;
                }
                else if(levelIn > level.get(i+1)){// seconds arent smaller, but the next level goes down one
                    place = i+1;// put in after this score
                    break;
                }
            }
        }

        name.add(place, nameIn);
        level.add(place, levelIn);
        sTot.add(place, sTotIn);
        mins.add(place, minIn);
        secs.add(place, secIn);
        name.remove(spots+1);
        level.remove(spots+1);
        sTot.remove(spots+1);
        mins.remove(spots+1);
        secs.remove(spots+1);
    }

}