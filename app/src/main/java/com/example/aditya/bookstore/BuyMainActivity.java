package com.example.aditya.bookstore;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
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
/**
 * Created by Aditya on 2/20/16.
 */
public class BuyMainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_main);
    }
}
