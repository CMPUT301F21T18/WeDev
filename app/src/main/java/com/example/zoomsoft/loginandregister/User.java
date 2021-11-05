<<<<<<< HEAD
package com.example.zoomsoft.loginandregister;

import java.util.ArrayList;

//User attributes
=======
package com.example.zoomsoft;

import java.util.ArrayList;

>>>>>>> origin/main
public class User {
    private   String email;
    private   String password;
    private   String username;
    private   ArrayList<String> followers = new ArrayList<>(); //the list here contains the username->since it's unique within the database
    private   ArrayList<String> requests = new ArrayList<>(); //the list here contains the username->since it's unique within the database

<<<<<<< HEAD
    public User() {
        //unverified
    }

=======
    public User(String username) {
      this.username = username;
    }
>>>>>>> origin/main
    public User(String email, String password, String username) {
        //This is for registration:
        this.email = email;
        this.password = password;
        this.username = username;
    }
<<<<<<< HEAD
    //Constructor
=======

>>>>>>> origin/main
    public User(String email, String password, String username, ArrayList<String> followers, ArrayList<String> requests) {
        //This is for login:
        this.email = email;
        this.password = password;
<<<<<<< HEAD
        this.username = username;
        this.followers = followers;
        this.requests = requests;
    }


=======
        this.followers = followers;
        this.requests = requests;
        this.username = username;
    }

>>>>>>> origin/main
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
<<<<<<< HEAD

=======
>>>>>>> origin/main
    public void declineRequest(String username) {
        if(!this.requests.contains(username)) throw new IllegalArgumentException("User is not in" + username + "requests");
        else this.requests.remove(username);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> origin/main
