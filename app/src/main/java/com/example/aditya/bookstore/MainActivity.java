package com.example.aditya.bookstore;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.app.SearchManager;
import android.support.v7.widget.SearchView;
import android.content.Context;
import android.widget.Toast;
import android.app.SearchableInfo;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static Button Buybutton;
    private static Button Sellbutton;
    private static Button Loginbutton;
    private static Button SignUpbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        onClickButtonListener();

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    public void onClickButtonListener() {

        Buybutton = (Button) findViewById(R.id.BuyButton);
        Buybutton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent buy_intent = new Intent("com.example.aditya.bookstore.BuyMainActivity");
                        startActivity(buy_intent);
                    }
                }
        );

        Sellbutton = (Button) findViewById(R.id.SellButton);
        Sellbutton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent sell_intent = new Intent("com.example.aditya.bookstore.SellMainActivity");
                        startActivity(sell_intent);
                    }
                }
        );

        Loginbutton = (Button) findViewById(R.id.LoginButton);
        Loginbutton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent login_intent = new Intent("com.example.aditya.bookstore.LoginActivity");
                        startActivity(login_intent);
                    }
                }
        );

        SignUpbutton = (Button) findViewById(R.id.SignupButton);
        SignUpbutton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent sign_intent = new Intent("com.example.aditya.bookstore.SignUpActivity");
                        startActivity(sign_intent);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //MenuItem searchItem = menu.findItem(R.id.search);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        Log.d("AdiD", "Main Activity : OnCreateOptionMenu!");
        Context context = this;
        SearchManager searchManager =
                (SearchManager) getSystemService(context.SEARCH_SERVICE);

        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Context context = getApplicationContext();
            CharSequence text = "You clicked on Setting in Menu of main activity";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return true;
        }

        if (id == R.id.menu_search) {

            Context context = getApplicationContext();
            CharSequence text = "You clicked on Search in Menu of main activity";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
