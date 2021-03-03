package com.neumontmc.stats_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Visibility;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.neumontmc.api.Models.User;
import com.neumontmc.stats_app.Controllers.APIController;
import com.neumontmc.stats_app.Controllers.ObjCompressor;
import com.neumontmc.stats_app.R;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

public class MainActivity extends AppCompatActivity {
    APIController apic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setUpWindowAnimations();
        setContentView(R.layout.activity_main);
        try  {
            ObjCompressor objCompressor = new ObjCompressor();
            apic = (APIController) objCompressor.decompressObject(getIntent().getByteArrayExtra("compressedApiController"));
            run();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void run(){
        TextView out = findViewById(R.id.so_wow);
        for(User u : apic.getUserList()){
            out.setText(u.toString());
        }
    }
    private void setUpWindowAnimations() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Log.i("ANIM", "Setting up transitions.");
            Fade fadeIN = new Fade(Visibility.MODE_IN);
            fadeIN.setDuration(1000);
            getWindow().setEnterTransition(fadeIN);
        }
    }
}
