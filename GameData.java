import java.util.Arrays;

public class GameData {
    public static Squares[][] grid;
    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;
    public int turn;

    public GameData() {
        reset();
    }

    public void reset() {
        grid = new Squares[5][5];
        for (Squares[] rowColumn : grid) {
            Arrays.fill(rowColumn, new Squares());
        }
    }

    public Squares[][] getGrid() {
        return grid;
    }

    public Squares getSquares(int row, int col) {
        return grid[row][col];
    }

    public boolean isFinished(){
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if(grid[r][c].getState() == 0)
                    return false;
            }
        }
        return true;
    }

    public static int hasWon()
    {
        int count1 = 0;
        int count2 = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if(grid[r][c].getState() == 1)
                    count1++;
                if(grid[r][c].getState() == 2)
                    count2++;
            }
        }
        if(count2 > count1)
            return 2;
        return 1;
    }

    public void setTurn(int player)
    {
        turn = player;
    }

    public int getTurn()
    {
        return turn;
    }

    public String toString() {
        String answer = "";
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                answer += grid[r][c];
            }
            answer += "\n";
        }
        return answer;
    }

    public void updateState(Squares square, int player){
        if(square.getEast() && square.getNorth() && square.getSouth() && square.getWest())
            square.setState(player);
    }
}