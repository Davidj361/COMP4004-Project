package Rummikub;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class App {
    private boolean readyUp = false;

    public static void main (String [] args) throws IOException, InterruptedException {
        System.out.println("Welcome to Rummikub!");
        // Print intro.txt
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is your player name?");
        String name = scanner.nextLine();
        int state = 0;
        while (state == 0) {
            System.out.println("Would you like to host a new game or connect to a host?");
            System.out.println("1: Host, 2: Connect");
            int hostOrConnect = scanner.nextInt();
            if (hostOrConnect == 1) {
                state = 1;
                host(name);
            } else if (hostOrConnect == 2) {
                state = 2;
                client(name);
            } else {
                System.out.println("invalid input");
            }
        }
    }


    // Host the game and play it at the same time
    private static void host(String name) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What port number do you want to host game on?");
        int port = scanner.nextInt();
        int numberOfPlayers;
        while (true) {
            System.out.println("How many players would you like to have in your game?");
            System.out.println("Note: A Game can have a minimum of 2 players and maximum of 4 players");
           numberOfPlayers = scanner.nextInt();
            if (numberOfPlayers < 2 || numberOfPlayers > 4) {
                System.out.println("Invalid input. number of players must be between 2 and 4");
            }
            else
                break;
        }

        try (Server server = new Server(name, port, numberOfPlayers)) {
            // Setup network
            if (!server.host()) {
                System.out.println("Could not host.");
                return;
            }
            scanner = new Scanner(System.in);
            server.start();
            while (server.getNamesSet().size() != server.getMaxClients()+1) // Add host's name
                Thread.sleep(10);

            Game game = new Game(server);
            // Command loop
            while (true) {
                String input = scanner.nextLine().toLowerCase();
                server.command(input);
                Thread.sleep(10);
            }
        }
    }

    // Connect to a host and play the game
    private static void client(String name) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is the IP/hostname that you wish to connect to?");
        String ip = scanner.nextLine().toLowerCase();
        System.out.println("What port number do you want to connect to?");
        int port = scanner.nextInt();
        // Setup network
        try (Client client = new Client(name, ip, port)) {
            if (!client.connect())
                return;
            client.start();
            client.sendName(); // Tell the server the client's name
            // Command loop
            while (true) {
                String input = scanner.nextLine().toLowerCase();
                client.send(input);
                Thread.sleep(10);
            }
        }
    }

}
