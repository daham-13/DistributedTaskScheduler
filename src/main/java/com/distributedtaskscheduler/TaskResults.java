package com.distributedtaskscheduler;

public class TaskResults {
    private final String taskID;
    private final String result;
    private final boolean success;

    public TaskResults(String taskID, String result, boolean success) {
        this.taskID = taskID;
        this.result = result;
        this.success = success;
    }

    public String getTaskID() {
        return taskID;
    }   

    public String getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        // Sent over the socket as a single line
        return taskID + ":" + (success ? "OK" : "ERROR") + ":" + result;
    }

    public static TaskResults fromString(String line) {
        String[] parts = line.split(":", 3);
        if (parts.length < 3) return new TaskResults("unknown", "Bad result format", false);
        return new TaskResults(parts[0], parts[2], parts[1].equals("OK"));
    }
}
