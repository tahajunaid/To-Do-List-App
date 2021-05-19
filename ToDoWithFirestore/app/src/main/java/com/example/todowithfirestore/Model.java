package com.example.todowithfirestore;

public class Model {
    String id, task, details;

    public Model() {

    }

    public Model(String id, String task, String details) {
        this.details = details;
        this.id = id;
        this.task = task;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
