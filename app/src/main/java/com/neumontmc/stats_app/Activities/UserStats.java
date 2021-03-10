package com.neumontmc.stats_app.Activities;

import com.bumptech.glide.Glide;
import com.neumontmc.stats_app.Controllers.APIController;
import com.neumontmc.stats_app.Controllers.ObjCompressor;
import com.neumontmc.stats_app.R;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;


import com.neumontmc.stats_app.Controllers.JsonView;

public class UserStats extends AppCompatActivity {

    TextView nameTV, uuidTV;

    String nameData, uuidData;

    Glide glide;

    ImageView userPFP;

    APIController apic;

    @SuppressLint("ResourceAsColor")
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
        JsonView jsonView = findViewById(R.id.jsonViewer);
        for (int i = 0; i < apic.getDatablocks().size(); i++) {
            if (apic.getDatablocks().get(i).contains(getIntent().getStringExtra("uuid"))) {
                jsonView.setJson(apic.getDatablocks().get(i));
                TextView noStatsMessages = findViewById(R.id.noStatsMessage);
                noStatsMessages.setVisibility(View.GONE);
                break;
            }
        }
    }

    public void getData() {
        if (getIntent().hasExtra("name") && getIntent().hasExtra("uuid")) {
            nameData = getIntent().getStringExtra("name");
            uuidData = getIntent().getStringExtra("uuid");
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setData() {
        nameTV.setText(nameData);
        uuidTV.setText(uuidData);
    }
}