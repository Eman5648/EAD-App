package org.ead2.bookapp;

public class Books {

    private int bookID;
    private String bookName;
    private String review;
    private String genre;
    private int price;
    private int read;

    public int getBookID() {
        return bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public String getGenre() {
        return genre;
    }

    public String getReview() { return review; }

    public int getPrice() { return price; }

    public int getRead() { return read; }
}
