package nl.verhoogenvansetten.androidtictactoe;
import java.util.*;
/**
 * Created by bloodyfool on 11-9-16.
 */
public class Box {

    private int state = 0;

    private List<Row> rows = new ArrayList<Row>();

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

        Iterator<Row> it = rows.iterator();
        while(it.hasNext()) {
            v += it.next().getValue();
        }

        return v;

    }

}