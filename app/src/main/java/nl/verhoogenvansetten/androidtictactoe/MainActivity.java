package nl.verhoogenvansetten.androidtictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button1, button2, button3, button4, button5, button6, button7, button8, button9, onButtonReset;
    Button[] bArray;
    TextView txtvGamesPlayed;
    SharedPreferences prefs;
    char player='O';
    boolean turn = true;
    boolean gameOver = false;
    AI ai = AI.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (PreferenceManager.getDefaultSharedPreferences(this).getString("theme_list", "0").equals("1")) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        txtvGamesPlayed = (TextView) findViewById(R.id.txtvGamesPlayed);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("games_played", prefs.getInt("games_played", 0)+1);
        editor.apply();
        setGamesPlayed(prefs.getInt("games_played", 0));

        bArray = new Button[]{button1, button2, button3, button4, button5,
                button6, button7, button8, button9};

        RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);
        RadioButton radioButtonO = (RadioButton) findViewById(R.id.radioButtonO);
        RadioButton radioButtonX = (RadioButton) findViewById(R.id.radioButtonX);

        group.check(R.id.radioButtonO);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        onButtonReset = (Button) findViewById(R.id.onButtonReset);

        bArray = new Button[]{button1, button2, button3, button4, button5,
                button6, button7, button8, button9};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Instant-apply settings, sort of
    @Override
    public void onResume() {
        super.onResume();

        String colour = prefs.getString("text_colour_list", "black");
        switch (colour) {
            case "black":
                setColour(getResources().getColor(R.color.md_black));
                break;
            case "yellow":
                setColour(getResources().getColor(R.color.md_yellow_500));
                break;
            default:
                setColour(getResources().getColor(R.color.md_red_500));
                break;
        }

        setSize(Float.parseFloat(prefs.getString("text_size_list", "40")));
    }

    /*
    private void setMove(char player, int location) {
        mBoardButtons[location].setEnabled(false);
        if (player == mGameFragment.PLAYER_ONE)
            mBoardButtons[location].setBackgroundDrawable(getResources().getDrawable(R.drawable.x));
        else
            mBoardButtons[location].setBackgroundDrawable(getResources().getDrawable(R.drawable.o));
    }
*/
    private int[] getBoard() {
        String temp;
        int[] board = new int[9];
        for(int i = 0; i < 9; i++) {
            temp = bArray[i].getText().toString();
            if(temp.equals(Character.toString(player)))
                board[i] = 2;
            else if (temp.equals(""))
                board[i] = 0;
            else
                board[i] = 1;
        }
        return board;
    }

    public void aiMove() {
        String comp;
        String difficulty = prefs.getString("difficulty_list", "easy");
        int move;

        switch (difficulty) {
            case "easy":
                move = ai.getRandomMove(getBoard());
                break;
            case "medium":
                move = ai.getMediumMove(getBoard());
                break;
            default:
                move = ai.getHardMove(getBoard());
                break;
        }

        if(player == 'O')
            comp = "X";
        else
            comp = "O";

        bArray[move].setClickable(false);
        bArray[move].setText(comp);
    }

    public boolean winCheck() {
        int win = ai.checkWin(getBoard());
        if(win != 0) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("games_played", prefs.getInt("games_played", 0)+1);
            editor.apply();
            setGamesPlayed(prefs.getInt("games_played", 0));
            gameOver = true;
            switch (win) {
                case 1:
                    // You lose
                    Toast.makeText(getApplicationContext(), R.string.game_lost, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    // You win
                    Toast.makeText(getApplicationContext(), R.string.game_won, Toast.LENGTH_SHORT).show();
                    break;
            }
            resetBoard(false);
            return true;
        }
        return false;
    }

    public void onClick(View v) {
        Button b = (Button)v;
        if(turn) {
            if (player == 'O') {
                b.setText(R.string.o);
            }
            else if (player == 'X') {
                b.setText(R.string.x);
            }
        }
        b.setClickable(false);
        if (winCheck())
            return;
        aiMove();
    }

    public void onButtonReset(View v) {
        resetBoard(true);
        gameOver = false;
    }

    public void resetBoard(boolean en){
        if(en) {
            button1.setText("");
            button2.setText("");
            button3.setText("");
            button4.setText("");
            button5.setText("");
            button6.setText("");
            button7.setText("");
            button8.setText("");
            button9.setText("");
        }

        button1.setClickable(en);
        button2.setClickable(en);
        button3.setClickable(en);
        button4.setClickable(en);
        button5.setClickable(en);
        button6.setClickable(en);
        button7.setClickable(en);
        button8.setClickable(en);
        button9.setClickable(en);

        if (!gameOver) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("games_played", prefs.getInt("games_played", 0)+1);
            editor.apply();
            setGamesPlayed(prefs.getInt("games_played", 0));
        }
    }

    public void onRadioButtonClicked(View v) {
        boolean checked = ((RadioButton) v).isChecked();

        switch (v.getId()){
            case R.id.radioButtonO:
                if(checked){
                    player = 'O';
                    Toast.makeText(getApplicationContext(), R.string.player_o, Toast.LENGTH_SHORT).show();
                    resetBoard(true);
                }
                break;
            case R.id.radioButtonX:
                if(checked) {
                    player = 'X';
                    Toast.makeText(getApplicationContext(), R.string.player_x, Toast.LENGTH_SHORT).show();
                    resetBoard(true);
                }
                break;
        }
    }

    private void setColour(int colour) {
        button1.setTextColor(colour);
        button2.setTextColor(colour);
        button3.setTextColor(colour);
        button4.setTextColor(colour);
        button5.setTextColor(colour);
        button6.setTextColor(colour);
        button7.setTextColor(colour);
        button8.setTextColor(colour);
        button9.setTextColor(colour);
    }

    private void setSize(float size) {
        button1.setTextSize(size);
        button2.setTextSize(size);
        button3.setTextSize(size);
        button4.setTextSize(size);
        button5.setTextSize(size);
        button6.setTextSize(size);
        button7.setTextSize(size);
        button8.setTextSize(size);
        button9.setTextSize(size);
    }

    private void setGamesPlayed(int games) {
        txtvGamesPlayed.setText(String.format(getString(R.string.games_played), games));
    }
}
