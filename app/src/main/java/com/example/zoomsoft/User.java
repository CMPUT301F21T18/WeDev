package com.example.zoomsoft;

import java.util.ArrayList;

public class User {
    private   String email;
    private   String password;
    private   String username;
    private   ArrayList<String> followers = new ArrayList<>(); //the list here contains the username->since it's unique within the database
    private   ArrayList<String> requests = new ArrayList<>(); //the list here contains the username->since it's unique within the database

    public User() {
        //unverified
    }
    public User(String email, String password, String username) {
        //This is for registration:
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public User(String email, String password, String username, ArrayList<String> followers, ArrayList<String> requests) {
        //This is for login:
        this.email = email;
        this.password = password;
        this.followers = followers;
        this.requests = requests;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getFollowers() {
        return followers;
    }

    public ArrayList<String> getRequests() {
        return requests;
    }
    //Implement remove user from requests after acceptance or decline
    public void acceptRequest(String username) {
        this.requests.remove(username);
        this.followers.add(username);
    }
    public void declineRequest(String username) {
        if(!this.requests.contains(username)) throw new IllegalArgumentException("User is not in" + username + "requests");
        else this.requests.remove(username);
    }
}
