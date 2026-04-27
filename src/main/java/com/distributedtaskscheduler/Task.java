package com.distributedtaskscheduler;

public class Task {

    private final String id;
    private final String type;
    private final String payload;

    public Task(String id, String type, String payload) {
        this.id = id;
        this.type = type;
        this.payload = payload;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return id + ":" + type + ":" + payload;
    }

    public static Task fromString(String taskString) {
        String[] parts = taskString.split(":", 3);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid task string: " + taskString);
        }
        return new Task(parts[0], parts[1], parts[2]);
    }
}    