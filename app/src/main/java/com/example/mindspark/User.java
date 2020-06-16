package com.example.mindspark;

import java.util.ArrayList;

public class User {
    String firstName, lastName, email, profilePicImg;
    ArrayList<Article> postedArticles;
    int followers;

    public User(String firstName, String lastName, String email, ArrayList<Article> postedArticles, int followers, String profilePicImg) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.postedArticles = postedArticles;
        this.followers = followers;
        this.profilePicImg = profilePicImg;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Article> getPostedArticles() {
        return postedArticles;
    }

    public int getFollowers() {
        return followers;
    }

    public String getProfilePicImg() {
        return profilePicImg;
    }
}
