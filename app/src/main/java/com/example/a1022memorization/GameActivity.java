package com.example.a1022memorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Makes the app full screen because the wifi and other icons are annoying
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);
        //Switch From Game Screen to Main Menu
        Button button = (Button) findViewById(R.id.back);
        button.setOnClickListener(v -> {

            Intent intent = new Intent(this,MainActivity.class);

            startActivity(intent);

        });
        //Receives the parsed difficulty from the main menu

        //Currently Causes the app to crash when transferring the data idk why

        //Intent mIntent = getIntent();
        //String diffStr = mIntent.getStringExtra("diffCount");
        //int diffCount = Integer.valueOf(diffStr);
        //System.out.println(diffCount);


        //Generates starting amount of buttons based on difficulty
        // Run the color button generation here and then sort it into the table
    }
}