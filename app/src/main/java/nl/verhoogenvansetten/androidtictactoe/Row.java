package nl.verhoogenvansetten.androidtictactoe;
import java.util.*;
/**
 * See AI.java for documentation on the use of this AI.
 */
public class Row {

    private int value = 0;

    private List<Box> boxes = new ArrayList<>();

    private int win = 0;

    public Row() {}

    public void addBox(Box b) {

        boxes.add(b);

    }

    public void setValue() {

        int ai = 0;
        int opponent = 0;

        Iterator<Box> it = boxes.iterator();
        while(it.hasNext()) {
            switch (it.next().getState()) {
                case 1:
                    ai += 1;
                    break;
                case 2:
                    opponent += 1;
                    break;
            }
        }
        if(ai == 3)
            win = 1;
        else if (opponent == 3)
            win = 2;
        if(ai == 2)
            value = 10000;
        else if(opponent == 2)
            value = 1000;
        else if(ai == 1)
            if(opponent == 1)
                value = 0;
            else
                value = 100;
        else
        if(opponent == 1)
            value = 1;
        else
            value = 10;
    }

    public int getValue() {

        return value;

    }

    public int getWinner() {

        return win;

    }

}
