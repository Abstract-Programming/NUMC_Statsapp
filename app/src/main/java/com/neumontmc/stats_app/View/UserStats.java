package com.neumontmc.stats_app.View;

import com.neumontmc.stats_app.Controllers.APIController;
import com.neumontmc.stats_app.Controllers.ObjCompressor;
import com.neumontmc.stats_app.Controllers.SearchAdapter;
import com.neumontmc.stats_app.Controllers.StatAdapter;
import com.neumontmc.stats_app.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class UserStats extends AppCompatActivity {

    TextView nameTV, uuidTV;

    String nameData, uuidData;

    APIController apic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stats);
        try {
            ObjCompressor objCompressor = new ObjCompressor();
            apic = (APIController) objCompressor.decompressObject(getIntent().getByteArrayExtra("compressedApiController"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        nameTV = findViewById(R.id.us_NameTextView);
        uuidTV = findViewById(R.id.us_UuidTextView);

        getData();
        setData();
        RecyclerView recyclerView = findViewById(R.id.stats);
        StatAdapter sAdapter = new StatAdapter(getApplication().getBaseContext(), null);
        recyclerView.setAdapter(sAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getData(){
        if (getIntent().hasExtra("name") && getIntent().hasExtra("uuid")){
            nameData = getIntent().getStringExtra("name");
            uuidData = getIntent().getStringExtra("uuid");
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setData(){
        nameTV.setText(nameData);
        uuidTV.setText(uuidData);
    }
}