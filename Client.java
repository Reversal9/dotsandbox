import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    private static int PLAYER;
    public static Connection connection;
    private static GameFrame frame;
    private static boolean pressRestart;
    private static boolean start;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8000);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            connection = new Connection(socket, ois, oos);
            start = false;
            String text = "";
            Scanner kb = new Scanner(System.in);


            CommandFromServer cfs = (CommandFromServer) ois.readObject();
            if (cfs.getCommand() == CommandFromServer.CONNECTED_AS_P1) {
                System.out.println("Connected as Player 1");
                PLAYER = CommandFromServer.CONNECTED_AS_P1;
            } else {
                System.out.println("Connected as Player 2");
                PLAYER = CommandFromServer.CONNECTED_AS_P2;
            }

            frame = new GameFrame("Connect Four", connection, PLAYER);
            //frame.drawWait();

            while (true) {
                CommandFromServer cmd = (CommandFromServer) ois.readObject();
                if (!start) {
                    start = true;
                    frame.reset();
                }
                System.out.println("Bro");
                System.out.println(cmd.getCommand());
                if (cmd.getCommand() == CommandFromServer.RESTART) {
                    frame.reset();
                    continue;
                } else if (cmd.getCommand() == CommandFromServer.MOVE) {
                    Move data = (Move) cmd.getData();
                    System.out.println(data.getPlayer());
                    if (data.getPlayer() == GameData.PLAYER_1) {
                        frame.drawLine(data.getFirst(), data.getSecond());
                    } else {
                        frame.drawLine(data.getFirst(), data.getSecond());
                    }
                    //System.out.println(Arrays.deepToString(board));
                } else if(cmd.getCommand() == CommandFromServer.P1_TURN && PLAYER == CommandFromServer.CONNECTED_AS_P1) {
                    System.out.println("Your turn");
                    frame.drawTurn();
                    MouseThread t = new MouseThread(GameData.PLAYER_1, connection, frame);
                    t.run();
                    frame.drawTurn();
                } else if(cmd.getCommand() == CommandFromServer.P2_TURN && PLAYER == CommandFromServer.CONNECTED_AS_P2) {
                    System.out.println("Your turn");
                    frame.drawTurn();
                    MouseThread t = new MouseThread(GameData.PLAYER_2, connection, frame);
                    t.run();
                    frame.drawTurn();
                } else if (cmd.getCommand() == CommandFromServer.P1_WINS || cmd.getCommand() == CommandFromServer.P2_WINS ||
                        cmd.getCommand() == CommandFromServer.TIE) {
                    frame.drawEnd(cmd.getCommand());
                } else if (cmd.getCommand() == CommandFromServer.SHUT_DOWN) {
                    System.out.println("Hi");
                    frame.drawShutDown();
                    frame.drawCountdown(5);
                    Thread.sleep(1000);
                    frame.drawCountdown(4);
                    Thread.sleep(1000);
                    frame.drawCountdown(3);
                    Thread.sleep(1000);
                    frame.drawCountdown(2);
                    Thread.sleep(1000);
                    frame.drawCountdown(1);
                    Thread.sleep(1000);
                    frame.drawCountdown(0);

                    System.exit(0);
                } else if (cmd.getCommand() == CommandFromServer.REQUEST_RESTART) {
                    frame.drawRestart();
                    if (pressRestart) {
                        frame.reset();
                        pressRestart = false;
                        continue;
                    }
                    pressRestart = true;
                    System.out.println("RESTART");
                    RestartThread t = new RestartThread(PLAYER, connection, frame);
                    t.run();
                }
                //ois.reset();
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static int dropAtCol(int[] first, int[] second, int player) {
        //edit board implement
        return 0;
    }
}