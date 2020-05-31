package com.example.mindspark;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.ArrayList;


public class Article {
    private String author, field, title, postType;
    private int estReadTime, numReads;
    private ArrayList<String> bodyText;
    private ArrayList<Integer> actualReadTime;

    public Article(){}
    public Article(String author, String field, String title, String postType, int estReadTime, int numReads, ArrayList<String> bodyText, ArrayList<Integer> actualReadTime){
        this.author = author;
        this.field = field;
        this.title = title;
        this.postType = postType;
        this.estReadTime = estReadTime;
        this.numReads = numReads;
        this.bodyText = bodyText;
        this.actualReadTime = actualReadTime;
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
        return getNumReads();
    }
    public ArrayList<String> getBodyText(){
        return bodyText;
    }
    public ArrayList<Integer> getActualReadTime(){
        return  actualReadTime;
    }
}

