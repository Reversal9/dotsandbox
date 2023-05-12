import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Arrays;

public class MouseThread implements Runnable {
    private int player;
    private GamePanel panel;
    private Connection con;
    private GameFrame frame;
    private static MouseListener ml;
    int[] clickOne = null;
    int[] clickTwo = null;

    public MouseThread(int player, Connection con, GameFrame frame) {
        this.player = player;
        this.con = con;
        this.frame = frame;
        this.panel = frame.panel;
    }

    @Override
    public void run() {
        try {
            /*if(panel.getData().isFinished()){
                /*try {
                    if(panel.getData().hasWon() == 1)
                        con.getOs().writeObject(new CommandFromServer(CommandFromServer.P1_WINS, null));
                    else if(panel.getData().hasWon() == 2)
                        con.getOs().writeObject(new CommandFromServer(CommandFromServer.P2_WINS, null));
                    else
                        con.getOs().writeObject(new CommandFromServer(CommandFromServer.TIE, null));
                    //con.getOs().writeObject(new CommandFromClient(CommandFromClient.,null));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }*/
            ml = new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (clickOne == null) {
                        clickOne = panel.getCell(e.getPoint());
                        return;
                    }

                    if (clickTwo == null) {
                        if(Arrays.equals(panel.getCell(e.getPoint()), clickOne)) {
                            clickOne = null;
                            return;
                        }
                        clickTwo = panel.getCell(e.getPoint());
                    }


                    //start here
                    //alreaedy have the points
                    System.out.println("Hihihihihih");
                    if(!(clickOne[0] <= -1 || clickOne[1] <= -1 || clickTwo[0] <= -1 || clickTwo[1] <= -1)) {
                        if (Math.abs(clickTwo[1] - clickOne[1]) >= Math.abs(clickTwo[0] - clickOne[0])) {
                            if (clickTwo[1] - clickOne[1] < 0)
                                clickTwo[1] = clickOne[1] - 1;
                            else
                                clickTwo[1] = clickOne[1] + 1;
                            clickTwo[0] = clickOne[0];

                            if (panel.getData().hasRepeated(clickOne, clickTwo)) {
                                clickOne = clickTwo = null;
                                return;
                            }

                            if(clickOne[1] == 0 || clickTwo[1] == 0){
                                if(clickOne[0] == 5)
                                    panel.getData().getSquares(4, 0).setSouth(true);
                                else {
                                    panel.getData().getSquares(clickOne[0], 0).setNorth(true);
                                    System.out.print("jkjbbbbbbskds");
                                }
                            } else if (clickOne[1] == 5 || clickTwo[1] == 5) {
                                System.out.print("jksdjsslslsls");
                                if(clickOne[0] == 5)
                                    panel.getData().getSquares(4, 4).setSouth(true);
                                else {
                                    panel.getData().getSquares(clickOne[0], 4).setNorth(true);

                                }
                            } else if (clickOne[1] == 4 || clickTwo[1] == 4) {
                                if(clickOne[0] == 5)
                                    panel.getData().getSquares(4, 3).setSouth(true);
                                else {
                                    panel.getData().getSquares(clickOne[0], 3).setNorth(true);

                                }
                            } else if (clickOne[1] == 3 || clickTwo[1] == 3) {
                                if(clickOne[0] == 5)
                                    panel.getData().getSquares(4, 2).setSouth(true);
                                else {
                                    panel.getData().getSquares(clickOne[0], 2).setNorth(true);

                                }
                            } else if (clickOne[1] == 2 || clickTwo[1] == 2) {
                                if(clickOne[0] == 5)
                                    panel.getData().getSquares(4, 1).setSouth(true);
                                else {
                                    panel.getData().getSquares(clickOne[0], 1).setNorth(true);

                                }
                            }
                        } else {
                            if (clickTwo[0] - clickOne[0] < 0)
                                clickTwo[0] = clickOne[0] - 1;
                            else
                                clickTwo[0] = clickOne[0] + 1;
                            clickTwo[1] = clickOne[1];

                            if (panel.getData().hasRepeated(clickOne, clickTwo)) {
                                clickOne = clickTwo = null;
                                return;
                            }

                            if(clickOne[0] == 0 || clickTwo[0] == 0){
                                if(clickOne[1] == 5)
                                    panel.getData().getSquares(0, 4).setEast(true);
                                else {
                                    panel.getData().getSquares(0, clickOne[1]).setWest(true);

                                }
                            } else if (clickOne[0] == 5 || clickTwo[0] == 5) {
                                if(clickOne[1] == 5)
                                    panel.getData().getSquares(4, 4).setEast(true);
                                else {
                                    panel.getData().getSquares(4, clickOne[1]).setWest(true);

                                }
                            } else if (clickOne[0] == 4 || clickTwo[0] == 4) {
                                if(clickOne[1] == 5)
                                    panel.getData().getSquares(3, 4).setEast(true);
                                else {
                                    panel.getData().getSquares(3, clickOne[1]).setWest(true);

                                }
                            } else if (clickOne[0] == 3 || clickTwo[0] == 3) {
                                if(clickOne[1] == 5)
                                    panel.getData().getSquares(2, 4).setEast(true);
                                else {
                                    panel.getData().getSquares(2, clickOne[1]).setWest(true);

                                }
                            } else if (clickOne[0] == 2 || clickTwo[0] == 2) {
                                if(clickOne[1] == 5)
                                    panel.getData().getSquares(1, 4).setEast(true);
                                else {
                                    panel.getData().getSquares(1, clickOne[1]).setWest(true);

                                }
                            }
                        }
                        panel.getData().fixSides(player);
                        //end here
                       // panel.getData().updateStates(panel.getData().getSquares());
                        panel.getData().printState();
                        if(player == 0)
                            panel.drawLine(panel.getGraphics(), clickOne, clickTwo, Color.BLACK);
                        else
                            panel.drawLine(panel.getGraphics(), clickOne, clickTwo, Color.RED);
                        try {
                            if (panel.getData().justCompletedBox) {
                                con.getOs().writeObject(new CommandFromClient(CommandFromClient.MOVE, new Move(player, clickOne, clickTwo, true)));
                            } else {
                                con.getOs().writeObject(new CommandFromClient(CommandFromClient.MOVE, new Move(player, clickOne, clickTwo, false)));
                            }
                            System.out.println("Hihihihihih");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }else
                        try {
                            con.getOs().writeObject(new CommandFromClient(CommandFromClient.INVALID,null));
                            System.out.println("Hihihihihih");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    frame.removeMouseListener(ml);
                    frame.removeTurn();
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            };
            frame.addMouseListener(ml);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
