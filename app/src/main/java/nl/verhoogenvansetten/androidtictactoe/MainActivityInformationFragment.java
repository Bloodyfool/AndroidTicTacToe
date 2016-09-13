package nl.verhoogenvansetten.androidtictactoe;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityInformationFragment extends Fragment {

    Button button1,button2,button3,button4,button5,button6,button7,button8,button9,onButtonReset;
    Button[] bArray;

    public MainActivityInformationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information, container, false);
    }




}
