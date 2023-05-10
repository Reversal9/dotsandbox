import java.io.Serializable;

public class CommandFromServer implements Serializable
{
    // The command being sent to the client
    private int command;
    // Text data for the command
    private Object data ="";

    // Command list
    public static final int CONNECTED_AS_P1=0;
    public static final int CONNECTED_AS_P2=1;
    public static final int P1_TURN=2;
    public static final int P2_TURN=3;
    public static final int MOVE=4;
    public static final int P1_WINS=5;
    public static final int P2_WINS=6;
    public static final int TIE=7;
    public static final int REQUEST_RESTART=8;
    public static final int RESTART=9;
    public static final int SHUT_DOWN=10;

    public CommandFromServer(int command, Object data) {
        this.command = command;
        this.data = data;
    }

    public int getCommand() {
        return command;
    }

    public Object getData() {
        return data;
    }
}
