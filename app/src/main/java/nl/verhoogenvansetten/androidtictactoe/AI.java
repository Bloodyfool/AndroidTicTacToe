package nl.verhoogenvansetten.androidtictactoe;
/**
 * This is the AI for a TicTacToe game.
 * To use the AI first call the ai.getInstance method to get an AI object.
 * To get a move call one of three functions:
 *      int getRandomMove(int[] board) for a random move
 *      int getMediumMove(int[] board) for a move that is random unless the ai or player is about to win
 *      int getHardMove(int[] board) for the shortsighted best move
 *
 * Either off these can be called at any point and all take the current board as input.
 * The board will be input in the following order:
 *      0|1|2
 *      3|4|5
 *      6|7|8
 * The array must be populated with 0 for an empty spot 1 for the AI and 2 for the opponent
 * The return value will be a possible move in the form of an integer between 0 and 8 in the same
 * configuration as the input screen.
 *
 */
public class AI {

    private static AI firstInstance = null;

    private Box[] boxes = new Box[9];

    private Row[] rows = new Row[8];

    private int theValue = 0;

    private AI() {
        //make boxes
        for(int i = 0; i < 9; i++)
            boxes[i] = new Box();

        //make rows
        for(int i = 0; i < 8; i++)
            rows[i] = new Row();

        //give rows to boxes and boxes to rows
        for(int h = 0; h < 3; h++)
            for(int v = 0; v < 3; v++) {
                //assigns horizontal rows
                boxes[h*3+v].addRow(rows[h]);
                rows[h].addBox(boxes[h*3+v]);

                //assigns vertical rows
                boxes[h*3+v].addRow(rows[v+3]);
                rows[v+3].addBox(boxes[h*3+v]);
            }

        //assigns diagonal rows
        for(int i = 0; i < 3; i++) {
            boxes[i*3+i].addRow(rows[6]);
            rows[6].addBox(boxes[i*3+i]);
            boxes[i*3+(2-i)].addRow(rows[7]);
            rows[7].addBox(boxes[i*3+(2-i)]);
        }

    }

    public static AI getInstance() {

        if(firstInstance == null){

            firstInstance = new AI();
        }

        return firstInstance;

    }

    public int getHardMove(int[] board) {

        int best = 0;
        int value;
        int bestValue = 0;

        //set board
        for(int i = 0; i < 9; i++)
            boxes[i].setState(board[i]);

        //set row value
        for(int i = 0; i < 8; i++)
            rows[i].setValue();

        //get box value
        for(int i = 0; i < 9; i++) {
            if(boxes[i].getState() == 0) {
                value = boxes[i].getValue();
                if(value >= bestValue) {
                    bestValue = value;
                    best = i;
                }
            }
        }

        theValue = bestValue;

        //return best box
        return best;

    }

    public int getRandomMove(int[] board) {

        while(true) {
            int i = (int)(Math.random()*9);
            if(board[i] == 0)
                return i;
        }
    }

    public int getMediumMove(int[] board) {

        int val = getHardMove(board);

        if(theValue < 1000)
            return getRandomMove(board);
        else
            return val;
    }
}