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
                        if (game.isFinished()) break;
                        //P1
                        broadcast(new CommandFromServer(CommandFromServer.P1_TURN, null));
                        CommandFromClient cfc = (CommandFromClient) PLAYER_ONE.getIs().readObject();

                        if (cfc.getCommand() == CommandFromClient.MOVE) {
                            Move move = (Move) cfc.getData();


                            /*if (!game.dropAt(move.getColumn(), Game.PLAYER_1)) {
                                continue;
                            }*/

                            System.out.println("Hihihihihih");
                            if(!(move.getFirst()[0] <= -1 || move.getFirst()[1] <= -1 || move.getSecond()[0] <= -1 || move.getSecond()[1] <= -1)) {
                                if (Math.abs(move.getSecond()[1] - move.getFirst()[1]) >= Math.abs(move.getSecond()[0] - move.getFirst()[0])) {
                                    if (move.getSecond()[1] - move.getFirst()[1] < 0)
                                        move.getSecond()[1] = move.getFirst()[1] - 1;
                                    else
                                        move.getSecond()[1] = move.getFirst()[1] + 1;
                                    move.getSecond()[0] = move.getFirst()[0];

                                    if (move.getFirst()[1] == 0 || move.getSecond()[1] == 0) {
                                        if (move.getFirst()[0] == 5)
                                            game.getSquares(4, 0).setSouth(true);
                                        else {
                                            game.getSquares(move.getFirst()[0], 0).setNorth(true);
                                            System.out.print("jkjbbbbbbskds");
                                            ;
                                        }
                                    } else if (move.getFirst()[1] == 5 || move.getSecond()[1] == 5) {
                                        System.out.print("jksdjsslslsls");
                                        ;
                                        if (move.getFirst()[0] == 5)
                                            game.getSquares(4, 4).setSouth(true);
                                        else {
                                            game.getSquares(move.getFirst()[0], 4).setNorth(true);

                                        }
                                    } else if (move.getFirst()[1] == 4 || move.getSecond()[1] == 4) {
                                        if (move.getFirst()[0] == 5)
                                            game.getSquares(4, 3).setSouth(true);
                                        else {
                                            game.getSquares(move.getFirst()[0], 3).setNorth(true);

                                        }
                                    } else if (move.getFirst()[1] == 3 || move.getSecond()[1] == 3) {
                                        if (move.getFirst()[0] == 5)
                                            game.getSquares(4, 2).setSouth(true);
                                        else {
                                            game.getSquares(move.getFirst()[0], 2).setNorth(true);

                                        }
                                    } else if (move.getFirst()[1] == 2 || move.getSecond()[1] == 2) {
                                        if (move.getFirst()[0] == 5)
                                            game.getSquares(4, 1).setSouth(true);
                                        else {
                                            game.getSquares(move.getFirst()[0], 1).setNorth(true);

                                        }
                                    }
                                } else {
                                    if (move.getSecond()[0] - move.getFirst()[0] < 0)
                                        move.getSecond()[0] = move.getFirst()[0] - 1;
                                    else
                                        move.getSecond()[0] = move.getFirst()[0] + 1;
                                    move.getSecond()[1] = move.getFirst()[1];

                                    if (move.getFirst()[0] == 0 || move.getSecond()[0] == 0) {
                                        if (move.getFirst()[1] == 5)
                                            game.getSquares(0, 4).setEast(true);
                                        else {
                                            game.getSquares(0, move.getFirst()[1]).setWest(true);

                                        }
                                    } else if (move.getFirst()[0] == 5 || move.getSecond()[0] == 5) {
                                        if (move.getFirst()[1] == 5)
                                            game.getSquares(4, 4).setEast(true);
                                        else {
                                            game.getSquares(4, move.getFirst()[1]).setWest(true);

                                        }
                                    } else if (move.getFirst()[0] == 4 || move.getSecond()[0] == 4) {
                                        if (move.getFirst()[1] == 5)
                                            game.getSquares(3, 4).setEast(true);
                                        else {
                                            game.getSquares(3, move.getFirst()[1]).setWest(true);

                                        }
                                    } else if (move.getFirst()[0] == 3 || move.getSecond()[0] == 3) {
                                        if (move.getFirst()[1] == 5)
                                            game.getSquares(2, 4).setEast(true);
                                        else {
                                            game.getSquares(2, move.getFirst()[1]).setWest(true);

                                        }
                                    } else if (move.getFirst()[0] == 2 || move.getSecond()[0] == 2) {
                                        if (move.getFirst()[1] == 5)
                                            game.getSquares(1, 4).setEast(true);
                                        else {
                                            game.getSquares(1, move.getFirst()[1]).setWest(true);

                                        }
                                    }
                                }
                                game.fixSides(GameData.PLAYER_1);

                            }
                            game.printState();
                            broadcast(new CommandFromServer(CommandFromServer.MOVE, new Move(GameData.PLAYER_1, move.getFirst(), move.getSecond(), false)));
//                            if (!move.getAgain())
                            if (!game.justCompletedBox) {
                                switchTurns();
                                if (game.isFinished()) break;
                            }else {
                                game.justCompletedBox = false;
                                if (game.isFinished()) break;
                            }
                            if (game.isFinished()) break;
                        } else {
                            continue;
                        }
                        //PLAYER_ONE.getIs().reset();
                        System.out.println(Arrays.deepToString(game.getGrid()));
                        System.out.println(game.isFinished());
                        if (game.isFinished()) break;
                    } else if (turn == GameData.PLAYER_2) {
                        System.out.println("P2 Turn");
                        if (game.isFinished()) break;
                        //P2
                        broadcast(new CommandFromServer(CommandFromServer.P2_TURN, null));
                        CommandFromClient cfc = (CommandFromClient) PLAYER_TWO.getIs().readObject();

                        if (cfc.getCommand() == CommandFromClient.MOVE) {
                            Move move = (Move) cfc.getData();
                            /*if (!game.dropAt(move.getColumn(), Game.PLAYER_2)) {
                                continue;
                            }*/

                            System.out.println("Hihihihihih");
                            if(!(move.getFirst()[0] <= -1 || move.getFirst()[1] <= -1 || move.getSecond()[0] <= -1 || move.getSecond()[1] <= -1)) {
                                if (Math.abs(move.getSecond()[1] - move.getFirst()[1]) >= Math.abs(move.getSecond()[0] - move.getFirst()[0])) {
                                    if (move.getSecond()[1] - move.getFirst()[1] < 0)
                                        move.getSecond()[1] = move.getFirst()[1] - 1;
                                    else
                                        move.getSecond()[1] = move.getFirst()[1] + 1;
                                    move.getSecond()[0] = move.getFirst()[0];

                                    if (move.getFirst()[1] == 0 || move.getSecond()[1] == 0) {
                                        if (move.getFirst()[0] == 5)
                                            game.getSquares(4, 0).setSouth(true);
                                        else {
                                            game.getSquares(move.getFirst()[0], 0).setNorth(true);
                                            System.out.print("jkjbbbbbbskds");
                                            ;
                                        }
                                    } else if (move.getFirst()[1] == 5 || move.getSecond()[1] == 5) {
                                        System.out.print("jksdjsslslsls");
                                        ;
                                        if (move.getFirst()[0] == 5)
                                            game.getSquares(4, 4).setSouth(true);
                                        else {
                                            game.getSquares(move.getFirst()[0], 4).setNorth(true);

                                        }
                                    } else if (move.getFirst()[1] == 4 || move.getSecond()[1] == 4) {
                                        if (move.getFirst()[0] == 5)
                                            game.getSquares(4, 3).setSouth(true);
                                        else {
                                            game.getSquares(move.getFirst()[0], 3).setNorth(true);

                                        }
                                    } else if (move.getFirst()[1] == 3 || move.getSecond()[1] == 3) {
                                        if (move.getFirst()[0] == 5)
                                            game.getSquares(4, 2).setSouth(true);
                                        else {
                                            game.getSquares(move.getFirst()[0], 2).setNorth(true);

                                        }
                                    } else if (move.getFirst()[1] == 2 || move.getSecond()[1] == 2) {
                                        if (move.getFirst()[0] == 5)
                                            game.getSquares(4, 1).setSouth(true);
                                        else {
                                            game.getSquares(move.getFirst()[0], 1).setNorth(true);

                                        }
                                    }
                                } else {
                                    if (move.getSecond()[0] - move.getFirst()[0] < 0)
                                        move.getSecond()[0] = move.getFirst()[0] - 1;
                                    else
                                        move.getSecond()[0] = move.getFirst()[0] + 1;
                                    move.getSecond()[1] = move.getFirst()[1];

                                    if (move.getFirst()[0] == 0 || move.getSecond()[0] == 0) {
                                        if (move.getFirst()[1] == 5)
                                            game.getSquares(0, 4).setEast(true);
                                        else {
                                            game.getSquares(0, move.getFirst()[1]).setWest(true);

                                        }
                                    } else if (move.getFirst()[0] == 5 || move.getSecond()[0] == 5) {
                                        if (move.getFirst()[1] == 5)
                                            game.getSquares(4, 4).setEast(true);
                                        else {
                                            game.getSquares(4, move.getFirst()[1]).setWest(true);

                                        }
                                    } else if (move.getFirst()[0] == 4 || move.getSecond()[0] == 4) {
                                        if (move.getFirst()[1] == 5)
                                            game.getSquares(3, 4).setEast(true);
                                        else {
                                            game.getSquares(3, move.getFirst()[1]).setWest(true);

                                        }
                                    } else if (move.getFirst()[0] == 3 || move.getSecond()[0] == 3) {
                                        if (move.getFirst()[1] == 5)
                                            game.getSquares(2, 4).setEast(true);
                                        else {
                                            game.getSquares(2, move.getFirst()[1]).setWest(true);

                                        }
                                    } else if (move.getFirst()[0] == 2 || move.getSecond()[0] == 2) {
                                        if (move.getFirst()[1] == 5)
                                            game.getSquares(1, 4).setEast(true);
                                        else {
                                            game.getSquares(1, move.getFirst()[1]).setWest(true);

                                        }
                                    }
                                }
                                game.fixSides(GameData.PLAYER_2);
                            }
                            game.printState();
                            broadcast(new CommandFromServer(CommandFromServer.MOVE, new Move(GameData.PLAYER_2, move.getFirst(), move.getSecond(), false)));

                            if (!game.justCompletedBox) {
                                switchTurns();
                                if (game.isFinished()) break;

                            }else {
                                game.justCompletedBox = false;
                                if (game.isFinished()) break;

                            }
//                            if (!move.getAgain())
                            if (game.isFinished()) break;
                        }
                        //PLAYER_TWO.getIs().reset();
                        System.out.println(Arrays.deepToString(game.getGrid()));
                        System.out.println(game.isFinished());
                        if (game.isFinished()) break;
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

                    broadcast(new CommandFromServer(CommandFromServer.REQUEST_RESTART, null));
                    System.out.println("reset");
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