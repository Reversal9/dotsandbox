import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
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
                System.out.println(cmd.getCommand());
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
                        frame.drawLine(data.getFirst(), data.getSecond(), Color.black);
                    } else {
                        frame.drawLine(data.getFirst(), data.getSecond(), Color.RED);
                    }
                    //System.out.println(Arrays.deepToString(board));

                    System.out.println("Hihihihihih");
                    if(!(data.getFirst()[0] <= -1 || data.getFirst()[1] <= -1 || data.getSecond()[0] <= -1 || data.getSecond()[1] <= -1)) {
                        if (Math.abs(data.getSecond()[1] - data.getFirst()[1]) >= Math.abs(data.getSecond()[0] - data.getFirst()[0])) {
                            if (data.getSecond()[1] - data.getFirst()[1] < 0)
                                data.getSecond()[1] = data.getFirst()[1] - 1;
                            else
                                data.getSecond()[1] = data.getFirst()[1] + 1;
                            data.getSecond()[0] = data.getFirst()[0];

                            if (data.getFirst()[1] == 0 || data.getSecond()[1] == 0) {
                                if (data.getFirst()[0] == 5)
                                    frame.panel.getData().getSquares(4, 0).setSouth(true);
                                else {
                                    frame.panel.getData().getSquares(data.getFirst()[0], 0).setNorth(true);
                                    System.out.print("jkjbbbbbbskds");
                                    ;
                                }
                            } else if (data.getFirst()[1] == 5 || data.getSecond()[1] == 5) {
                                System.out.print("jksdjsslslsls");
                                ;
                                if (data.getFirst()[0] == 5)
                                    frame.panel.getData().getSquares(4, 4).setSouth(true);
                                else {
                                    frame.panel.getData().getSquares(data.getFirst()[0], 4).setNorth(true);

                                }
                            } else if (data.getFirst()[1] == 4 || data.getSecond()[1] == 4) {
                                if (data.getFirst()[0] == 5)
                                    frame.panel.getData().getSquares(4, 3).setSouth(true);
                                else {
                                    frame.panel.getData().getSquares(data.getFirst()[0], 3).setNorth(true);

                                }
                            } else if (data.getFirst()[1] == 3 || data.getSecond()[1] == 3) {
                                if (data.getFirst()[0] == 5)
                                    frame.panel.getData().getSquares(4, 2).setSouth(true);
                                else {
                                    frame.panel.getData().getSquares(data.getFirst()[0], 2).setNorth(true);

                                }
                            } else if (data.getFirst()[1] == 2 || data.getSecond()[1] == 2) {
                                if (data.getFirst()[0] == 5)
                                    frame.panel.getData().getSquares(4, 1).setSouth(true);
                                else {
                                    frame.panel.getData().getSquares(data.getFirst()[0], 1).setNorth(true);

                                }
                            }
                        } else {
                            if (data.getSecond()[0] - data.getFirst()[0] < 0)
                                data.getSecond()[0] = data.getFirst()[0] - 1;
                            else
                                data.getSecond()[0] = data.getFirst()[0] + 1;
                            data.getSecond()[1] = data.getFirst()[1];

                            if (data.getFirst()[0] == 0 || data.getSecond()[0] == 0) {
                                if (data.getFirst()[1] == 5)
                                    frame.panel.getData().getSquares(0, 4).setEast(true);
                                else {
                                    frame.panel.getData().getSquares(0, data.getFirst()[1]).setWest(true);

                                }
                            } else if (data.getFirst()[0] == 5 || data.getSecond()[0] == 5) {
                                if (data.getFirst()[1] == 5)
                                    frame.panel.getData().getSquares(4, 4).setEast(true);
                                else {
                                    frame.panel.getData().getSquares(4, data.getFirst()[1]).setWest(true);

                                }
                            } else if (data.getFirst()[0] == 4 || data.getSecond()[0] == 4) {
                                if (data.getFirst()[1] == 5)
                                    frame.panel.getData().getSquares(3, 4).setEast(true);
                                else {
                                    frame.panel.getData().getSquares(3, data.getFirst()[1]).setWest(true);

                                }
                            } else if (data.getFirst()[0] == 3 || data.getSecond()[0] == 3) {
                                if (data.getFirst()[1] == 5)
                                    frame.panel.getData().getSquares(2, 4).setEast(true);
                                else {
                                    frame.panel.getData().getSquares(2, data.getFirst()[1]).setWest(true);

                                }
                            } else if (data.getFirst()[0] == 2 || data.getSecond()[0] == 2) {
                                if (data.getFirst()[1] == 5)
                                    frame.panel.getData().getSquares(1, 4).setEast(true);
                                else {
                                    frame.panel.getData().getSquares(1, data.getFirst()[1]).setWest(true);

                                }
                            }
                        }
                        frame.panel.getData().fixSides(PLAYER);
                    }


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
                    System.out.println("drew end");
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
                    System.out.println("restarted");
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