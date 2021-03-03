package com.neumontmc.stats_app.View;

import com.neumontmc.stats_app.Controllers.SearchAdapter;
import com.neumontmc.stats_app.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class SearchUsersActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String names[], uuids[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);

        recyclerView = findViewById(R.id.reView);

        //names = //Get names
        //uuids = //Get uuids

        SearchAdapter sAdapter = new SearchAdapter(this, names, uuids);
        recyclerView.setAdapter(sAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}