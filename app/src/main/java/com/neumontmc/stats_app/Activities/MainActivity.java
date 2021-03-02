package com.neumontmc.stats_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.neumontmc.stats_app.Controllers.APIController;
import com.neumontmc.stats_app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        APIController apic = new APIController();
            //apic.init();
        Intent intent = new Intent(this, ShowUserStats.class);
        intent.putExtra("apic", apic);
        startActivity(intent);
    }
}