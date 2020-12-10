package Rummikub;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client extends Thread implements AutoCloseable {

    String hostName = "127.0.0.1";
    int port = 27015;
    String name;
    Socket socket;
    boolean testing = false;
    String lastResponse = "";

    Client() {}
    Client(String name) { this.name = name; }
    Client(String name, String hostName, int port) {
        this(name, hostName, port, false);
    }
    Client(String name, String hostName, int port, boolean b) {
        this.name = name;
        this.hostName = hostName;
        this.port = port;
        testing = b;
    }

    public void run() {
        System.out.println("Client Running");
        while (isOpen()) {
            try {
                String str = read();
                if (str != null && str != "") {
                    if (testing)
                        lastResponse = str;
                    System.out.print(str);
                }
            } catch (SocketException e) {
                // Server closed
                try {
                    close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean connect() throws IOException {
        if (!isOpen()) {
            try {
                socket = new Socket(hostName, port);
            } catch (SocketException e) {
                System.out.println("Client could not connect.");
                return false;
            }
            System.out.println("Client connected!");
            return true;
        }
        return false;
    }

    public boolean disconnect() throws IOException {
        System.out.println("Disconnected");
        return disconnect(false);
    }

    private boolean disconnect(boolean force) throws IOException {
        if (force || isOpen()) {
            socket.close();
            socket = null;
            return true;
        }
        return false;
    }

    // Apparently client socket doesn't close?
    public boolean isOpen() {
        return socket != null;
    }

    public void close() throws IOException {
        disconnect();
    }


    public boolean send(String str) throws IOException {
        if (!isOpen() || socket == null || str.equals(""))
            return false;
        ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());
        dOut.writeUTF(str);
        dOut.flush();
        return true;
    }


    public String read() throws IOException {
        String str = "";
        try {
            ObjectInputStream dIn = new ObjectInputStream(socket.getInputStream());
            str = dIn.readUTF();
        } catch (EOFException e) { // Server most likely closed
            System.out.println("Error while reading from server. Server most likely closed.");
            System.out.println("Closing connection...");
            disconnect();
        }
        return str;
    }

    public void flush() throws IOException {
        InputStream is = socket.getInputStream();
        // ObjectInputStream dIn = new ObjectInputStream(socket.getInputStream());
        while (is.available() > 0)
            is.read();
    }

    public String getPlayerName() { return name; }

    public void sendName() throws IOException {
        String out = "Name: "+name; // Needed to avoid double send?
        out.trim();
        send(out);
    }

}