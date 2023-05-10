import java.io.Serializable;
public class Move implements Serializable {
    private int player;
    private int[] first;
    private int[] second;

    public Move(int player, int[] first, int[] second) {
        this.player = player;
        this.first = first;
        this.second = second;
    }

    public int getPlayer() {
        return player;
    }

    public int[] getFirst() {
        return first;
    }

    public int[] getSecond() {
        return second;
    }
}
