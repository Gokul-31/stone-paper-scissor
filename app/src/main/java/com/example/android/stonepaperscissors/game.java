package com.example.android.stonepaperscissors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class game extends AppCompatActivity {

    private String[] Pname=new String[2];
    private int rounds;
    private ViewGroup picLayout;
    private Button start;
    private Button exitBtn;
    private TextView turnsView;
    private ConstraintLayout allView;
    private TextView turnTextView;
    private int pturn=0;
    private int round=0;
    private player[] players= new player[2];
    private ImageView[] images= new ImageView[3];
    private imageListener imgListener =new imageListener();
    private int[] colors=new int[2];
    private TextView roundView;
    private ConstraintLayout instructView;
    private LinearLayout layoutView;
    private TextView[] scoresView=new TextView[2];
    private int[] scores=new int[2];
    private TextView selectTextView;
    private ImageView stone;
    private Bitmap bm1;
    private Bitmap scaledBm1;
    private LinearLayout stoneLayout;
    private int bmWidth;
    private int bmHeight;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final Intent gameActivity = getIntent();
        Pname[0] = gameActivity.getStringExtra("p1");
        Pname[1] = gameActivity.getStringExtra("p2");
        rounds = gameActivity.getIntExtra("Size", 5);

        players[0]=new player();
        players[1]=new player();
        colors[0]= getResources().getColor(R.color.player1);
        colors[1]=getResources().getColor(R.color.player2);
        scores[0]=0;
        scores[1]=0;

        stoneLayout=findViewById(R.id.stoneLayout);
        stone=findViewById(R.id.stone);
        allView = findViewById(R.id.root);
        layoutView=findViewById(R.id.Layout);
        turnTextView = findViewById(R.id.turnText);
        picLayout = findViewById(R.id.picLayout);
        start = findViewById(R.id.start);
        turnsView = findViewById(R.id.turns);
        images[0]=findViewById(R.id.stone);
        images[1]=findViewById(R.id.paper);
        images[2]=findViewById(R.id.scissors);
        roundView=findViewById(R.id.rounds);
        instructView=findViewById(R.id.instruct);
        scoresView[0]=findViewById(R.id.score1);
        scoresView[1]=findViewById(R.id.score2);
        exitBtn=findViewById(R.id.exit);
        selectTextView=findViewById(R.id.selectText);

        //ready things for player 1
        round=1;
        updateLayouts();

        BitmapDrawable drawing =(BitmapDrawable) stone.getDrawable();
        bm1=drawing.getBitmap();
        scaledBm1= scaleDown(bm1,(float)1000,true);
        bmHeight=scaledBm1.getHeight();
        bmWidth=scaledBm1.getWidth();

        BitmapDrawable result1= new BitmapDrawable(scaledBm1);
        stone.setImageDrawable(result1);

        LinearLayout.LayoutParams params=(LinearLayout.LayoutParams) stone.getLayoutParams();
        params.width=bmWidth;
        params.height=bmHeight;
        stone.setLayoutParams(params);

        //set on click listener
        for(int i=0;i<3;i++)
            images[i].setOnClickListener(imgListener);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public class imageListener implements View.OnClickListener {

        @Override
        public void onClick(View v){
            if(v.getId()==R.id.stone){
                players[pturn].setChoice(1);
            }
            else if(v.getId()==R.id.paper){
                players[pturn].setChoice(2);
            }
            else{
                players[pturn].setChoice(3);
            }
            changePlayer();
            if(pturn==0){
                round++;
                checkWin();
            }
        }
    }

    private void checkWin() {
        if(players[0].getChoice()==players[1].getChoice())
        {
            selectTextView.setText("LastRound: Tie");
        }
        else if(players[0].getChoice()==players[1].getChoice()+1){
            selectTextView.setText("LastRound: "+Pname[0]);
            players[0].incrementWin();
            scores[0]++;
        }
        else if(players[1].getChoice()==players[0].getChoice()+1){
            selectTextView.setText("LastRound: "+Pname[1]);
            players[1].incrementWin();
            scores[1]++;
        }
        else if(players[1].getChoice()==1&&players[0].getChoice()==3) {
            selectTextView.setText("LastRound: "+Pname[1]);
            players[1].incrementWin();
            scores[1]++;
        }
        else
        {
            selectTextView.setText("LastRound: "+Pname[0]);
            players[0].incrementWin();
            scores[0]++;
        }
        updateLayouts();
        checkEnd();
    }

    private void checkEnd() {
        if(round==rounds+1){
            Intent intent=new Intent(getApplicationContext(),result.class);
            if(players[0].getWinsRound()>players[1].getWinsRound()){
                intent.putExtra("winner",Pname[0]);
            }
            else if(players[1].getWinsRound()>players[0].getWinsRound()){
                intent.putExtra("winner",Pname[1]);
            }
            else
                intent.putExtra("winner","tie");
            startActivity(intent);
        }
    }

    private void changePlayer() {
        if(pturn==0){
            pturn=1;
        }
        else
            pturn=0;
        updateLayouts();
    }

//to be done
    private void updateLayouts() {
            turnsView.setText(Pname[pturn]);
            roundView.setText(Integer.toString(round));
            //set colors for everything
            instructView.setBackgroundColor(colors[pturn]);
            allView.setBackgroundColor(colors[pturn]);
            layoutView.setBackgroundColor(colors[pturn]);
            //set scores
            for (int i=0;i<2;i++){
                scoresView[i].setText(Pname[i]+"\'s "+"Score: "+Integer.toString(scores[i]));
            }

    }
    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }
}
