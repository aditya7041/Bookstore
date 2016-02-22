package com.example.aditya.bookstore;

/**
 * Created by ejazveljee on 2/21/16.
 */
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rest.client.BookClient;
import rest.client.User;
import rest.client.UserClient;
import rest.client.Book;

public class AfterSignupOrLoginActivity extends AppCompatActivity {

    /* For Display */
    ListView l;
    /* Number of books from backend */
    List<Book> books;
    /* User Query */
    int user_id;
    /* User task in Async */
    private UserRecommendedTask RecommendedBookTask = null;

    /* Sell and Buy Button */
    private static Button AfterSignupOrLoginBuyButton;
    private static Button AfterSignupOrLoginSellButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_signup_or_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d("AdiD", "Recommend(After signup/login) : On create !");

        /* Toast of Recommendation */
        Toast.makeText(getApplicationContext(), "Recommended Books !", Toast.LENGTH_SHORT).show();
        handleIntent(getIntent());

        try{
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        // List View Item

        l = (ListView) findViewById(R.id.listView);

        //String[] hardcodedRecommendationBooks = {"Book1","Book2","Book3","Book4","Book5"};
        String[] retr_books = retrieveBooksForDisplay();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, retr_books);
        l.setAdapter(adapter);
    }

    public void OnItemClickListener(AdapterView<?> adapterView, View view, int i, long l) {
        TextView temp = (TextView) view;

    }

    public void onClickButtonListener() {

        AfterSignupOrLoginBuyButton = (Button) findViewById(R.id.AfterSignupOrLoginBuyButton);
        AfterSignupOrLoginBuyButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent buy_intent = new Intent("com.example.aditya.bookstore.BuyMainActivity");
                        startActivity(buy_intent);
                    }
                }
        );

        AfterSignupOrLoginSellButton = (Button) findViewById(R.id.AfterSignupOrLoginSellButton);
        AfterSignupOrLoginSellButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent sell_intent = new Intent("com.example.aditya.bookstore.SellMainActivity");
                        startActivity(sell_intent);
                    }
                }
        );

    }

    public String[] retrieveBooksForDisplay(){

        int size = books.size();
        String[]ret_books = new String[size];

        if(size==0){
            Log.d("AdiD","Zero Book from Backend");
            return null;
        }else{

            for(int i=0;i<size;i++){
                ret_books[i] = books.get(i).getName();
            }
            Log.d("AdiD","Num of books retrieved from backend = " + size);
        }
        return ret_books;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        Log.d("AdiD", "Recommend(After signup/login) Activity : On create Option!");

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                Toast toast = Toast.makeText(getApplicationContext(), "Press Enter for search !", Toast.LENGTH_SHORT);
                toast.show();
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast toast = Toast.makeText(getApplicationContext(), "Showing Results !", Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
        });

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Log.d("AdiD", "Search Activity : Handle Intent outside get Action!");

        //if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
          //  query = intent.getStringExtra(SearchManager.QUERY);
            user_id=1;
            Log.d("AdiD", "Recommend(After signup/login) Activity : Handle Intent with Query!" + user_id);

            books = new ArrayList<>();
            GetBooks();
    }

    /* Check for valid query and send the response to Async task */
    private void GetBooks() {
        if (user_id == 0) {
            Log.d("AdiD", "Recommend(After signup/login) Activity Get books : User Id = 0 !");
            return;
        }
        boolean cancel = false;

        // Check if the phone number is valid
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
        } else {
            // Show a progress spinner, and kick off a background task to
            Log.d("AdiD", "Recommend activity Get books : Start Async task !");
            RecommendedBookTask = new UserRecommendedTask(user_id);
            RecommendedBookTask.execute((Void) null);
        }
    }
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserRecommendedTask extends AsyncTask<Void, Void, Boolean> {

        private final int userid;

        UserRecommendedTask(int user_id) {
            this.userid = user_id;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                Log.d("AdiD", "Recommend Act. Async BckGrnd task : Retrieving books from Backend !");
                UserClient UserRecommendedBooks = new UserClient();

                if (userid != 0) {

                    try {
                        books = UserRecommendedBooks.recommendBooks(userid);
                        Log.d("AdiD", "Recommend Act. Async BckGrnd task : Retrieved " + books.size() + " books from Backend !");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                Log.d("AdiD", "Recommend Activity : Post Execute Success !");
            } else {
                // mPasswordView.setError(getString(R.string.error_incorrect_password));
                // mPasswordView.requestFocus();
            }
        }
    }
}