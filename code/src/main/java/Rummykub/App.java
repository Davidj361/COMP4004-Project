package Rummykub;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class App {
    private boolean readyUp = false;

    public static void main (String [] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String ipAddress = "";
        int port;
        int numberOfPlayers;
        String hostOrConnect = "";

        System.out.println("What is you name?");
        String name = scanner.nextLine().toLowerCase();
        while (true) {
            System.out.println("Would you like to host a new game or connect to a host?");
            System.out.println("1: Host, 2: Connect");
            hostOrConnect = scanner.nextLine().toLowerCase();
            if (hostOrConnect.compareTo("1") == 0) {
                System.out.println("What port number do you want to host game on?");
                port = scanner.nextInt();
                while (true) {
                    System.out.println("How many players would you like to have in your game");
                    System.out.println("Note: A Game can have a minimum of 2 players and maximum of 4 players");
                    numberOfPlayers = scanner.nextInt();
                    if (numberOfPlayers < 2 || numberOfPlayers > 4) {
                        System.out.println("Invalid input. number of players must be between 2 and 4");
                    }
                    else {
                        break;
                    }
                }
                host();
                break;
            } else if (hostOrConnect.compareTo("2") == 0) {
                System.out.println("What is your IP address?");
                ipAddress = scanner.nextLine().toLowerCase();
                System.out.println("What port number do you want to connect to?");
                port = scanner.nextInt();
                client();
                break;
            } else {
                System.out.println("invalid input");
            }
        }

    }

    public void readyUp (int numOfPlayers, Server server) {
        if (numOfPlayers == server.getNumClients()) {
            readyUp = true;
        }
    }

    static private void host() throws IOException, InterruptedException {
        try (Server server = new Server()) {
            // Setup network
            if (!server.host()) {
                System.out.println("Could not host.");
                return;
            }
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(System.in);
            server.start();
            while (server.clientsConnected != Server.maxClients)
                Thread.sleep(10);

            Game game = new Game(server);
            game.launch();

            while (true) {
                String input = scanner.nextLine().toLowerCase();
                server.command(input);
                Thread.sleep(10);
            }
        }
    }

    static private void client() throws UnknownHostException, IOException, InterruptedException {
        // Setup network
        try (Client client = new Client()) {
            if (!client.connect()) {
                System.out.println("Could not connect.");
                return;
            }
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(System.in);
            client.start();
            while (true) {
                String input = scanner.nextLine().toLowerCase();
                client.send(input);
                Thread.sleep(10);
            }
        }
    }

    }

