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
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/*
Todo:
General testing

other/general:
    change px to dp

bugs:
    incorrect popup is dismissable
 */

public class MainActivity extends AppCompatActivity {
    //Global Stuff
    //Keeps track of difficulty
    int diffCount = 0;// shouldnt this be public static?

    // leaderboard variables
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> level = new ArrayList<>();
    ArrayList<Integer> sTot = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: check if extras were added (from the game), and if so pass to addScore.

        //TODO: Code for parsing in level stuff as said above

        leaderStartData(); // Fills in base values of the leaderboard


        String nameIn = "default menu";
        int levelIn = 10;
        int sTotIn = 67;

        if (savedInstanceState == null) {// no idea how this works
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                nameIn = null;
            } else {
                nameIn = extras.getString("name", "none");
                levelIn = Integer.parseInt(extras.getString("level", "-1"));
                sTotIn = Integer.parseInt(extras.getString("sTot", "-1"));
                name = extras.getStringArrayList("nameLeader");
                Log.i("name passed in", name.get(0));
                level = extras.getIntegerArrayList("levelLeader");
                sTot = extras.getIntegerArrayList("sTotLeader");
            }
        } else {
            nameIn = (String) savedInstanceState.getSerializable("name");
            levelIn = Integer.parseInt((String) savedInstanceState.getSerializable("level"));
            sTotIn = Integer.parseInt((String) savedInstanceState.getSerializable("sTot"));
        }

        //nameIn = getIntent().getExtras().getString("name", "Hugh");
        if(nameIn != null){
            Log.i("name", nameIn);
            //levelIn = 7;
            //sTotIn = (60*7)-1;
            addScore(nameIn, levelIn, sTotIn);
        }



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
            intent.putExtra("nameLeader", name);
            intent.putExtra("levelLeader", level);
            intent.putExtra("sTotLeader", sTot);

            //Starts the game
            startActivity(intent);

        });
        diffButton(null);// run this once to set up the diff button


    }

    public void leaderStartData(){
        //LeaderBoard Starting Data
        //1st
        name.add(0, "Joe");
        level.add(0,12);
        sTot.add(0,150);
        //2nd
        name.add(1, "Candice");
        level.add(1,7);
        sTot.add(1,80);
        //3rd
        name.add(2, "Ferris");
        level.add(2,5);
        sTot.add(2,70);
        //4th
        name.add(3,"Deez");
        level.add(3,3);
        sTot.add(3,65);
    }
    @SuppressLint("SetTextI18n") // Very helpful
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
        //Displays the Leaderboard data
        TextView leaderBoardText = popupView.findViewById(R.id.leaderBoard);
        leaderBoardText.setText("Leaderboard\n " +
                "\uD83E\uDD47: " + name.get(0)  +"\n" + "Level: " +level.get(0) +", " + "Time: " + timeStr(sTot.get(0)) +"\n"
                +"\n \uD83E\uDD48: " + name.get(1)  +"\n " + "Level: " + level.get(1) +", " + " Time: " + timeStr(sTot.get(1)) +"\n"
                + "\n \uD83E\uDD49: "  + name.get(2)  +"\n " + "Level: " +level.get(2) +", " + " Time: " + timeStr(sTot.get(2)) +"\n"
                + "\n \uD83C\uDF6A: "  + name.get(3)  +"\n " + "Level: " +level.get(3) +", " + " Time: " + timeStr(sTot.get(3)));
    }
    //Totally not code from the other class
    public static String timeStr(int secs){
        NumberFormat formatter = new DecimalFormat("00");
        String formatted = "00:00";

        if(secs>0) {
            int minsF = secs / 60;
            int secsF = secs - (minsF * 60);

            formatted = formatter.format(minsF) + ":" + formatter.format(secsF);
        }
        return(formatted);
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
    public void addScore (String nameIn, int levelIn, int sTotIn){
        levelIn = levelIn-1;// if you complete 2 it should be 2 not 3, this fixes that
        int spots = 4;// max number of scores to store
        int place = 4;// default put it in the last place
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
            if(levelIn > level.get(i)){// first place or whatever
                place = i;
                break;
            }
        }

        name.add(place, nameIn);
        level.add(place, levelIn);
        sTot.add(place, sTotIn);
        name.remove(spots);
        level.remove(spots);
        sTot.remove(spots);
    }

}