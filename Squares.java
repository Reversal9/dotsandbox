public class Squares {
    private boolean north;
    private boolean south;
    private boolean east;
    private boolean west;
    private int state;
    final int INCOMPLETE_STATE = 0;
    final int PLAYER_ONE_STATE = 1;
    final int PLAYER_TWO_STATE = 2;

    public boolean getWest() {
        return west;
    }

    public void setWest(boolean west) {
        this.west = west;
    }

    public boolean getEast() {
        return east;
    }

    public void setEast(boolean east) {
        this.east = east;
    }

    public boolean getNorth() {
        return north;
    }

    public void setNorth(boolean north) {
        this.north = north;
    }

    public boolean getSouth() {
        return south;
    }

    public void setSouth(boolean south) {
        this.south = south;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
