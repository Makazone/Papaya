package com.vsrstudio.papaya.model;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Request")
public class Request extends ParseObject {
    public static final int ALL = 0;
    public static final int BUY = 1;
    public static final int EXCHANGE = 2;

    public static final int SEND = 0;
    public static final int ACCEPTED = 1;
    public static final int REJECTED = 2;

    public static void makeRequest(List<Book> booksOffered, Book bookRequested, int type, int amount) {
        Request newRequest = new Request();
        newRequest.setType(type);
        newRequest.setAmount(amount);
        newRequest.setStatus(SEND);

        newRequest.setBooksOffered(booksOffered);
        newRequest.setRequestedBook(bookRequested);

        newRequest.saveInBackground();
    }

    public void acceptRequest() {};
    public void rejectRequest() {};

    public int getType() {
        return getInt("type");
    }

    public void setType(int t) {
        put("type", t);
    }

    public int getAmount() {
        return getInt("amount");
    }

    public void setAmount(int m) {
        put("amount", m);
    }

    public int getStatus() {
         return getInt("status");
    }

    public void setStatus(int s) {
        put("status", s);
    }

    public void setBooksOffered(List<Book> booksOffered) {
        put("booksOffered", booksOffered);
    }

    public List<Book> getBooksOffered() {
        return getList("booksOffered");
    }

    public void setRequestedBook(Book b) {
        put("requestedBook", b);
    }

    public Book getRequestedBook() {
        return (Book)getParseObject("requestedBook");
    }
}
