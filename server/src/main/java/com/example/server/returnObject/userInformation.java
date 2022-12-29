package com.example.server.returnObject;

public class userInformation {
    private String username;
    private int id;

    public String getUsername() {
        return username;
    }

    public userInformation(String username, int id) {
        this.username = username;
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "userInformation{" +
                "username='" + username + '\'' +
                ", id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
