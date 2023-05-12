import java.io.Serializable;
public class Move implements Serializable {
    private int player;
    private int[] first;
    private int[] second;
    private boolean again;

    public Move(int player, int[] first, int[] second, boolean again) {
        this.player = player;
        this.first = first;
        this.second = second;
        this.again = again;
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

    public boolean getAgain() {
        return again;
    }
}
