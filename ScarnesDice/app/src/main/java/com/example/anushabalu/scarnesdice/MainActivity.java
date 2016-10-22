package com.example.anushabalu.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;
import android.util.Log;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private int userOverallScore =0;
    private int compOverallScore =0;
    private int userTurnScore =0;
    private int compTurnScore =0;
    private static final int WIN_SCORE = 100;
    private static Button btnRoll,btnReset,btnHold;
    private static ImageView ivDiceFace;
    private static TextView turnScore, winner;
    private static Random random = new Random();
    Handler handler = new Handler();


    private static int diceFaces[] = {
            R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivDiceFace = (ImageView) findViewById(R.id.iv_dice_face);
        btnRoll = (Button) findViewById(R.id.btn_roll);
        btnReset =(Button)findViewById(R.id.btn_Reset);
        btnHold = (Button)findViewById(R.id.btn_Hold);
        turnScore = (TextView) findViewById(R.id.score);
        winner = (TextView) findViewById(R.id.winner);
        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetDice();
            }
        });
        btnHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holdDice();
            }
        });
    }
    Runnable run = new Runnable() {
        @Override
        public void run() {
            btnRoll.setEnabled(false);
            btnHold.setEnabled(false);
            int diceValue = random.nextInt(6) + 1;
            Log.i("Roll Value for computer", String.valueOf(diceValue));
            ivDiceFace.setImageResource(diceFaces[diceValue - 1]);
               if (diceValue != 1) {
                   compTurnScore += diceValue;
                   labelUpdate();
                   handler.postDelayed(this, 500);
               } else {
                   compTurnScore = 0;
                   labelUpdate();
                   Log.i("Am exiting", String.valueOf(compTurnScore));
                   handler.removeCallbacksAndMessages(run);
                }
            compOverallScore += compTurnScore;
            compTurnScore =0;
            if(compOverallScore >= WIN_SCORE)
            {
                winner.setText(" COMPUTER WIN");
                btnRoll.setEnabled(false);
                btnHold.setEnabled(false);
                return;
            }

            btnRoll.setEnabled(true);
            btnHold.setEnabled(true);
        }
    };

    protected void rollDice()
    {
        int diceValue = random.nextInt(6) + 1;
        Log.i("Roll Value",String.valueOf(diceValue));
        ivDiceFace.setImageResource(diceFaces[diceValue-1]);
        if(diceValue != 1)
        {
            userTurnScore += diceValue;
            labelUpdate();
        }
        else
        {
            userTurnScore = 0;
            labelUpdate();
            computerTurn();
        }
    }

    protected void resetDice()
    {
        userOverallScore =0;
        compOverallScore =0;
        userTurnScore =0;
        compTurnScore =0;
        labelUpdate();
        winner.setText(" ");

    }
    protected void holdDice()
    {
        userOverallScore += userTurnScore;
        userTurnScore = 0;
        labelUpdate();
        if(userOverallScore >= WIN_SCORE)
        {
            winner.setText("YOU WIN");
            btnRoll.setEnabled(false);
            btnHold.setEnabled(false);
            return;
        }
        computerTurn();
    }
    protected void computerTurn()
    {

        handler.postDelayed(run,500);
        compTurnScore =0;
        labelUpdate();
        Log.i("After postdelayed",String.valueOf(compTurnScore));

    }
    protected void labelUpdate()
    {
        String userScore = "Your Overall Score:" +userOverallScore+ "\t Your Turn Score:" +userTurnScore+"\n";
        String compScore = "Computer Overall Score:" +compOverallScore+ "\t Computer Turn Score:" +compTurnScore;
        String label = userScore + compScore ;
        turnScore.setText(label);
    }
}
