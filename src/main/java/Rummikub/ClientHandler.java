package Rummikub;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler extends Thread {

    final private Server server;
    private Socket socket;
    final private int clientId;
    private String name = "unnamed";
    boolean testing;

    ClientHandler(Server s, Socket ss, int i) {
        this(s, ss, i, false);
    }
    ClientHandler(Server s, Socket ss, int i, boolean b) {
        if (s == null)
            throw new IllegalArgumentException();
        server = s;
        socket = ss;
        clientId = i;
        testing = b;
    }

    public void run() {
        while (isOpen())
        {
            try {
                String str = read();
                if (str != null) {
                    if (str.contains("Name: ")) {
                        name = str.substring(6); // Length of "Name: "
                        server.print("Client " + clientId + " is named as: " + name+"\n");
                        continue;
                    }
                    if (testing)
                        System.out.println(str+"\n");
                    else
                        server.command(clientId, str);
                }
            }
            catch (EOFException | SocketException e) {
                // Not a big deal
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean close() throws IOException {
        if (!socket.isClosed()) {
            socket.close();
            socket = null;
            return true;
        }
        return false;
    }

    public boolean isOpen() {
        return socket != null && !socket.isClosed();
    }

    public boolean send(String str) {
        if (socket == null || socket.isClosed())
            return false;
        try {
            ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());
            dOut.writeUTF(str);
            dOut.flush();
        } catch (IOException e) {
            System.out.print("ClientHandler.send(..) is unable to send to clientID: "+clientId);
            System.out.println(", Player name is: "+name);
            e.printStackTrace();
        }
        return true;
    }

    public String read() throws IOException {
        String str;
        ObjectInputStream dIn = new ObjectInputStream(socket.getInputStream());
        str = dIn.readUTF();
        return str;
    }

    public void reset() throws IOException {
        ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());
        dOut.reset();
    }

    public boolean isConnected() {
        if (socket == null)
            return false;
        return socket.isConnected();
    }

    public boolean isClosed() {
        if (socket == null)
            return true;
        return socket.isClosed();
    }

    public String getPlayerName() { return name; }

}

