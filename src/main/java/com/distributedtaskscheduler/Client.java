package com.distributedtaskscheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 9000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            // A few sample tasks
            String[] tasks = {
                "1:UPPERCASE:hello world",
                "2:REVERSE:distributed systems",
                "3:LENGTH:WSO2",
                "4:LOWERCASE:JAVA",
                "5:UPPERCASE:concurrency"
            };

            for (String taskStr : tasks) {
                System.out.println("Sending: " + taskStr);
                out.println(taskStr);

                String response = in.readLine();
                System.out.println("Response: " + response + "\n");
                // If we get an ERROR response, we could retry, but we'll just print for now.
            }
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
