package org.ead2.bookapp;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Array;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button authors, books, read, search, changeLanguage;
    private TextView bookResult;
    private TextView authorResult;
    private EditText input;
    String currentLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        books = findViewById(R.id.books);
        bookResult = findViewById(R.id.bookResult);
        authorResult = findViewById(R.id.authorResult);
        search = findViewById(R.id.search);
        input = findViewById(R.id.input);
        read = findViewById(R.id.read);
        authors = findViewById(R.id.authors);
        changeLanguage = findViewById(R.id.changeLang);

        currentLanguage = Locale.getDefault().getLanguage().toLowerCase(Locale.ROOT);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ca2bookserver20220425025357.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JSONPlaceHolderAPI.class);
        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        // Callback
        Call<List<Books>> call = jsonPlaceHolderAPI.getBooks();
        call.enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {
                if (!response.isSuccessful()) {
                    bookResult.setText("Code: " + response.code());
                    return;
                }
                List<Books> books = response.body();
                for (Books books1 : books) {
                    String content = "";
                    content += books1.getBookName() + ", ";
                    content += books1.getGenre() + ", ";
                    content += "Reads: " + books1.getRead() + "\n\n";

                    bookResult.append(content);
                    bookResult.setMovementMethod(new ScrollingMovementMethod());
                }
            }
            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {
                bookResult.setText(t.getMessage());
            }
        });

        // Functionality for changing language
        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLanguage.equals("en")) {
                    currentLanguage = "hi";
                    Locale newLocale = new Locale(currentLanguage);
                    Locale.setDefault(newLocale);
                    Configuration config = new Configuration();
                    config.locale = newLocale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    recreate();
                    return;
                }
                else if (currentLanguage.equals("hi")) {
                    currentLanguage = "en";
                    Locale newLocale = new Locale(currentLanguage);
                    Locale.setDefault(newLocale);
                    Configuration config = new Configuration();
                    config.locale = newLocale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    recreate();
                    return;
                }
                }
        });

        // Get list of authors
        authors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookResult.setText("");
                authorResult.setText("");
                Call<List<Authors>> callAuthors = jsonPlaceHolderAPI.getAuthors();
                callAuthors.enqueue(new Callback<List<Authors>>() {
                    @Override
                    public void onResponse(Call<List<Authors>> callAuthors, Response<List<Authors>> response) {
                        if (!response.isSuccessful()) {
                            authorResult.setText("Code: " + response.code());
                            return;
                        }

                        List<Authors> authors = response.body();

                        for (Authors allAuthors : authors) {
                            String content = "";
                            content += "ID: " + allAuthors.getAuthorID() + ", ";
                            content += "Name: " + allAuthors.getAuthorName() + ", ";
                            content += "Age: " + allAuthors.getAge() + ", ";
                            content += "DOB: " + allAuthors.getDob() + "\n\n";

                            authorResult.append(content);
                            authorResult.setMovementMethod(new ScrollingMovementMethod());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Authors>> callAuthors, Throwable t) {
                        authorResult.setText(t.getMessage());
                    }
                });
            }
        });

        // Get list of books
        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookResult.setText("");
                authorResult.setText("");
                Call<List<Books>> call = jsonPlaceHolderAPI.getBooks();
                call.enqueue(new Callback<List<Books>>() {
                    // Collection displays if successful
                    @Override
                    public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {
                        if (!response.isSuccessful()) {
                            bookResult.setText("Code: " + response.code());
                            return;
                        }

                        // Puts JSON Objects inside collection & iterates through each item for bookResult
                        List<Books> books = response.body();

                        for (Books books1 : books) {
                            String content = "";
                            content += "ID:" + books1.getBookID() + ", ";
                            content += "Name: " + books1.getBookName() + ", ";
                            content += "Review: " + books1.getReview() + ", ";
                            content += "Price: €" + books1.getPrice() + ", ";
                            content += "Genre: " + books1.getGenre() + ", ";
                            content += "Reads: " + books1.getRead() + "\n\n";

                            bookResult.append(content);
                            bookResult.setMovementMethod(new ScrollingMovementMethod());
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Books>> call, Throwable t) {
                        bookResult.setText(t.getMessage());
                    }
                });
            }
        });

        // Search functionality for books
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookResult.setText("");
                authorResult.setText("");
                Call<List<Books>> callBookByName = jsonPlaceHolderAPI.getBookByName(input.getText().toString());
                callBookByName.enqueue(new Callback<List<Books>>() {
                    @Override
                    public void onResponse(Call<List<Books>> callBookByName, Response<List<Books>> response) {
                        if (!response.isSuccessful()) {
                            input.setText("Code: " + response.code());
                            return;
                        }
                        List<Books> books = response.body();

                        for (Books books1 : books) {
                            String content = "";
                            content += "ID:" + books1.getBookID() + ", ";
                            content += "Name: " + books1.getBookName() + ", ";
                            content += "Review: " + books1.getReview() + ", ";
                            content += "Price: €" + books1.getPrice() + ", ";
                            content += "Genre: " + books1.getGenre() + ", ";
                            content += "Reads: " + books1.getRead() + "\n\n";

                            bookResult.append(content);
                            bookResult.setMovementMethod(new ScrollingMovementMethod());
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Books>> callBookByName, Throwable t) {
                        input.setText(t.getMessage());
                    }
                });
            }
        });

        // Read Functionality to increment "Reads" count
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookResult.setText("");
                authorResult.setText("");
                Call<List<Books>> readBook = jsonPlaceHolderAPI.readBook(input.getText().toString());
                readBook.enqueue(new Callback<List<Books>>() {
                    // Puts JSON Objects inside collection & iterates through each item for no. of reads
                    @Override
                    public void onResponse(Call<List<Books>> readBook, Response<List<Books>> response) {
                        if (!response.isSuccessful()) {
                            input.setText("Code: " + response.code());
                            return;
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Books>> readBook, Throwable t) {
                        input.setText(t.getMessage());
                    }
                });
                Call<List<Books>> call = jsonPlaceHolderAPI.getBooks();
                call.enqueue(new Callback<List<Books>>() {
                    @Override
                    public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {
                        if (!response.isSuccessful()) {
                            bookResult.setText("Code: " + response.code());
                            return;
                        }

                        List<Books> books = response.body();
                        for (Books books1 : books) {
                            String content = "";
                            content += books1.getBookName() + ", ";
                            content += books1.getGenre() + ", ";
                            content += "Reads: " + books1.getRead() + "\n\n";

                            bookResult.append(content);
                            bookResult.setMovementMethod(new ScrollingMovementMethod());
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Books>> call, Throwable t) {
                        bookResult.setText(t.getMessage());
                    }
                });
            }
        });
    }
}