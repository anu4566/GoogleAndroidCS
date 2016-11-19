package com.example.anushabalu.rockpaperscissor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String userChoice, compChoice;
    private int compScore=0,userScore=0;
    private static ImageButton rock,paper,scissors;
    private static Random random = new Random();
    private static TextView userText,computerText,turnResult,finResult,computerScore,usersScore ;
    private static Button resetBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userText=(TextView)findViewById(R.id.UserChoice);
        computerText=(TextView)findViewById(R.id.ComputerChoice);
        turnResult =(TextView)findViewById(R.id.TrnResult);
        finResult=(TextView)findViewById(R.id.FinalWinner);
        computerScore =(TextView)findViewById(R.id.CompScore);
        usersScore =(TextView)findViewById(R.id.UserScore);
        rock=(ImageButton) findViewById(R.id.RockBtn);
        paper= (ImageButton)findViewById(R.id.PaperBtn);
        scissors =(ImageButton)findViewById(R.id.ScissorBtn);
        resetBtn =(Button)findViewById(R.id.rstBtn);

        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userChoice ="Rock";
                userText.setText("Your Choice:\n"+userChoice);
                compTurn();
                callWin();
            }
        });
        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userChoice ="Paper";
                userText.setText("Your Choice\n"+userChoice);
                compTurn();
                callWin();
            }
        });

        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userChoice ="Scissors";
                userText.setText("Your Choice\n"+userChoice);
                compTurn();
                callWin();
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userScore =0;
                compScore =0;
                userChoice="";
                compChoice="";
                finResult.setText("");
                usersScore.setText("Your Score:"+userScore);
                computerScore.setText("Computer Score:"+compScore);
                userText.setText("Your Choice"+userChoice);
                computerText.setText("Computer Choice"+compChoice);
                turnResult.setText("");

            }
        });

    }
    public void callWin()

    {
        if(userScore == 10)
        {
            finResult.setText("You won the game!");
        }
        else if(compScore == 10)
        {
            finResult.setText("Computer Won the game!");
        }

        else {
            if (userChoice.equalsIgnoreCase(compChoice)) {
                turnResult.setText("Its a tie!");
            } else if (userChoice.equalsIgnoreCase("rock")) {
                if (compChoice.equalsIgnoreCase("Scissors")) {
                    turnResult.setText("User wins! ");
                    ++userScore;
                    usersScore.setText("Your Score:" + userScore);
                } else if (compChoice.equalsIgnoreCase("paper")) {
                    turnResult.setText("Computer Wins!");
                    ++compScore;
                    computerScore.setText("Computer Score:" + compScore);
                }

            } else if (userChoice.equalsIgnoreCase("paper")) {
                if (compChoice.equalsIgnoreCase("Scissors")) {
                    turnResult.setText("Computer wins!");
                    ++compScore;
                    computerScore.setText("Computer Score:" + compScore);
                } else if (compChoice.equalsIgnoreCase("rock")) {
                    turnResult.setText("User Wins!");
                    ++userScore;
                    usersScore.setText("User Score:" + userScore);
                }
            } else if (userChoice.equalsIgnoreCase("Scissors")) {
                if (compChoice.equalsIgnoreCase("Paper")) {
                    turnResult.setText("User wins!");
                    ++userScore;
                    usersScore.setText("User Score:" + userScore);
                } else if (compChoice.equalsIgnoreCase("rock")) {
                    turnResult.setText("Compuer Wins!");
                    ++compScore;
                    computerScore.setText("Computer Score:" + compScore);
                }
            }

        }
    }

    public void compTurn()
    {
        ArrayList<String> compSelection = new ArrayList<String>();
        compSelection.add("Rock");
        compSelection.add("Paper");
        compSelection.add("Scissors");
        int index = random.nextInt(compSelection.size());
        compChoice = compSelection.get(index);
        computerText.setText("Computer Choice\n"+compChoice);

    }




}


