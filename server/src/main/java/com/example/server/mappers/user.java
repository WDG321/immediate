package com.example.server.mappers;

public class user {
    private int id;
    private String username;
    private String password;
    private String profilePhoto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public user(int id, String username, String password, String profilePhoto) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profilePhoto = profilePhoto;
    }

    public user(int id, String username, String profilePhoto) {
        this.id = id;
        this.username = username;
        this.profilePhoto = profilePhoto;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                '}';
    }
}
