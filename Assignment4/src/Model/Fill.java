/*
    Tracks the state of the current fill operation during gameplay.
    Manages the eight outer cells around the game board, recording which cells
    have been selected, the cumulative strength (sum of selected cell values),
    and the order of selection for weapon condition checking. Records timing
    information for time-based weapons and maps cell indices to opponent positions
    (left, middle, right) for targeting. Provides methods to check fill completion,
    validate selection patterns (ascending/descending order), and determine the final
    target position based on the last selected cell.
*/
package Model;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Fill {
    private int strength;
    private boolean [] filledCells;
    private long startTime;
    private List<Integer> selectionOrder;
    private int cellsAdded;
    private int lastFilledIndex = -1;

    public Fill() {
        this.strength = 0;
        this.filledCells = new boolean[8];//??? how did it know it was 8....
        this.startTime = System.currentTimeMillis();
        this.selectionOrder = new ArrayList<>();
        this.cellsAdded = 0;
    }

    public void addCell(int cellIndex, int cellValue){
        filledCells[cellIndex] = true;
        strength += cellValue;
        selectionOrder.add(cellValue);
        cellsAdded++;
        this.lastFilledIndex = cellIndex;
    }

    public Optional<Position.PositionType> getFinalCellPosition(){
        if(isComplete()){
            return getPosition(lastFilledIndex);
        }
        return Optional.empty();
    }

    private Optional<Position.PositionType> getPosition(int index){
        return switch (index) {
            case 0, 6, 7 -> Optional.of(Position.PositionType.LEFT);
            case 1, 5 -> Optional.of(Position.PositionType.MIDDLE);
            case 2, 3, 4 -> Optional.of(Position.PositionType.RIGHT);
            default -> Optional.empty();
        };
    }

    public boolean isComplete(){
        for(boolean filled : filledCells){
            if(!filled) return false;
        }
        return true;
    }

    public boolean isCellFilled(int index) {
        return filledCells[index];
    }

    public boolean isAscendingOrder(){
        if(selectionOrder.isEmpty()) return false;

        for(int i = 1; i < selectionOrder.size(); i++){
            if(selectionOrder.get(i - 1) >
                    selectionOrder.get(i)) return false;
        }
        return true;
    }

    public boolean isDescendingOrder() {
        if(selectionOrder.isEmpty()) return false;

        for(int i = 1; i < selectionOrder.size(); i++){
            if(selectionOrder.get(i) >
                    selectionOrder.get(i-1)) return false;
        }
        return true;
    }

    public Long getFillTime(){
        return System.currentTimeMillis() - startTime;
    }
    public int getStrength() {
        return strength;
    }
    public int getCellsAdded() {
        return cellsAdded;
    }
}
