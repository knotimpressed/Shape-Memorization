package com.example.a1022memorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Calendar;

public class GameActivity extends AppCompatActivity {

    public static int[][] guess = new int[1][1];// stupid global variable to make code simpler, current guess state
    public static boolean betterRand = true;// if true, makes actually random games

    public static String name;
    public static int level;
    public static int sTot;
    public static int mins;
    public static int secs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //guess[0][0] = -1;// just for testing
        super.onCreate(savedInstanceState);
        //Makes the app full screen because the wifi and other icons are annoying
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);
        //Switch From Game Screen to Main Menu
        Button backButton = (Button) findViewById(R.id.back);
        backButton.setOnClickListener(v -> {

            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);

        });
        //Receives the parsed difficulty from the main menu

        int diffCount = getIntent().getExtras().getInt("diffCount", 0);
        //String string2 = getIntent().getExtras().getString("STRING key","defaultValueIfNull");// basic form
        Log.i("diffCount", Integer.toString(diffCount));


        /* somewhere, we need to put this code on the end of game popup.
        sendButton.setOnClickListener(v -> {

            Intent intent = new Intent(this, MainActivity.class);

            intent.putExtra("name", name);
            //repeat for all the others

            startActivity(intent);
            //TODO: use an add extras thing to pass name, level, seconds to main activity
            //TODO: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! use SystemClock.uptimeMillis() for timing!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        });

         */


        //Generates starting amount of buttons based on difficulty TODO: actually make it do that lol
        // Run the color button generation here and then sort it into the table

        Context context = this;// context of where to make buttons

        int rows = 3;
        int columns = 3;

        int[] pattern = this.randomTable(rows, columns, context);// randomize colors

        Button submitButton = (Button) findViewById(R.id.submit);// set up submit button as "im done memorizing"
        submitButton.setOnClickListener(v -> {

            this.getUserTable(rows, columns, context);// sloppy, but makes correct button listeners, and wipes table

            submitButton.setOnClickListener(v2 -> {// check user input if its pressed again (ikik this is kinda bad)

                //for now this can chill here but it should probably be its own method or something, in like a chain for the game screens

                boolean correct = true;// check each cell in the guess with the generated random array
                for(int i = 0; i < rows; i++){
                    for(int j = 0; j < columns; j++){
                        if(pattern[i*rows + j] != guess[i][j]){
                            correct = false;
                            //Log.i("bad tile", "i: " + i + " j: " + j + " LS: " + pattern[i*rows + j] + " RS: " + guess[i][j]);//debug log
                            break;// this might not do anything but doesnt really matter
                        }
                    }
                }
                if(correct) {// TODO: some actual output goes here
                    Log.i("guess", "correct");
                }
                else{
                    Log.i("guess", "incorrect");
                }
            });
        });
    }

    public void getUserTable(int rows, int columns, Context context){
        guess = new int[rows][columns];//in theory, all zeroes on initialization
        Button[][] buttonArray = new Button[rows][columns];// initialize button array

        for (int row = 0; row < rows; row++) {// propagate and make look okay/better (in theory)
            for (int button = 0; button < columns; button++) {//loop though, setting each as per the randomized array
                Button currentButton = new Button(context);
                currentButton.setBackgroundColor(
                        currentButton.getContext().getResources().getColor(
                                R.color.color0 + 0));

                int finalRow = row;// these are semi-final so the lambda expression works
                int finalColumn = button;
                currentButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {// code to cycle the colors
                        buttonFunc(finalRow, finalColumn, currentButton);
                    }
                });
                //TableLayout.LayoutParams params =
                //        new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                //          TableLayout.LayoutParams.WRAP_CONTENT);// TODO: this code doesnt work but it would be nice if it did
                //currentButton.setLayoutParams(params);
                buttonArray[row][button] = currentButton;// write out the current button to the array
            }
        }

        this.updateTable(buttonArray, context);// update the table using the custom method
    }

    public void buttonFunc(int finalRow, int finalColumn, Button currentButton) {// function that runs when a guess button is pressed
        Log.i("button", "row: " + finalRow + " Column: " + finalColumn);// degub output

        if (guess[finalRow][finalColumn] < 2) {// loop through colours for each button, while updating global guess array
            guess[finalRow][finalColumn]++;
            currentButton.setBackgroundColor(
                    currentButton.getContext().getResources().getColor(
                            R.color.color0 + guess[finalRow][finalColumn]));
        } else {
            guess[finalRow][finalColumn] = 0;
            currentButton.setBackgroundColor(
                    currentButton.getContext().getResources().getColor(
                            R.color.color0 + guess[finalRow][finalColumn]));
        }
    }


    public int[] randomTable(int rows, int columns, Context context){// makes the array of randomly coloured buttons
        Button[][] buttonArray = new Button[rows][columns];// initialize button array
        int buttId = 101;
        int[] randArr = new int[rows*columns];

        if(betterRand) {
            int cycles = 0;
            Date currentTime = Calendar.getInstance().getTime();
            cycles = currentTime.getSeconds();// this is deprecated but hey it works lol
            Log.i("seconds for cycles", Integer.toString(cycles));
            for (int i = 0; i < cycles; i++) {// this fixes the random numbers always being the same
                ThreadLocalRandom.current().nextInt(0, 2);
            }
        }

        for(int i = 0; i < rows*columns; i++) {
            randArr[i] = ThreadLocalRandom.current().nextInt(0, 2+1);
        }

        int colorCount = 0;
        for (int row = 0; row < rows; row++) {// propagate and make look okay/better
            for (int button = 0; button < columns; button++) {
                //Button currentButton = new Button(context,null, buttonStyle);// TODO: this code doesnt work but it would be nice if it did
                Button currentButton = new Button(context);
                currentButton.setId(buttId);
                currentButton.setBackgroundColor(
                        currentButton.getContext().getResources().getColor(
                                R.color.color0 + randArr[colorCount]));
                colorCount++;
                //TableLayout.LayoutParams params =
                //        new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);// TODO: this code doesnt work but it would be nice if it did
                //currentButton.setLayoutParams(params);
                buttonArray[row][button] = currentButton;
            }
        }

        this.updateTable(buttonArray, context);// update the table
        return(randArr);
    }

    public void updateTable(Button[][] buttonArray, Context context){// update the table to a new array of buttons

        TableLayout table = findViewById(R.id.gameTable);
        table.removeAllViews();// wipe table
        for (int row = 0; row < buttonArray.length; row++) {// add each row of buttons to a row object
            TableRow currentRow = new TableRow(context);
            for (int button = 0; button < buttonArray[1].length; button++) {
                currentRow.addView(buttonArray[row][button]);
            }
            // a new row has been constructed -> add to table
            table.addView(currentRow);
        }
    }

}