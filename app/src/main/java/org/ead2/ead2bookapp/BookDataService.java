package org.ead2.ead2bookapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookDataService {

    public static final String QUERY_FOR_URL = "https://ca2bookserver.azurewebsites.net/";
    public static final String QUERY_FOR_BOOKS = "https://ca2bookserver.azurewebsites.net/books";

    Context context;
    String authorID;

    public BookDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String authorID);
    }

    public void getAuthorID(String authorName, VolleyResponseListener volleyResponseListener) {
        String url = QUERY_FOR_URL + authorName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                authorID  = "";
                try {
                    JSONObject authorInfo = response.getJSONObject(0);
                    authorID = authorInfo.getString("authorID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // This worked, but did not return ID number to MainActivity
                // Toast.makeText(context, "Author ID = " + authorID, Toast.LENGTH_SHORT).show();
                volleyResponseListener.onResponse(authorID);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("Error occurred.");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);

        // returned NULL. Big problem.
        // return authorID;
    }

    public interface BookByIDResponse {
        void onError(String message);

        void onResponse(List<BookReportModel> bookReportModels);
    }


    public void getBookID(String getAuthorID, BookByIDResponse bookByIDResponse) {
        List<BookReportModel> bookReportModels = new ArrayList<>();

        String url = QUERY_FOR_BOOKS;
        // get the JSON Object
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();

                try {
                    JSONArray object_list = response.getJSONArray(0);
                    // First item in array
                    BookReportModel first_book = new BookReportModel();

                    for(int i=0; i<object_list.length(); i++ ) {

                        JSONObject first_book_api = (JSONObject) object_list.get(i);
                        first_book.setBookID(first_book_api.getInt("bookID"));
                        first_book.setBookName(first_book_api.getString("bookName"));
                        first_book.setReview(first_book_api.getString("review"));
                        first_book.setPrice(first_book_api.getLong("price"));
                        first_book.setAuthorID(first_book_api.getInt("authorID"));
                        bookReportModels.add(first_book);

                    }

                    bookByIDResponse.onResponse(bookReportModels);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // get the property called " " which is an array

        // get each item in the array and assign it to a new BookReportModel object.
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

}
