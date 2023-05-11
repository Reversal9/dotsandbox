import com.sun.security.jgss.GSSUtil;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
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
                    System.out.println("Hihihihihih");
                    if(!(clickOne[0] <= -1 || clickOne[1] <= -1 || clickTwo[0] <= -1 || clickTwo[1] <= -1)) {
                        if (Math.abs(clickTwo[1] - clickOne[1]) >= Math.abs(clickTwo[0] - clickOne[0])) {
                            if (clickTwo[1] - clickOne[1] < 0)
                                clickTwo[1] = clickOne[1] - 1;
                            else
                                clickTwo[1] = clickOne[1] + 1;
                            clickTwo[0] = clickOne[0];
                        } else {
                            if (clickTwo[0] - clickOne[0] < 0)
                                clickTwo[0] = clickOne[0] - 1;
                            else
                                clickTwo[0] = clickOne[0] + 1;
                            clickTwo[1] = clickOne[1];
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
                            }
                            System.out.println("WEST: " + panel.getData().getSquares(0, 0).getWest() );
                            System.out.println("EAST: " + panel.getData().getSquares(0, 0).getEast() );
                            //data.getSquares(0, 0).setNorth(true);
                        }
                        //clickOne[0] -= 1;
                        //clickTwo[0] -= 1;
                        if(player == 0)
                            panel.drawLine(panel.getGraphics(), clickOne, clickTwo, Color.BLACK);
                        else
                            panel.drawLine(panel.getGraphics(), clickOne, clickTwo, Color.RED);
                        try {
                            con.getOs().writeObject(new CommandFromClient(CommandFromClient.MOVE, new Move(player, clickOne, clickTwo)));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
