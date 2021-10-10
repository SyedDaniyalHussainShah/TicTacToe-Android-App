package com.example.tictactoe;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameLogic {

    private int[][] gameBoard;

    private int player = 1;

    private String[] playerNames;
    private Button playAgainBTN;
    private Button homeBTN;
    private TextView playerTurn;

    //1st element = row,2nd element = col, 3rd element = line type
    private int[] winType = {-1,-1,-1};

    public GameLogic(){
        gameBoard = new int[3][3];

        for(int row=0; row<3; row++){

            for(int col=0; col<3; col++){
                gameBoard[row][col] = 0;
            }
        }

    }

    public boolean updateGameBoard(int row, int col){

        if(gameBoard[row][col]== 0){
            gameBoard[row][col] = player;

            if(player == 1){
                playerTurn.setText((playerNames[1] + "'s Turn"));
            }
            else{
                playerTurn.setText((playerNames[0] + "'s Turn"));
            }

            return true;
        }
        else{
            return false;//it will return false if that spot is unavailable
        }
    }

    public boolean winnerCheck(){

        boolean isWinner = false;
        int boardFilled = 0;

        //Horizontal check for winner
        for(int row = 0; row<3 ; row++){

            if(gameBoard[row][0] == gameBoard[row][1] && gameBoard[row][0] == gameBoard[row][2] &&
            gameBoard[row][0] != 0){
                winType = new int[] {row, 0, 1}; //1 represents horizontal line type
                isWinner = true;
            }

        }

        //Vertical check for winner
        for(int col = 0; col<3 ; col++){

            if(gameBoard[0][col] == gameBoard[1][col] && gameBoard[0][col] == gameBoard[2][col] &&
                    gameBoard[0][col] != 0){
                winType = new int[] {0, col, 2}; //2 represents vertical line type
                isWinner = true;
            }

        }

        //Negative Diagonal check for winner
        if(gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] &&
                gameBoard[0][0] != 0){
            isWinner = true;
            winType = new int[] {0, 2, 3}; //3 represents Negative diagonal line type
        }

        //Positive Diagonal Check for winner
        if(gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] &&
                gameBoard[2][0] != 0){
            isWinner = true;
            winType = new int[] {2, 2, 4}; //4 represents Positive diagonal line type
        }

        //checking board filled
        for(int row=0; row<3; row++){

            for(int col=0; col<3; col++){
                if(gameBoard[row][col] != 0){
                    boardFilled += 1;
                }
            }
        }

        if(isWinner){
            playAgainBTN.setVisibility(View.VISIBLE);
            homeBTN.setVisibility(View.VISIBLE);
            playerTurn.setText((playerNames[player-1] + " Won!!!!"));
        }
        else if(boardFilled == 9){
            playAgainBTN.setVisibility(View.VISIBLE);
            homeBTN.setVisibility(View.VISIBLE);
            playerTurn.setText(("Tie Game!!!!"));
        }

        return isWinner;
    }

    public void resetGame(){

        for(int row=0; row<3; row++){

            for(int col=0; col<3; col++){
                gameBoard[row][col] = 0;
            }
        }
        // setting attributes to default
        player = 1;
        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);
        playerTurn.setText((playerNames[0] + "'s Turn"));

    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }

    public void setPlayAgainBTN(Button playAgainBTN) {
        this.playAgainBTN = playAgainBTN;
    }

    public void setHomeBTN(Button homeBTN) {
        this.homeBTN = homeBTN;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public int[] getWinType() {
        return winType;
    }
}
