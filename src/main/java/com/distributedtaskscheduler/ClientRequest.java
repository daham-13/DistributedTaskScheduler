package com.distributedtaskscheduler;

import java.io.PrintWriter;

public class ClientRequest {
    private final Task task;
    private final PrintWriter clientOut;

    public ClientRequest(Task task, PrintWriter clientOut) {
        this.task = task;
        this.clientOut = clientOut;
    }

    public Task getTask() {
        return task;
    }

    public PrintWriter getClientOut() {
        return clientOut;
    }
}
