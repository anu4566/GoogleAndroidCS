package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.view.KeyEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    private String fragment = "";
    private String dictionaryWord,challengeString;

    TextView ghostText, gameStatus;
    Button challenge, restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);

        ghostText = (TextView) findViewById(R.id.ghostText);
        gameStatus = (TextView) findViewById(R.id.gameStatus);

        challenge = (Button) findViewById(R.id.challenge);
        restart = (Button) findViewById(R.id.reset);

        challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                challengeComputer();
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart(view);
            }
        });
        AssetManager assetManager = getAssets();
        try
        {
            InputStream inputStream = assetManager.open("words.txt");
            //dictionary = new FastDictionary(inputStream);
              dictionary = new SimpleDictionary(inputStream);

        }
        catch (IOException e)
        {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("stringFragment", fragment);
        outState.putBoolean("userTurn", userTurn);
        outState.putString("challengeString",challengeString);
        outState.putString("tvGhostText", ghostText.getText().toString());
        outState.putString("tvGameStatus", gameStatus.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fragment = savedInstanceState.getString("fragment");
        userTurn = savedInstanceState.getBoolean("userTurn");
        ghostText.setText(savedInstanceState.getString("ghostText"));
        gameStatus.setText(savedInstanceState.getString("gameStatus"));
        challengeString = savedInstanceState.getString("challengeString");
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        fragment = "";
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn)
        {
            label.setText(USER_TURN);
        }
        else
        {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        char userInput = (char) event.getUnicodeChar();
        userInput = Character.toLowerCase(userInput);
        if(userInput < 'a' || userInput > 'z')
        {
            gameStatus.setText("Invalid input");
            super.onKeyUp(keyCode, event);
        }
        else
        {
            gameStatus.setText("Valid Input");
            fragment = (fragment + event.getDisplayLabel()).toLowerCase();
            ghostText.setText(fragment);
            userTurn = false;
            gameStatus.setText(COMPUTER_TURN);
            computerTurn();
            return true;
        }
        return true;
    }

    private void challengeComputer() {

        challengeString = dictionary.getAnyWordStartingWith(fragment);

        if (fragment.length() >= 4 && dictionary.isWord(fragment))
        {
            challengeString = fragment + " is a valid word!";
            ghostText.setText(challengeString);
            gameStatus.setText("You win!");
        }
        else
        {
            gameStatus.setText("Computer wins!" + "\n" + "The word can be: " + challengeString);
        }
    }

    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);

        if (fragment.length() == 0)
        {
            char randChar = (char) (random.nextInt(26) + 97);
            fragment = "";
            fragment += randChar;
            ghostText.setText(fragment);
            userTurn = true;
            gameStatus.setText(USER_TURN);
            return;
        }

        if (fragment != null && fragment.length() >= 4)
        {
            if (dictionary.isWord(fragment))
            {
                dictionaryWord = fragment + " is a word!";
                ghostText.setText(dictionaryWord);
                gameStatus.setText("Computer Wins!");
                return;
            }
        }
            dictionaryWord = dictionary.getAnyWordStartingWith(fragment);
           // dictionaryWord = dictionary.getGoodWordStartingWith(fragment, userTurn);

            if(dictionaryWord == null )
            {
                dictionaryWord = "A valid word cannot be formed with " + fragment;
                ghostText.setText(dictionaryWord);
                fragment ="";
                gameStatus.setText("Computer Wins!");
            }
            else
            {
                char nextChar = dictionaryWord.charAt(fragment.length());
                fragment += nextChar;
                ghostText.setText(fragment);
                userTurn = true;
                label.setText(USER_TURN);
            }

    }
}
