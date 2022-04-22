package org.ead2.bookappca2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // Class Member Variables

    Button btn;
    TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign Values to Table & Button
        btn = findViewById(R.id.btn_newEntry);

        // Click listeners to button
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Navigating to the Add Book Screen...", Toast.LENGTH_LONG).show();
            }
        });

        TextView txt = findViewById(R.id.row1Author);
        txt.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            RequestQueue requestQueue;

            // Instantiate the cache
            Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

            // Set up the network to use HttpURLConnection as the HTTP client.
            Network network = new BasicNetwork(new HurlStack());

            // Instantiate the RequestQueue with the cache and network.
            requestQueue = new RequestQueue(cache, network);

            // Start the queue
            requestQueue.start();

            String url = "https://ca2bookserver.azurewebsites.net/authors";

            // Formulate the request and handle the response.
/*            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Do something with the response
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error
                        }
                    });*/
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    String authorDetails = "";

                    try {
                        JSONObject getAuthor1 = response.getJSONObject(0);
                        authorDetails = getAuthor1.getString("authorName");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Toast.makeText(MainActivity.this, "Author Details = " + authorDetails, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });

            // Add the request to the RequestQueue.
            requestQueue.add(request);
        }
    });

    }
}