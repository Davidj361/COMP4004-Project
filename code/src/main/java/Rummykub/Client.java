package Rummykub;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client extends Thread implements AutoCloseable {

    String hostName = "127.0.0.1";
    static int port = 27015;
    Socket socket;

    public void run() {
        System.out.println("Client Running");
        while (isOpen()) {
            try {
                String str = read();
                if (str != null)
                    System.out.print(str);
            } catch (SocketException e) {
                // Server closed
                try {
                    close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public boolean connect() throws UnknownHostException, IOException {
        if (!isOpen()) {
            try {
                socket = new Socket(hostName, port);
            }catch (SocketException e) {
                System.out.println("Client could not connect.");
            }
            System.out.println("Client connected!");
            return true;
        }
        return false;
    }

    public boolean disconnect() throws IOException {
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
        if (!isOpen() || socket == null)
            return false;
        ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());
        dOut.writeUTF(str);
        dOut.flush();
        return true;
    }


    public String read() throws IOException {
        String str;
        ObjectInputStream dIn = new ObjectInputStream(socket.getInputStream());
        str = dIn.readUTF();
        return str;
    }

    public void flush() throws IOException {
        InputStream is = socket.getInputStream();
        // ObjectInputStream dIn = new ObjectInputStream(socket.getInputStream());
        while (is.available() > 0)
            is.read();
    }

}
