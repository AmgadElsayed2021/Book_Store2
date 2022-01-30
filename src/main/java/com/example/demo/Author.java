package com.example.demo;

import java.util.List;

public class Author {
    private  String authorFName;
    public String getAuthorFName() {return authorFName;}
    public void setAuthorFName(String authorFName) {this.authorFName = authorFName;}


    private  String authorLName;
    public String getAuthorLName() {return authorLName;}
    public void setAuthorLName(String authorLName) {this.authorLName = authorLName;}

    private List<Books> booksList;
    public List<Books> getBooksList() {return booksList;}
    public void setBooksList(List<Books> booksList) {this.booksList = booksList;}


    private  int ID;
    public int getID() {return ID;}

    public void setID(int ID) {this.ID = ID;}

    public Author() {
        this.authorFName = authorFName;
        this.authorLName = authorLName;
        this.ID=ID;
    }

    public Author(int ID,String authorFName, String authorLName, List<Books> booksList) {
        this.authorFName = authorFName;
        this.authorLName = authorLName;
        this.booksList = booksList;
        this.ID=ID;
    }

}
