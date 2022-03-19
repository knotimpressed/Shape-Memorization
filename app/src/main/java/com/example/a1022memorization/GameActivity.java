package com.example.a1022memorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import java.util.concurrent.ThreadLocalRandom;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        /*


        Randomization of the colors


         */
        Context context = this;

        Button[][] buttonArray = new Button[4][4];// initialize button array
        int buttId = 101; // TODO: Don't use hardcoded values for dimensions
        int[] randArr = new int[16];
        for(int i = 0; i < 16; i++) {
            randArr[i] = ThreadLocalRandom.current().nextInt(0, 2+1);
        }

        int colorCount = 0;
        for (int row = 0; row < 4; row++) {// propagate and make look okay/better
            for (int button = 0; button < 4; button++) {
                //Button currentButton = new Button(context,null, buttonStyle);// TODO: this code doesnt work but it would be nice if it did
                Button currentButton = new Button(context);
                currentButton.setId(buttId);
                currentButton.setBackgroundColor(
                        currentButton.getContext().getResources().getColor(
                                R.color.color0 + randArr[colorCount]));
                colorCount++;
                currentButton.setText("0");
                currentButton.setWidth(50);
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

        /*Button currentButton = new Button(context,null, buttonStyle);// test code to set a button, doesnt work
        currentButton.setText("test");
        currentButton.setWidth(50);
        TableLayout layout = (TableLayout) findViewById(R.id.gameTable);
        layout.addView(currentButton);*/


        /*


        Creation of Buttons for player input


        */

    }


    //Button[][] buttonArrayIn, TableLayout tableIn,
    public void updateTable(Button[][] buttonArray, Context context){
        // NEEED TO WIPE TABLE!!!!!!!!!

        TableLayout table = findViewById(R.id.gameTable);
        for (int row = 0; row < 4; row++) {
            TableRow currentRow = new TableRow(context);
            for (int button = 0; button < 4; button++) {
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