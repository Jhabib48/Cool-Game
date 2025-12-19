/*
    Manages the 3x3 game grid with center cell and eight outer cells.
    Handles board initialization with random values, validates player
    moves by checking sums, and updates cells during gameplay. Supports
    cheat commands for modifying maximum cell values and tracks which outer
    cells are filled. Maintains the mapping between cell indices and board
    positions for display and targeting.
*/
package Model;
import java.util.Random;

public class GameBoard {
    private int [][] grid = new int[3][3];
    private int[] outerCells = new int[8];

    private int MAX_VALUE = 15;
    private final Random rand = new Random();

    public GameBoard(){
        initializeBoard();
    }

    public void setMaxValue(int max) {
        if (max >= 0) {
            this.MAX_VALUE = max;
        }
    }

    public void initializeBoard(){
        grid[1][1] = getRandomValue();
        for(int i = 0; i < outerCells.length; i++){
            outerCells[i] = getRandomValue();
        }
    }

    public boolean isValidMove(int sum, Fill currentFill){
        int center = getCenterValue();
        for(int i = 0; i < outerCells.length; i++){
            if(!currentFill.isCellFilled(i) &&
                    (center + outerCells[i] == sum)){
                return true;
            }
        }
        return false;
    }

    public int getRandomValue(){return rand.nextInt(MAX_VALUE + 1);}
    public int getCenterValue(){
        return grid[1][1];
    }
    public int getOuterValue(int index){
        return outerCells[index];
    }
    public void updateCell(int index, int newValue){
        outerCells[index] = newValue;
    }
    public void updateCenterCell(int newValue){
        grid[1][1] = newValue;
    }
}
