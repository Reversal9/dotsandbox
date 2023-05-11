import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    //region Attributes
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int COLUMN = 6;
    private static final int ROW = 6;
    private static final int STARTING_OFFSET = 100;
    private static final int CIRCLE_SIZE = 25;
    private static final int SPACE = 75;
    private static final int OFFSET = CIRCLE_SIZE / 2;
    private static final int OFFSET2 = 50;
    public GameData data = new GameData();
    //endregion

    public GamePanel() {
        setSize(WIDTH, HEIGHT);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        drawBoard(g);
    }

    public void drawBoard(Graphics g) {
        g.setColor(Color.BLACK);
        for (int x = STARTING_OFFSET; x < WIDTH - STARTING_OFFSET; x += SPACE + CIRCLE_SIZE + OFFSET) {
            for (int y = STARTING_OFFSET; y < HEIGHT - STARTING_OFFSET; y += SPACE + CIRCLE_SIZE + OFFSET) {
                g.fillOval(x + OFFSET, y + OFFSET, CIRCLE_SIZE, CIRCLE_SIZE);
            }
        }
    }

    public void reset(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        drawBoard(g);
    }

    public GameData getData(){
        return data;
    }

    public int[] getCell(Point p) {
        // Implement return select dot
        int col;
        if (p.x <= STARTING_OFFSET - CIRCLE_SIZE * 2 || p.x >= STARTING_OFFSET + (SPACE + CIRCLE_SIZE + OFFSET) * 6 + (CIRCLE_SIZE) * 2) {
            col = -1;
        } else if (p.x <= STARTING_OFFSET + (CIRCLE_SIZE * 2)) {
            col = 0;
        } else if (p.x <= STARTING_OFFSET + (SPACE + CIRCLE_SIZE + OFFSET) + (CIRCLE_SIZE) * 2) {
            col = 1;
        } else if (p.x <= STARTING_OFFSET + (SPACE + CIRCLE_SIZE + OFFSET) * 2 + (CIRCLE_SIZE) * 2) {
            col = 2;
        } else if (p.x <= STARTING_OFFSET + (SPACE + CIRCLE_SIZE + OFFSET) * 3 + (CIRCLE_SIZE) * 2) {
            col = 3;
        } else if (p.x <= STARTING_OFFSET + (SPACE + CIRCLE_SIZE + OFFSET) * 4 + (CIRCLE_SIZE) * 2) {
            col = 4;
        } else {
            col = 5;
        }

        int row;
        if (p.y <= STARTING_OFFSET - CIRCLE_SIZE * 2 || p.y >= STARTING_OFFSET + (SPACE + CIRCLE_SIZE + OFFSET) * 6 + (CIRCLE_SIZE) * 2) {
            row = -2;
        } else if (p.y <= STARTING_OFFSET + (CIRCLE_SIZE * 2) - OFFSET2) {
            row = -1;
        } else if (p.y <= STARTING_OFFSET + (SPACE + CIRCLE_SIZE + OFFSET) + (CIRCLE_SIZE) * 2 - OFFSET2) {
            row = 0;
        } else if (p.y <= STARTING_OFFSET + (SPACE + CIRCLE_SIZE + OFFSET) * 2 + (CIRCLE_SIZE) * 2 - OFFSET2) {
            row = 1;
        } else if (p.y <= STARTING_OFFSET + (SPACE + CIRCLE_SIZE + OFFSET) * 3 + (CIRCLE_SIZE) * 2 - OFFSET2) {
            row = 2;
        } else if (p.y <= STARTING_OFFSET + (SPACE + CIRCLE_SIZE + OFFSET) * 4 + (CIRCLE_SIZE) * 2 - OFFSET2) {
            row = 3;
        } else if (p.y <= STARTING_OFFSET + (SPACE + CIRCLE_SIZE + OFFSET) * 5 + (CIRCLE_SIZE) * 2 - OFFSET2) {
            row = 4;
        } else {
            row = 5;
        }

        return new int[]{row, col};
    }

    public Point getPoint(int x, int y) {
        Point loc = new Point();

        loc.x = STARTING_OFFSET + (SPACE + CIRCLE_SIZE + OFFSET)*x + OFFSET*2 + 1;
        loc.y = STARTING_OFFSET + (SPACE + CIRCLE_SIZE + OFFSET)*y + OFFSET*2 + 1;

        return loc;
    }

    public void drawLine(Graphics g, int[] first, int[] second, Color color) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(10));

        g2.setColor(color);
        Point loc1 = getPoint(first[0], first[1]);
        Point loc2 = getPoint(second[0], second[1]);
        g2.drawLine(loc1.y, loc1.x, loc2.y, loc2.x);
        System.out.println("Line drawn");
    }

    public void drawWait(Graphics g) {
        String text = "Waiting for other player...";
        Font font = new Font(Font.SERIF, Font.PLAIN,  40);
        FontMetrics metrics = g.getFontMetrics(font);
        int x = WIDTH/2 - (metrics.stringWidth(text)) / 2;
        int y = 80;
        g.setFont(font);
        g.drawString(text, x, y);
    }

    public void drawEndGame(Graphics g, int state) {
        String text = "";
        switch (state) {
            case CommandFromServer.P1_WINS:
                text = "Player 1 Wins";
                break;
            case CommandFromServer.P2_WINS:
                text = "Player 2 Wins";
                break;
            case CommandFromServer.TIE:
                text = "Tie Game";
                break;
        }
        Font font = new Font(Font.SERIF, Font.PLAIN,  40);
        FontMetrics metrics = g.getFontMetrics(font);
        int x = WIDTH/2 - (metrics.stringWidth(text)) / 2;
        int y = 60;
        g.setFont(font);
        g.drawString(text, x, y);
    }

    public void drawShutDown(Graphics g) {
        String text = "Disconnected. Shutting Down.";
        Font font = new Font(Font.SERIF, Font.PLAIN,  40);
        FontMetrics metrics = g.getFontMetrics(font);
        int x = WIDTH/2 - (metrics.stringWidth(text)) / 2;
        int y = 80;
        g.setFont(font);
        g.drawString(text, x, y);
    }

    public void drawCountdown(Graphics g, int number) {
        g.setColor(Color.WHITE);
        g.fillRect(640, 40, 60, 60);
        g.setColor(Color.black);
        String text = ""+number;
        Font font = new Font(Font.SERIF, Font.PLAIN,  40);
        FontMetrics metrics = g.getFontMetrics(font);
        int x = WIDTH/2 - (metrics.stringWidth(text)) / 2 + 250;
        int y = 80;
        g.setFont(font);
        g.drawString(text, x, y);
    }

    public void drawTurn(Graphics g) {
        g.setColor(Color.black);
        String text = "You Turn";
        Font font = new Font(Font.SERIF, Font.PLAIN,  40);
        FontMetrics metrics = g.getFontMetrics(font);
        int x = WIDTH/2 - (metrics.stringWidth(text)) / 2;
        int y = 80;
        g.setFont(font);
        g.drawString(text, x, y);
    }

    public void drawRestart(Graphics g) {
        String text = "Press anywhere to restart";
        Font font = new Font(Font.SERIF, Font.PLAIN,  20);
        FontMetrics metrics = g.getFontMetrics(font);
        int x = WIDTH/2 - (metrics.stringWidth(text)) / 2;
        int y = 80;
        g.setFont(font);
        g.drawString(text, x, y);
    }

    public void drawRestarted(Graphics g) {
        String text = "Restarting.. 1/2";
        Font font = new Font(Font.SERIF, Font.PLAIN,  20);
        FontMetrics metrics = g.getFontMetrics(font);
        int x = WIDTH/2 - (metrics.stringWidth(text)) / 2;
        int y = 95;
        g.setFont(font);
        g.drawString(text, x, y);
    }

    public void removeTurn(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0,0,WIDTH,100);
    }
}
