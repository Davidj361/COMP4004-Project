package Rummikub;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ServerTester extends MyTestCase {

    // NOTE: Tests don't to be running in order as coded
    // IMPORTANT NOTE: Sockets must be manually closed in each test because Java doesn't close sockets when objects deconstruct.
    //				   So you need to use AutoCloseable try blocks to deal with this.


    @Test
    public void testExists() throws IOException {
        try (Server server = new Server()) {
            assertNotNull(server); // Does the server class exist and is it instantiated?
        }
    }


    // Is the server open and ready for connections?
    @Test
    public void testStart() throws IOException {
        try (Server server = new Server()) {
            assertTrue(server.host());
            assertTrue(server.isOpen());
        }
    }


    // Previous tests running should have an already running server
    @Test
    public void testRestart() throws IOException {
        try (Server server = new Server()) {
            assertTrue(server.host());
            server.start();

            assertTrue(server.isOpen());
            assertTrue(server.isBound());
            assertFalse(server.isClosed());

            assertTrue(server.stopHost());
            assertTrue(server.host());
        }
    }

    // Check command loop
    @Test
    public void testCommandLoop()  throws IOException, InterruptedException {
        // Server(String name, int port, int numPlayers, boolean b)
        try (Server server = new Server("testCommandLoop", 27015, 2)) {
            assertTrue(server.host());
            server.start();
            // Client(name, ip, port, testing)
            Client client = new Client("the client", "localhost", 27015, true);
            assertTrue(client.connect());
            client.start();
            client.sendName();

            while (server.getNamesSet().size() != server.getMaxClients()+1) // Add host's name
                Thread.sleep(10);
            assertEquals(server.getNumClients(), server.getMaxClients());
            Game game = new Game(server);
            client.send("asdf");

            String recv = "";
            while (!recv.equals("It is not your turn yet.\n")) {
                recv = client.lastResponse;
                Thread.sleep(10);
            }

            // Are the server's client sockets still open/connected to server when stopped?
            assertTrue(server.stopHost());
            for (int i=0; i<server.getNumClients(); i++) {
                assertFalse(server.clients.get(i).isConnected());
                assertTrue(server.clients.get(i).isClosed());
            }
        }
    }

}