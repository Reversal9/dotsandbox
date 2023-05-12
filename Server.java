import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;

public class Server {
    public static ServerSocket serverSocket;
    public static Connection PLAYER_ONE;
    public static Connection PLAYER_TWO;
    public static int turn;
    public static GameData game;


    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("Starting server socket...");
        serverSocket = new ServerSocket(8000);
        System.out.println("Initialized ConnectionThread...");
        Thread connectionListener = new Thread(new ConnectionThread());
        connectionListener.start();

        try {
            while (true) {
                if (game == null && PLAYER_TWO != null && PLAYER_ONE != null) {
                    broadcast(new CommandFromServer(CommandFromServer.RESTART, null));
                    game = new GameData(); //should be the below one
                    //game = new GameData(new int[6][7], CommandFromServer.P1_TURN, false);
                    game.setTurn(GameData.PLAYER_1);
                    turn = GameData.PLAYER_1;
                }
                System.out.print("");

                if (game == null) continue;
                System.out.print("");

                if (!game.isFinished()) {
                    if (turn == GameData.PLAYER_1) {
                        System.out.println("P1 turn");
                        //P1
                        broadcast(new CommandFromServer(CommandFromServer.P1_TURN, null));
                        CommandFromClient cfc = (CommandFromClient) PLAYER_ONE.getIs().readObject();

                        if (cfc.getCommand() == CommandFromClient.MOVE) {
                            Move move = (Move) cfc.getData();


                            /*if (!game.dropAt(move.getColumn(), Game.PLAYER_1)) {
                                continue;
                            }*/

                            broadcast(new CommandFromServer(CommandFromServer.MOVE, new Move(GameData.PLAYER_1, move.getFirst(), move.getSecond())));
                            switchTurns();
                        } else {
                            continue;
                        }
                        //PLAYER_ONE.getIs().reset();
                        System.out.println(Arrays.deepToString(game.getGrid()));
                        System.out.println(game.isFinished());
                    } else if (turn == GameData.PLAYER_2) {
                        System.out.println("P2 Turn");
                        //P2
                        broadcast(new CommandFromServer(CommandFromServer.P2_TURN, null));
                        CommandFromClient cfc = (CommandFromClient) PLAYER_TWO.getIs().readObject();

                        if (cfc.getCommand() == CommandFromClient.MOVE) {
                            Move move = (Move) cfc.getData();
                            /*if (!game.dropAt(move.getColumn(), Game.PLAYER_2)) {
                                continue;
                            }*/

                            broadcast(new CommandFromServer(CommandFromServer.MOVE, new Move(GameData.PLAYER_2, move.getFirst(), move.getSecond())));
                            switchTurns();
                        }
                        //PLAYER_TWO.getIs().reset();
                        System.out.println(Arrays.deepToString(game.getGrid()));
                        System.out.println(game.isFinished());
                    }

                } else {
                    // restart mech
                    int status = GameData.hasWon();
                    if (status == GameData.PLAYER_1) {
                        broadcast(new CommandFromServer(CommandFromServer.P1_WINS, null));
                        System.out.println(PLAYER_ONE + " wins");
                    } else if (status == GameData.PLAYER_2) {
                        broadcast(new CommandFromServer(CommandFromServer.P2_WINS, null));
                        System.out.println(PLAYER_TWO + " wins");
                    } else {
                        System.out.println("Tie");
                        broadcast(new CommandFromServer(CommandFromServer.TIE, null));
                    }

                    broadcast(new CommandFromServer(CommandFromServer.RESTART, null));

                    CommandFromClient r1 = (CommandFromClient) PLAYER_ONE.getIs().readObject();
                    CommandFromClient r2 = (CommandFromClient) PLAYER_TWO.getIs().readObject();
                    if (r1.getCommand() == r2.getCommand() && r1.getCommand() == CommandFromClient.RESTART) {
                        System.out.println("RESTARTINGGG");
                        broadcast(new CommandFromServer(CommandFromServer.RESTART, null));
                        game = new GameData(); //should be the below one
                        //game = new GameData(new int[6][7], CommandFromServer.P1_TURN, false);
                        game.setTurn(GameData.PLAYER_1);
                        turn = GameData.PLAYER_1;
                    }
                    //PLAYER_ONE.getIs().reset();
                    //PLAYER_TWO.getIs().reset();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            broadcast(new CommandFromServer(CommandFromServer.SHUT_DOWN, null));

            Thread.sleep(5000);
            System.exit(0);
        }
    }

    public static void broadcast(CommandFromServer cfs) throws IOException {
        try {
            PLAYER_ONE.getOs().writeObject(cfs);
        } catch (Exception e) {
            PLAYER_TWO.getOs().writeObject(cfs);
            System.out.println("Disconnect");
        }

        try {
            PLAYER_TWO.getOs().writeObject(cfs);
        } catch (Exception e) {
            PLAYER_ONE.getOs().writeObject(cfs);
            System.out.println("Disconnect");
        }
    }

    public static void switchTurns() {
        if (turn == GameData.PLAYER_1) turn = GameData.PLAYER_2;
        else if (turn == GameData.PLAYER_2) turn = GameData.PLAYER_1;
    }
}