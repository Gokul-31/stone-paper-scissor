package com.example.android.stonepaperscissors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class result extends AppCompatActivity {

    private TextView resultView;
    private String winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultView=findViewById(R.id.result);
        Intent intent = getIntent();
        winner=intent.getStringExtra("winner");
        if(winner.equals("tie")){
            resultView.setText(R.string.tie);
        }
        else
            resultView.setText(winner+ " wins");

    }
}
