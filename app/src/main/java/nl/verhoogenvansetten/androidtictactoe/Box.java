package nl.verhoogenvansetten.androidtictactoe;
import java.util.*;
/**
 * See AI.java for documentation on the use of this AI.
 */
public class Box {

    private int state = 0;

    private List<Row> rows = new ArrayList<>();

    public Box() {


    }

    public void addRow(Row r) {

        rows.add(r);

    }

    public int getState() {

        return state;

    }

    public void setState(int s) {

        this.state = s;

    }

    public int getValue() {

        int v = 0;

        // TODO replace while with foreach?
        Iterator<Row> it = rows.iterator();
        while(it.hasNext()) {
            v += it.next().getValue();
        }

        return v;

    }

}