package Rummikub;

import java.io.IOException;
import java.util.Scanner;

public class App {

    boolean testing = false;
    String name;
    int port;
    int state; // 0 = undecided, 1 = host, 2 = client
    String ip;
    int numberOfPlayers;
    int gameEndingScore = -1;
    Game game;
    Server server;
    Client client;

    public App() {}
    public App(boolean testing) { this.testing = testing; }

    public static void main (String [] args) throws IOException, InterruptedException {
        new App().run();
    }

    // A magic way to deal with static functions and non static functions+variables
    public void run() throws IOException, InterruptedException {
        System.out.println("Welcome to Rummikub!");
        askForName();
        askForPort();
        askForHostOrClient();
    }

    public void askForName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your player name?");
        name = scanner.nextLine();
        if (name.equals("")) {
            name = "NoName";
            System.out.println(name);
        }
    }

    public void askForPort() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What port number do you want to host/connect on?");
        String prt = scanner.nextLine();
        port = 27015;
        if (prt.equals(""))
            System.out.println(port);
        else
            port = Integer.parseInt(prt);
    }

    public void askForHostOrClient() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        state = 0;
        while (state == 0) {
            System.out.println("Would you like to host a new game and play or connect to a host and play?");
            System.out.println("Type '1' for Host or '2' for Connect");
            int hostOrConnect = scanner.nextInt();
            if (hostOrConnect == 1) {
                state = 1;
                host();
            } else if (hostOrConnect == 2) {
                state = 2;
                client();
            } else
                System.out.println("invalid input");
        }
    }


    // Host the game and play it at the same time
    public boolean host() throws IOException, InterruptedException {
        if (!testing) {
            askHowManyPlayers();
            askGameEndingScore();
            startHost();
        }
        while (server.getNamesSet().size() != server.getMaxClients()+1) // Add host's name
            Thread.sleep(10);

        game = new Game(server, testing);
        // There's already a default value in Game
        // so only if a player specifies a score then it sets a new one
        if (gameEndingScore != -1)
            game.setGameEndingScore(gameEndingScore);
        game.startText(); // Tells everyone the game is starting
        Scanner scanner = new Scanner(System.in);
        // Command loop
        while (!testing) {
            String input = scanner.nextLine().toLowerCase();
            server.command(input);
            Thread.sleep(10);
        }
        return true;
    }

    public Server startHost() throws IOException {
        server = new Server(name, port, numberOfPlayers);
        if (!server.host()) {
            System.out.println("Could not host.");
            return null;
        }
        server.start();
        return server;
    }

    public void askHowManyPlayers() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("How many players would you like to have in your game?");
            System.out.println("Note: A Game can have a minimum of 2 players and maximum of 4 players");
            numberOfPlayers = scanner.nextInt();
            if (numberOfPlayers < 2 || numberOfPlayers > 4)
                System.out.println("Invalid input. number of players must be between 2 and 4");
            else
                break;
        }
        scanner.nextLine(); // Have to manually call due to some bug
    }

    public void askGameEndingScore() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("At what number of points should the game end at?");
            String tmp = scanner.nextLine();
            if (tmp.equals(""))
                break;
            int i = Integer.parseInt(tmp);
            if (i <= 0)
                System.out.println("Specified score must be above 0");
            else {
                gameEndingScore = i;
                break;
            }
        }
    }

    // Connect to a host and play the game
    public boolean client() throws IOException, InterruptedException {
        if (!testing)
            askForIP();
        // Setup network
        client = new Client(name, ip, port);
        if (!client.connect())
            return false;
        if (!testing)
            client.start();
        client.sendName(); // Tell the server the client's name
        Scanner scanner = new Scanner(System.in);
        // Command loop
        while (!testing) {
            String input = scanner.nextLine().toLowerCase();
            client.send(input);
            Thread.sleep(10);
        }
        return true;
    }

    public void askForIP() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is the IP/hostname that you wish to connect to?");
        ip = scanner.nextLine().toLowerCase();
        if (ip.equals("")) {
            ip = "127.0.0.1";
            System.out.println(ip);
        }
    }

}

