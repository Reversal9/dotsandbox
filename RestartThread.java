import com.sun.security.jgss.GSSUtil;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RestartThread implements Runnable {
    private int player;
    private Connection con;
    private GameFrame frame;
    private static MouseListener ml;
    public RestartThread(int player, Connection con, GameFrame frame) {
        this.player = player;
        this.con = con;
        this.frame = frame;
    }

    @Override
    public void run() {
        try {
            System.out.println("Moving moving");
            ml = new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    try {
                        con.getOs().writeObject(new CommandFromClient(CommandFromClient.RESTART, null));
                        System.out.println("RESTARTINGGGG");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    frame.drawRestarted();
                    frame.removeMouseListener(ml);
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
