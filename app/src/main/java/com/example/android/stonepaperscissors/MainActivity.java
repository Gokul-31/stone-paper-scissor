package com.example.android.stonepaperscissors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Declaration:
    private EditText name1;
    private EditText name2;
    private EditText roundView;
    private Button goNext;
    private String p1Name;
    private String p2Name;
    private int rounds =5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting Views:
        name1=findViewById(R.id.player1_name);
        name2=findViewById(R.id.player2_name);
        roundView =findViewById(R.id.round_num);
        goNext=findViewById(R.id.Go);

        goNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //using the Views:
                p1Name=name1.getText().toString();
                p2Name=name2.getText().toString();
                rounds =Integer.parseInt(roundView.getText().toString());

                //border case check
                if(rounds <1){
                    Toast.makeText(getApplicationContext(),"Enter number between 3 and 15",Toast.LENGTH_SHORT).show();
                }
                else {
                    //pass through intent
                    Intent gameActivity = new Intent(getApplicationContext(),game.class);
                    gameActivity.putExtra("p1", p1Name);
                    gameActivity.putExtra("p2", p2Name);
                    gameActivity.putExtra("Size", rounds);
                    startActivity(gameActivity);
                }
            }
        });

    }
}
