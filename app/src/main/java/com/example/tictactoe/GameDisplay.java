package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameDisplay extends AppCompatActivity {

    private TicTacToeBoard ticTacToeBoard;
    private Button playAgainBTN;
    private Button homeBTN;
    private TextView playerDisplay;

    String [] playerNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_display);

        ticTacToeBoard = findViewById(R.id.ticTacToeBoard);
        playAgainBTN = findViewById(R.id.play_again);
        homeBTN = findViewById(R.id.home_button);
        playerDisplay = findViewById(R.id.player_display);

        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);

        playerNames = getIntent().getStringArrayExtra("PLAYER_NAMES");

        playerDisplay.setText((playerNames[0] + "'s Turn"));//setting the initial value


        ticTacToeBoard.setupGame(playAgainBTN, homeBTN, playerDisplay, playerNames);

    }

    public void playAgainButtonClick(View view){

        ticTacToeBoard.resetGame();
        ticTacToeBoard.invalidate();

    }

    public void homeButtonClick(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}