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
import java.util.concurrent.ThreadLocalRandom;

public class GameActivity extends AppCompatActivity {

    public static int[][] guess = new int[1][1];// stupid global variable to make code simpler

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


        //Generates starting amount of buttons based on difficulty
        // Run the color button generation here and then sort it into the table

        Context context = this;

        int rows = 3;
        int columns = 3;

        int[] pattern = this.randomTable(rows, columns, context);// randomize colors

        Button submitButton = (Button) findViewById(R.id.submit);// wipe the table to blank
        submitButton.setOnClickListener(v -> {
            //guess = new int[rows][columns];
            int[][] userIn = this.getUserTable(rows, columns, context);// sloppy, but makes correct button listeners
            //Log.i("first wipe", Integer.toString(guess.length));
        });

        submitButton.setOnClickListener(v -> {// check user input

            //for now this can chill here but it should probably be its own method or something, in like a chain for the game screens


            //Log.i("TL", Integer.toString(guess[0][0]));
            /*if(guessG[0][0] != 0) {
                Log.i("check", "1");
            }*/

            int[][] userIn = this.getUserTable(rows, columns, context);// sloppy, but makes correct button listeners

            boolean correct = true;
            for(int i = 0; i < rows; i++){
                for(int j = 0; j < columns; j++){
                    if(pattern[i*rows + j] != userIn[i][j]){
                        correct = false;
                        Log.i("bad tile", "i: " + i + " j: " + j + " LS: " + pattern[i*rows + j] + " RS: " + userIn[i][j]);
                        break;
                    }
                }
            }
            if(correct) {
                Log.i("guess", "correct");
            }
            else{
                Log.i("guess", "incorrect");
            }

            //int[][] userIn = this.getUserTable(rows, columns, context);// get user input

        });


    }

    public void getUserTable(int rows, int columns, Context context){
        guess = new int[rows][columns];//in theory, all zeroes
        Button[][] buttonArray = new Button[rows][columns];// initialize button array

        for (int row = 0; row < rows; row++) {// propagate and make look okay/better
            for (int button = 0; button < columns; button++) {
                Button currentButton = new Button(context);
                currentButton.setBackgroundColor(
                        currentButton.getContext().getResources().getColor(
                                R.color.color0 + 0));

                int finalRow = row;// these are semi-final so the lambda expression works, just for debug
                int finalColumn = button;
                currentButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {// code to cycle the colors
                        buttonFunc(finalRow, finalColumn);
                    }
                });
                //TableLayout.LayoutParams params =
                //        new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);// TODO: this code doesnt work but it would be nice if it did
                //currentButton.setLayoutParams(params);
                buttonArray[row][button] = currentButton;
            }
        }

        this.updateTable(buttonArray, context);// update the table
        Log.i("return", Integer.toString(guess.length));
        return(guess);
    }

    public void buttonFunc(int finalRow, int finalColumn) {
        Log.i("button", "row: " + finalRow + " Column: " + finalColumn);

        /*if (guess[finalRow][finalColumn] < 2) {
            guess[finalRow][finalColumn]++;
            currentButton.setBackgroundColor(
                    currentButton.getContext().getResources().getColor(
                            R.color.color0 + guess[finalRow][finalColumn]));
        } else {
            guess[finalRow][finalColumn] = 0;
            currentButton.setBackgroundColor(
                    currentButton.getContext().getResources().getColor(
                            R.color.color0 + guess[finalRow][finalColumn]));
        }*/
        GameActivity.guessG[0][0]++;
    }


    public int[] randomTable(int rows, int columns, Context context){
        Button[][] buttonArray = new Button[rows][columns];// initialize button array
        int buttId = 101;
        int[] randArr = new int[rows*columns];
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
                //currentButton.setId(buttId);
                // you could initialize them here
                //currentButton.setOnClickListener(v -> {Log.i("button", "button");});
                //TableLayout.LayoutParams params =
                //        new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);// TODO: this code doesnt work but it would be nice if it did
                //currentButton.setLayoutParams(params);
                buttonArray[row][button] = currentButton;
            }
        }

        this.updateTable(buttonArray, context);// update the table
        return(randArr);

        /*Button currentButton = new Button(context,null, buttonStyle);// test code to set a button, doesnt work
        currentButton.setText("test");
        currentButton.setWidth(50);
        TableLayout layout = (TableLayout) findViewById(R.id.gameTable);
        layout.addView(currentButton);*/
    }

    //Button[][] buttonArrayIn, TableLayout tableIn,
    public void updateTable(Button[][] buttonArray, Context context){
        // NEEED TO WIPE TABLE!!!!!!!!!

        TableLayout table = findViewById(R.id.gameTable);
        table.removeAllViews();
        for (int row = 0; row < buttonArray.length; row++) {
            TableRow currentRow = new TableRow(context);
            for (int button = 0; button < buttonArray[1].length; button++) {
                //Button currentButton = new Button(context,null, R.style.TableStyle);
                /*Button currentButton = new Button(context);
                currentButton.setText("test2");
                currentButton.setWidth(50);
                // you could initialize them here
                currentButton.setOnClickListener(v -> {Log.i("button", "button");});
                buttonArray[row][button] = currentButton;*/
                // and you have to add them to the TableRow
                currentRow.addView(buttonArray[row][button]);
            }
            // a new row has been constructed -> add to table
            table.addView(currentRow);
        }
    }

}