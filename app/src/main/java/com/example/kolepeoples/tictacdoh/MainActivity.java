package com.example.kolepeoples.tictacdoh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageViewBat;
    private ImageView imageViewLastSon;

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1Pts;
    private int player2Pts;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewBat = findViewById(R.id.batman);
        imageViewLastSon = findViewById(R.id.superman);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("B");
        }
        else {
            ((Button)v).setText("S");
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn)
                player1Wins();

            else {
                player2Wins();
            }
        }
        else if (roundCount == 9) {
            draw();
        }
        else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
                return true;
        }

        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Pts++;
        Toast.makeText(this, "Batman wins!", Toast.LENGTH_SHORT).show();
        updatePtsText();
        resetBoard();

    }

    private void player2Wins() {
        player2Pts++;
        Toast.makeText(this, "Superman wins!", Toast.LENGTH_SHORT).show();
        updatePtsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePtsText() {
        textViewPlayer1.setText("Player 1: " + player1Pts);
        textViewPlayer2.setText("Player 2: " + player2Pts);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame() {
        textViewPlayer1.setText("Player 1: 0");
        textViewPlayer2.setText("Player 2: 0");
        player1Pts = 0;
        player2Pts = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Pts", player1Pts);
        outState.putInt("player2Pts", player2Pts);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Pts = savedInstanceState.getInt("player1Pts");
        player2Pts = savedInstanceState.getInt("player2Pts");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
