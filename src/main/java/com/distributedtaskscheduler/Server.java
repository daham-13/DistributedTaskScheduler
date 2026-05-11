package com.distributedtaskscheduler;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class Server {
    private static final int PORT = 9000;
    private static final int WORKER_COUNT = 4;

    public static void main(String[] args) {
        BlockingQueue<ClientRequest> queue = new LinkedBlockingQueue<>();

        ExecutorService workerPool = Executors.newFixedThreadPool(WORKER_COUNT);
        for (int i = 0; i < WORKER_COUNT; i++) {
            workerPool.execute(new Worker(queue));
        }

        System.out.println("Distributed Task Scheduler Server started on port " + PORT);
        System.out.println("Workers ready: " + WORKER_COUNT);

        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                new Thread(new ClientHandler(clientSocket, queue)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerPool.shutdown();
        }
    }
}
