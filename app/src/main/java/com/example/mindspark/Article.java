package com.example.mindspark;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.ArrayList;


public class Article {
    private String author, field, title, postType, id, authorID, imageURL, subheading;
    private int estReadTime, numReads, likes;
    private ArrayList<String> bodyText, comments;
    private ArrayList<Integer> actualReadTime;

    public Article(){}
    public Article(String author, String field, String title, String postType, int estReadTime, int numReads, ArrayList<String> bodyText, ArrayList<Integer> actualReadTime, int likes, String id, ArrayList<String> comments, String authorID, String imageURL, String subheading){
        this.author = author;
        this.field = field;
        this.title = title;
        this.postType = postType;
        this.estReadTime = estReadTime;
        this.numReads = numReads;
        this.bodyText = bodyText;
        this.actualReadTime = actualReadTime;
        this.likes = likes;
        this.id = id;
        this.comments = comments;
        this.authorID = authorID;
        this.imageURL = imageURL;
        this.subheading = subheading;
    }
    public String getAuthor(){
        return author;
    }
    public String getField(){
        return field;
    }
    public String getTitle(){
        return title;
    }
    public String getPostType(){
        return postType;
    }
    public int getEstReadTime(){
        return  estReadTime;
    }
    public int getNumReads(){
        return numReads;
    }
    public ArrayList<String> getBodyText(){
        return bodyText;
    }
    public ArrayList<Integer> getActualReadTime(){
        return  actualReadTime;
    }
    public int getLikes(){
        return likes;
    }
    public String getId(){
        return id;
    }
    public ArrayList<String> getComments(){
        return comments;
    }
    public String getAuthorID() {
        return authorID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getSubheading() {
        return subheading;
    }
}

