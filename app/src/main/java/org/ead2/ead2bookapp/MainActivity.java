package org.ead2.ead2bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_authors, btn_books, btn_all;
    EditText et_dataInput;
    ListView lv_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_authors = findViewById(R.id.btn_getAuthors);
        btn_books = findViewById(R.id.btn_getBooks);
        btn_all = findViewById(R.id.btn_getAllDetails);

        et_dataInput = findViewById(R.id.et_dataInput);
        lv_details = findViewById(R.id.lv_details);

        final BookDataService bookDataService = new BookDataService(MainActivity.this);

        // Click Listeners
        btn_authors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // This does not return anything
                bookDataService.getAuthorID(et_dataInput.getText().toString(), new BookDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something wrong!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String authorID) {
                        Toast.makeText(MainActivity.this, "Returned an ID of " + authorID, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btn_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookDataService.getBookID("1", new BookDataService.BookByIDResponse() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<BookReportModel> bookReportModels) {
                        // Puts list into listview control
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, bookReportModels);
                        lv_details.setAdapter(arrayAdapter);

                        // Toast.makeText(MainActivity.this, bookReportModel.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "You Typed " + et_dataInput.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}