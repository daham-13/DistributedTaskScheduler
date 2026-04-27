package com.distributedtaskscheduler;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Worker implements Runnable {

    private final BlockingQueue<ClientRequest> taskQueue;
    private final Random random = new Random();
    private volatile boolean running = true;

    public Worker(BlockingQueue<ClientRequest> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                ClientRequest request = taskQueue.take();
                Task task = request.getTask();

                System.out.println(Thread.currentThread().getName() + " processing task: " + task.getId());

                //process the task

                String result = switch (task.getType()) {
                    case "UPPERCASE" -> task.getPayload().toUpperCase();
                    case "LOWERCASE" -> task.getPayload().toLowerCase();
                    case "REVERSE" -> new StringBuilder(task.getPayload()).reverse().toString();
                    case "LENGTH" -> String.valueOf(task.getPayload().length());
                    default -> "Unknown task type: " + task.getType();
                };

                request.getClientOut().println(task.getId() + ":OK:" + result);
                System.out.println(Thread.currentThread().getName() + " completed task " + task.getId() + " -> " + result);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName() + " interrupted, stopping worker.");
            }
        }
    }
}