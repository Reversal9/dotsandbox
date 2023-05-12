import java.util.ArrayList;

public class GameData {
    public static Squares[][] grid = new Squares[5][5];
    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;
    public int turn;

    public static ArrayList<Tuple> repeated;

    static class Tuple {
        public int[] first;
        public int[] second;

        public Tuple(int[] first, int[] second) {
            this.first = first;
            this.second = second;
        }
    }

    public GameData() {
        reset();
    }

    public void reset() {
        grid = new Squares[5][5];
        repeated = new ArrayList<>();
        for (Squares[] rowColumn : grid) {
            for (int i = 0; i < rowColumn.length; i++) {
                rowColumn[i] = new Squares();
            }
        }
    }

    public boolean hasRepeated(int[] first, int[] second) {
        for (var a : repeated) {
            if (a.first[0] == first[0] && a.first[1] == first[1] && a.second[0] == second[0] && a.second[1] == second[1]) {
                return true;
            } else if (a.first[0] == second[0] && a.first[1] == second[1] && a.second[0] == first[0] && a.second[1] == first[1]) {
                return true;
            }
        }
        repeated.add(new Tuple(first, second));
        repeated.add(new Tuple(second, first));
        return false;
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

    public void updateStates(Squares square, int player){
        if(square.getEast() && square.getNorth() && square.getSouth() && square.getWest() && square.getState() == 0)
            square.setState(player);
    }

    public void printState(){
        for (int r = 0; r < grid.length; r++) {
            System.out.println("--------------------------------------------");
            for (int c = 0; c < grid[0].length; c++) {
                System.out.print("E:" + grid[r][c].getEast() + " W: " + grid[r][c].getWest() + " N: " + grid[r][c].getNorth() + " S: " + grid[r][c].getSouth() + " || ");
            }
        }
    }

    public void fixSides(int player){
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                updateStates(grid[r][c], player);
                if(c - 1 >= 0 && grid[r][c-1].getEast())
                    grid[r][c].setWest(true);
                if(c + 1 < grid[0].length && grid[r][c+1].getWest())
                    grid[r][c].setEast(true);
                if(r - 1 >= 0 && grid[r-1][c].getSouth())
                    grid[r][c].setNorth(true);
                if(r + 1 < grid.length && grid[r+1][c].getNorth())
                    grid[r][c].setSouth(true);
            }
        }
    }
}
