package com.example.aditya.bookstore;

/**
 * Created by Aditya on 2/19/16.
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rest.client.BookClient;
import rest.client.User;
import rest.client.UserClient;
import rest.client.Book;

public class SearchResultsActivity extends AppCompatActivity {

    /* For Display */
    ListView l;
    /* Number of books from backend */
    List<Book> books;
    /* User Query */
    String query;
    /* User task in Async */
    private UserLoginTask mBookTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d("AdiD", "Search Activity : On create !");
        handleIntent(getIntent());

        try{
            Thread.sleep(2000);
        }catch(Exception e){
            e.printStackTrace();
        }
        // List View Item

        l = (ListView) findViewById(R.id.listView);
        String[] retr_books = retrieveBooksForDisplay();
        if(retr_books.length >0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, retr_books);
            l.setAdapter(adapter);
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "No result found !!", Toast.LENGTH_SHORT);
        }
        
        //finish();
    }

    public void OnItemClickListener(AdapterView<?> adapterView, View view, int i, long l) {
        TextView temp = (TextView) view;

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
        Log.d("AdiD", "Search Activity : On create Option!");

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                Toast toast = Toast.makeText(getApplicationContext(), "Query change", Toast.LENGTH_SHORT);
                toast.show();
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast toast = Toast.makeText(getApplicationContext(), "Query Submit", Toast.LENGTH_SHORT);
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

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);

            // Ask the ba
            Log.d("AdiD", "Search Activity : Handle Intent with Query!" + query);
            Context context = getApplicationContext();
            CharSequence text = "You have searched for" + query;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            books = new ArrayList<>();
            GetBooks();
        }
    }

    /* Check for valid query and send the response to Async task */
    private void GetBooks() {
        if (query == null) {
            Log.d("AdiD", "Search Activity Get books : Null Query !");
            return;
        }
        boolean cancel = false;

        // Check if the phone number is valid
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
        } else {
            // Show a progress spinner, and kick off a background task to
            Log.d("AdiD", "Search Activity Get books : Start Async task !");
            mBookTask = new UserLoginTask(query);
            mBookTask.execute((Void) null);
        }
    }
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String SearchQuery;

        UserLoginTask(String SearchQuery) {
            this.SearchQuery = SearchQuery;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                Log.d("AdiD", "Async BckGrnd task : Retrieving books from Backend !");
                BookClient book_search = new BookClient();
                if (SearchQuery != null) {
                    try {
                        books = book_search.search(SearchQuery);
                        int size = books.size();
                        Log.d("AdiD", "Async BckGrnd task : Retrieved " + size + " books from Backend !");

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
                Log.d("AdiD", "Search Activity : Post Execute Success3 !");
            } else {

                // mPasswordView.setError(getString(R.string.error_incorrect_password));
                // mPasswordView.requestFocus();
            }
        }
    }
}