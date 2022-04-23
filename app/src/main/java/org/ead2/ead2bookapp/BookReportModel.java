package org.ead2.ead2bookapp;

public class BookReportModel {
/*              "bookID": 1,
                "bookName": "Book1",
                "review": "Good",
                "price": 10,
                "authorID": 1
}*/

    private int bookID;
    private String bookName;
    private String review;
    private float price;
    private int authorID;

    public BookReportModel(int bookID, String bookName, String review, float price, int authorID) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.review = review;
        this.price = price;
        this.authorID = authorID;
    }

    public BookReportModel() {
    }

    @Override
    public String toString() {
        return "BookReportModel{" +
                "bookID=" + bookID +
                ", bookName='" + bookName + '\'' +
                ", review='" + review + '\'' +
                ", price=" + price +
                "authorID=" + authorID +
                '}';
    }


    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }
}
