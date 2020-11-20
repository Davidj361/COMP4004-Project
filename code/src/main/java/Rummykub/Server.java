package Rummykub;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class Server extends Thread implements AutoCloseable {

    final static int maxClients = 2;
    static int port = 27015;
    ServerSocket socket;
    int clientsConnected = 0;
    ClientHandler[] clients = new ClientHandler[maxClients];
    Game game;
    boolean testing;

    Server() {
        this(false);
    }
    Server(boolean b) {
        testing = b;
    }

    public void run() {
        System.out.println("Server Running");
        System.out.println("Waiting for clients to connect...");
        while (isOpen()) {
            try {
                accept();
            } catch (SocketException e) {
                // Not a big deal
                System.out.println("Server stopped");
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

    public boolean accept() throws IOException {
        if (!isOpen() || clientsConnected == maxClients)
            return false;
        Socket s = socket.accept();
        if (socket != null && s != null) {
            clients[clientsConnected] = new ClientHandler(this, s, clientsConnected, testing);
            clients[clientsConnected].start();
            print("Host: Client " + clientsConnected + " connected.\n");
            clientsConnected++;
            return true;
        }
        return false;
    }

    public boolean host() throws IOException {
        if (!isOpen()) {
            socket = new ServerSocket(port);
            return true;
        }
        return false;
    }

    public boolean stopHost() throws IOException {
        return stopHost(false);
    }

    private boolean stopHost(boolean force) throws IOException {
        if (force || isOpen()) {
            for (int i=0; i<clientsConnected; i++) {
                if (clients[i] != null)
                    clients[i].close();
            }
            socket.close();
            socket = null;
            clientsConnected = 0;
            return true;
        }
        return false;
    }

    public boolean isOpen() {
        return socket != null && !socket.isClosed();
    }

    public void close() throws IOException {
        stopHost();
    }

    public boolean send(final int iClient, String str) throws IOException {
        if (clients[iClient] == null)
            throw new IllegalStateException();
        return clients[iClient].send(str);
    }

    public String read(final int iClient) throws IOException {
        if (clients[iClient] == null)
            throw new IllegalStateException();
        return clients[iClient].read();
    }

    public void print(String str) {
        System.out.print(str);
        for (int i=0; i<clientsConnected; i++) {
            try {
                send(i, str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

	/*
	public void reset() throws IOException {
		for (int i=0; i<clientsConnected; i++)
			clients[i].reset();
	}
	*/

    /* Working with Game class from Pirates

    // When the host asks for a command
    public boolean command(String str) throws IOException {
        if (!commHelper(0, str)) {
            System.out.print("It is not your turn yet.\n");
            return false;
        }
        return true;
    }

    // When client asks for a command
    public boolean command(int clientId, String str) throws IOException {
        final int player = clientId+1;
        if (!commHelper(player, str)) {
            send(clientId, "It is not your turn yet.\n");
            return false;
        }
        return true;
    }


    private boolean commHelper(int player, String str) {
        if (!game.playerTurn(player))
            return false;
        return game.command(player, str);
    }
    */

}
