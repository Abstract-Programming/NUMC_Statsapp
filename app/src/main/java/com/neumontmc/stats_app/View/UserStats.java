package com.neumontmc.stats_app.View;

import com.neumontmc.stats_app.R;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class UserStats extends AppCompatActivity {

    TextView nameTV, uuidTV;

    String nameData, uuidData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stats);

        nameTV = findViewById(R.id.us_NameTextView);
        uuidTV = findViewById(R.id.us_UuidTextView);

        getData();
        setData();
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