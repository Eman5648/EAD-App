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

    @GET("Books/book/{bookName}")
    Call<List<Books>> getBookByName(@Path("bookName") String bookName);

    @PUT("Books/read/{bookName}")
    Call<List<Books>> readBook(@Path("bookName") String bookName);

    @GET("Books/genre/{genreName}")
    Call<List<Books>> filterBook(@Path("genreName") String genreName);
}
