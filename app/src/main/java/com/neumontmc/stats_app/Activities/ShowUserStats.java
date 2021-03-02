package com.neumontmc.stats_app.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.neumontmc.stats_app.Controllers.APIController;
import com.neumontmc.stats_app.R;

public class ShowUserStats extends AppCompatActivity {
    APIController apic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_stats);
        apic = getIntent().getParcelableExtra("apic");
        run();
    }

    private void run(){

    }
}
