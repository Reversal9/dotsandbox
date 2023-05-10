import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class GameFrame extends JFrame {
    private Connection connection;
    private int PLAYER;
    private static final int GAP = 5;
    public GamePanel panel;
    public GameFrame(String frameName, Connection connection, int PLAYER) {
//    public GameFrame(String frameName) {
        super(frameName);
        this.connection = connection;
        this.PLAYER = PLAYER;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        panel = new GamePanel();
        panel.setLayout(new BorderLayout(GAP,GAP));
        Insets frameInsets = getInsets();
        int frameWidth = panel.getWidth() + (frameInsets.left + frameInsets.right);
        int frameHeight = panel.getHeight() + (frameInsets.top + frameInsets.bottom);
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setLayout(null);
        add(panel);


        pack();
        setVisible(true);

    }

    public void drawEnd(int state) {
        panel.drawEndGame(panel.getGraphics(), state);
    }

    public void drawShutDown() {
        panel.drawShutDown(panel.getGraphics());
    }

    public void drawCountdown(int number) {
        panel.drawCountdown(panel.getGraphics(), number);
    }

    public void drawTurn() {
        panel.drawTurn(panel.getGraphics());
    }

    public void removeTurn() {
        panel.removeTurn(panel.getGraphics());
    }

    public void reset() {
        panel.reset(panel.getGraphics());
    }

    public void drawRestart() {
        panel.drawRestart(panel.getGraphics());
    }

    public void drawRestarted() {
        panel.drawRestarted(panel.getGraphics());
    }

    public void drawWait() {
        panel.drawWait(panel.getGraphics());
    }


    public void drawLine(int[] first, int[] second) {
        panel.drawLine(panel.getGraphics(), first, second);
    }
}
