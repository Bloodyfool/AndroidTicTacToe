package nl.verhoogenvansetten.androidtictactoe;

import android.content.Intent;
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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button1, button2, button3, button4, button5, button6, button7, button8, button9, onButtonReset;
    Button[] bArray;
    char player;
    boolean turn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (PreferenceManager.getDefaultSharedPreferences(this)
                .getString("theme_list", "0").equals("1")) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bArray = new Button[]{button1, button2, button3, button4, button5,
                button6, button7, button8, button9};

        RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);
        RadioButton radioButtonO = (RadioButton) findViewById(R.id.radioButtonO);
        RadioButton radioButtonX = (RadioButton) findViewById(R.id.radioButtonX);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    public void onClick(View v) {
        Button b = (Button)v;
        if(turn) {
            if (player == 'O')
                b.setText("O");
            else if (player == 'X')
                b.setText("X");
        }
        b.setClickable(false);
    }

    public void onButtonReset(View v) {
        for(Button b : bArray){
            b.setText("");
            b.setEnabled(true);
        }
            // b.setBackgroundDrawable(getResources().getDrawable(R.drawable.blank));
    }

    public void onRadioButtonClicked(View v) {
        boolean checked = ((RadioButton) v).isChecked();

        switch (v.getId()){
            case R.id.radioButtonO:
                if(checked){
                    player = 'O';
                    Toast.makeText(getApplicationContext(),"You are O", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radioButtonX:
                if(checked) {
                    player = 'X';
                    Toast.makeText(getApplicationContext(), "You are X", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
