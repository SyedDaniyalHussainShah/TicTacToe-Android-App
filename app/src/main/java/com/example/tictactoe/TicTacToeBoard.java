package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class TicTacToeBoard extends View {

    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winningLineColor;

    private boolean winningLine = false; //to determine if we have a winner

    private final Paint paint = new Paint();

    private int cellSize = getWidth() / 3;//default value

    private final GameLogic game;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        game = new GameLogic();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TicTacToeBoard, 0, 0);

        try {
            boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor, 0);
            XColor = a.getInteger(R.styleable.TicTacToeBoard_XColor, 0);
            OColor = a.getInteger(R.styleable.TicTacToeBoard_OColor, 0);
            winningLineColor = a.getInteger(R.styleable.TicTacToeBoard_winningLineColor, 0);
        } finally {
            a.recycle();// recycles the memory
        }
    }

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight()); // getting minimum width or height to adjust our tictactoe board to every screen
        cellSize = dimension / 3;

        setMeasuredDimension(dimension, dimension); //tictactoe board will be a perfect square
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE); //draw lines on board
        paint.setAntiAlias(true); //make the edges smoother


        drawGameBoard(canvas);
        drawMarkers(canvas);

        if(winningLine){

            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }

    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();// to get user touches on board

        if(action == MotionEvent.ACTION_DOWN) {

            int row = (int) Math.floor(y / cellSize);
            int col = (int) Math.floor((x / cellSize));

            if (!winningLine){
                if (game.updateGameBoard(row, col)) {
                    invalidate();//it will redraw our game board by running the onDraw method

                    if(game.winnerCheck()){
                        winningLine = true;
                        invalidate();
                    }

                    //updates the players turn
                    if (game.getPlayer() % 2 == 0) {
                        game.setPlayer(game.getPlayer() - 1);
                    } else {
                        game.setPlayer(game.getPlayer() + 1);
                    }
                }
        }

            return true;
        }

        return false;
    }

    private void drawGameBoard(Canvas canvas) {
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);

        for (int col = 1; col < 3; col++) {
            canvas.drawLine(cellSize * col, 0, cellSize * col, canvas.getWidth(), paint);
        }

        for (int row = 1; row < 3; row++) {
            canvas.drawLine(0, cellSize * row, canvas.getWidth(), cellSize * row, paint);
        }

    }

    private void drawX(Canvas canvas, int row, int col) {
        paint.setColor(XColor);

        canvas.drawLine((float) ((col + 1) * cellSize - cellSize*0.2),
                (float) (row * cellSize + cellSize*0.2),
                (float) (col * cellSize + cellSize*0.2),
                (float) ((row + 1) * cellSize - cellSize*0.2),
                paint); //draws first line of X and cellsize*0.2 is added so that line doesn't touches the border of cell

        canvas.drawLine((float) (col * cellSize + cellSize*0.2),
                (float) (row * cellSize + cellSize*0.2),
                (float) ((col + 1) * cellSize - cellSize*0.2),
                (float) ((row + 1) * cellSize - cellSize*0.2),
                paint); //draws second line of X
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawO(Canvas canvas, int row, int col) {
        paint.setColor(OColor);


            canvas.drawOval((float) (col * cellSize + cellSize*0.2),
                    (float) (row * cellSize + cellSize*0.2),
                (float) (col * cellSize + cellSize*0.9),
                (float) (row * cellSize + cellSize*0.9),
                    paint);// plus cellsize*0.9 values are for width and height



    }

    private void drawHorizontalLine(Canvas canvas, int row, int col){

        canvas.drawLine(col, (float) (row*cellSize+cellSize/2),
                cellSize*3, (float) (row*cellSize+cellSize/2),paint);

    }

    private void drawVerticalLine(Canvas canvas, int row, int col){

        canvas.drawLine((float) (col*cellSize+cellSize/2),  row,
                (float) (col*cellSize+cellSize/2), cellSize*3,paint);

    }

    private void drawDiagonalLinePos(Canvas canvas){

        canvas.drawLine(0,(float) (cellSize*2.9),
                (float)(cellSize*2.9),0,paint);

    }

    private void drawDiagonalLineNeg(Canvas canvas){

        canvas.drawLine(0, (float) (cellSize*0.1),
                (float)(cellSize*2.9),cellSize*3,paint);

    }

    private void drawWinningLine(Canvas canvas){

        int row = game.getWinType()[0];
        int col = game.getWinType()[1];

        switch(game.getWinType()[2]){

            case 1:
                drawHorizontalLine(canvas,row,col);
                break;
            case 2:
                drawVerticalLine(canvas,row,col);
                break;
            case 3:
                drawDiagonalLineNeg(canvas);
                break;
            case 4:
                drawDiagonalLinePos(canvas);
                break;

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private  void drawMarkers(Canvas canvas){

        for(int row=0; row<3; row++){

            for(int col=0; col<3; col++){
                if(game.getGameBoard()[row][col] != 0){
                    if(game.getGameBoard()[row][col] == 1){ // we have represent X as 1
                        drawX(canvas, row, col);
                    }
                    else {
                        drawO(canvas, row, col);
                    }
                }
            }
        }

    }

    public void setupGame(Button playAgain, Button home, TextView playerDisplay, String[] playerNames){

        game.setPlayAgainBTN(playAgain);
        game.setHomeBTN(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(playerNames);

    }


    public void resetGame(){

        game.resetGame();
        winningLine = false;
    }

}
