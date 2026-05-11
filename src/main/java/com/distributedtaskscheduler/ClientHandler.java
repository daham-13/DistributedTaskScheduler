package com.distributedtaskscheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final BlockingQueue<ClientRequest> requestQueue;

    public ClientHandler(Socket socket, BlockingQueue<ClientRequest> queue) {
        this.clientSocket = socket;
        this.requestQueue = queue;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String line;
            while ((line = in.readLine()) != null) {
                Task task = Task.fromString(line);
                System.out.println("Received task: " + task);
                requestQueue.put(new ClientRequest(task, out));
            }
        } catch (Exception e) {
            System.err.println("Client handler error: " + e.getMessage());
        } finally {
            try { clientSocket.close(); } catch (Exception ignored) {}
        }
    }
}
