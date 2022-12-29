package com.example.server.returnObject;

public class userInformation {
    private String username;
    private int id;
    private String profilePhoto;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public userInformation(String username, int id, String profilePhoto) {
        this.username = username;
        this.id = id;
        this.profilePhoto = profilePhoto;
    }

    @Override
    public String toString() {
        return "userInformation{" +
                "username='" + username + '\'' +
                ", id=" + id +
                ", profilePhoto='" + profilePhoto + '\'' +
                '}';
    }
}
