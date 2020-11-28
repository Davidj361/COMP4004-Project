package Rummykub;

import java.io.IOException;
import java.util.Scanner;

public class App {
    private boolean readyUp = false;
    private Server server = new Server();

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
                System.out.println("What is your IP address?");
                ipAddress = scanner.nextLine().toLowerCase();
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
                break;
            } else if (hostOrConnect.compareTo("2") == 0) {
                System.out.println("What is your IP address?");
                ipAddress = scanner.nextLine().toLowerCase();
                System.out.println("What port number do you want to connect to?");
                port = scanner.nextInt();
                break;
            } else {
                System.out.println("invalid input");
            }
        }

    }

//    public void readyUp (int numOfPlayers) {
//        if (numOfPlayers == server.getNumClients()) {
//            readyUp = true;
//        }
//    }


    }

