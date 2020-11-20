package Rummykub;

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
            assertTrue(server.socket.isBound());
            assertFalse(server.socket.isClosed());

            assertTrue(server.stopHost());
            assertTrue(server.host());
        }
    }


    // Wait for 2 players to connect, display they connect
    @Test
    public void test2PlayersConnect() throws IOException, InterruptedException {
        try (Server server = new Server(true)) {
            assertTrue(server.host());
            server.start();
            Client[] clients = new Client[2];
            for (int i = 0; i < Server.maxClients; i++) {
                clients[i] = new Client();
                assertTrue(clients[i].connect());
            }
            while (server.clientsConnected != Server.maxClients)
                //noinspection BusyWait
                Thread.sleep(10);
            assertEquals(server.clientsConnected, Server.maxClients);

            // Are the server's client sockets still open/connected to server when stopped?
            assertTrue(server.stopHost());
            for (int i=0; i<server.clientsConnected; i++) {
                assertFalse(server.clients[i].isConnected());
                assertTrue(server.clients[i].isClosed());
            }
        }
    }

}