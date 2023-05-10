import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionThread implements Runnable {
    @Override
    public void run() {
        try {
            while (true) {
                if (Server.serverSocket == null) return;

                if (Server.PLAYER_ONE == null || Server.PLAYER_ONE.getSocket().isClosed()) {
                    Socket socket1 = Server.serverSocket.accept();

                    ObjectOutputStream xos = new ObjectOutputStream(socket1.getOutputStream());
                    ObjectInputStream xis = new ObjectInputStream(socket1.getInputStream());

                    xos.writeObject(new CommandFromServer(CommandFromServer.CONNECTED_AS_P1, null));
                    Server.PLAYER_ONE = new Connection(socket1, xis, xos);
                    System.out.println("Player 1 has connected");
                }

                if (Server.PLAYER_TWO == null || Server.PLAYER_TWO.getSocket().isClosed()) {
                    Socket socket2 = Server.serverSocket.accept();

                    ObjectOutputStream oos = new ObjectOutputStream(socket2.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(socket2.getInputStream());

                    oos.writeObject(new CommandFromServer(CommandFromServer.CONNECTED_AS_P2, null));
                    Server.PLAYER_TWO = new Connection(socket2, ois, oos);
                    System.out.println("Player 2 has connected");
                }

                Thread.sleep(100);
            }
        } catch (IOException e) {
            System.out.println("Disconnected");
            run();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
