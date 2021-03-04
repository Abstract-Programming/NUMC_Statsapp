package com.neumontmc.stats_app.View;

import com.neumontmc.stats_app.Controllers.SearchAdapter;
import com.neumontmc.stats_app.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;

public class SearchUsersActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String[] names, uuids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);

        recyclerView = findViewById(R.id.reView);

        //names = //Get names
        //uuids = //Get uuids

        genViewer(names);

        EditText searchUserET = findViewById(R.id.searchUserET);
        searchUserET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text){
        ArrayList<String> filteredList = new ArrayList<>();

        for (String string : names){
            if (string.toLowerCase().contains(text.toLowerCase())){
                filteredList.add(string);

            }
        }

        genViewer((String[])filteredList.toArray());
    }

    private void genViewer(String[] currentNames){
        SearchAdapter sAdapter = new SearchAdapter(this, currentNames, uuids);
        recyclerView.setAdapter(sAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}