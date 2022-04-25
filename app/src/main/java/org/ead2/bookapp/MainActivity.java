package org.ead2.bookapp;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button authors, books, read, search, action, fiction, drama, horror, changeLanguage;
    private TextView bookResult;
    private TextView authorResult;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        loadLocale();*/
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

        changeLanguage = findViewById(R.id.changeLanguage);

//        LView = findViewById(R.id.LView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ca2bookserver20220425025357.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JSONPlaceHolderAPI.class);

        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                showChangeLanguageDialog();*/
            }
        });

        // Callback
        Call<List<Books>> call = jsonPlaceHolderAPI.getBooks();
        call.enqueue(new Callback<List<Books>>() {
            // Checks if successful and displays
            @Override
            public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {
                if (!response.isSuccessful()) {
                    bookResult.setText("Code: " + response.code());
                    return;
                }

                // Gets JSON Objects
                List<Books> books = response.body();

                // Iterates through each JSON item
                for (Books books1 : books) {
                    String content = "";
                    content += books1.getBookName() + ", ";
                    content += books1.getGenre() + ", ";
                    content += "Reads: " + books1.getRead() + "\n\n";

                    bookResult.append(content);
                    bookResult.setMovementMethod(new ScrollingMovementMethod());
                }
            }
            // Checks if unsuccessful and displays error
            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {
                bookResult.setText(t.getMessage());
            }
        });


        //get api/books
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

        //get api/books by filter
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

                        // Puts JSON Objects inside collection & iterates through each item for BOOK DISPLAY
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
                    // Error handling
                    @Override
                    public void onFailure(Call<List<Books>> call, Throwable t) {
                        bookResult.setText(t.getMessage());
                    }
                });
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookResult.setText("");
                authorResult.setText("");
                Call<List<Books>> callBookByName = jsonPlaceHolderAPI.getBookByName(input.getText().toString());
                callBookByName.enqueue(new Callback<List<Books>>() {
                    // Collection displays if successful
                    @Override
                    public void onResponse(Call<List<Books>> callBookByName, Response<List<Books>> response) {
                        if (!response.isSuccessful()) {
                            input.setText("Code: " + response.code());
                            return;
                        }

                        // Puts JSON Objects inside collection & iterates through each item for book search
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
                    // Checks if unsuccessful and displays error
                    @Override
                    public void onFailure(Call<List<Books>> callBookByName, Throwable t) {
                        input.setText(t.getMessage());
                    }
                });
            }
        });

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
                    // Checks if unsuccessful and displays error
                    @Override
                    public void onFailure(Call<List<Books>> readBook, Throwable t) {
                        input.setText(t.getMessage());
                    }
                });
                //
                Call<List<Books>> call = jsonPlaceHolderAPI.getBooks();
                call.enqueue(new Callback<List<Books>>() {
                    // Checks if successful and displays
                    @Override
                    public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {
                        if (!response.isSuccessful()) {
                            bookResult.setText("Code: " + response.code());
                            return;
                        }

                        // Gets JSON Objects
                        List<Books> books = response.body();

                        // Iterates through each JSON item
                        for (Books books1 : books) {
                            String content = "";
                            content += books1.getBookName() + ", ";
                            content += books1.getGenre() + ", ";
                            content += "Reads: " + books1.getRead() + "\n\n";

                            bookResult.append(content);
                            bookResult.setMovementMethod(new ScrollingMovementMethod());
                        }
                    }
                    // Checks if unsuccessful and displays error
                    @Override
                    public void onFailure(Call<List<Books>> call, Throwable t) {
                        bookResult.setText(t.getMessage());
                    }
                });
            }
        });

/*    private void showChangeLanguageDialog() {
        final String[] listLangs = {"Bosnian", "Russian", "English"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Choose Language...");
        mBuilder.setSingleChoiceItems(listLangs, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {*/
/*                if (i == 0){
                    // Bosnian
                    setLocale("bs");
                    recreate();
                }
                else if (i == 1){
                    // Russian
                    setLocale("ru");
                    recreate();
                }
                else if (i == 2){
                    // English
                    setLocale("en");
                    recreate();
                }*/

/*                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();

        mDialog.show();
    }*/

/*    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        // Save data
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }*/

/*    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);*/

    }
}