package org.ead2.bookapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JSONPlaceHolderAPI {

    @GET("Books")
    Call<List<Books>> getBooks();

    @GET("Authors")
    Call<List<Authors>> getAuthors();

    @GET("books/{bookName}")
    Call<List<Books>> getBookByName(@Path("bookName") String bookName);

    @PUT("books/view/{bookName}")
    Call<List<Books>> readBook(@Path("bookName") String bookName);
}
