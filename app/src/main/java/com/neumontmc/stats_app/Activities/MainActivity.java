package com.neumontmc.stats_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.neumontmc.api.Controllers.Zeitsterung;
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
    private TextView totalPlayTime, totalPlayer;
    private ProgressBar pb1, pb2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setUpWindowAnimations();
        setContentView(R.layout.activity_main);
        totalPlayer = findViewById(R.id.totalPlayer);
        totalPlayTime = findViewById(R.id.totalPlayTime);
        pb1 = findViewById(R.id.progressBar);
        pb2 = findViewById(R.id.progressBar3);
        try  {
            ObjCompressor objCompressor = new ObjCompressor();
            apic = (APIController) objCompressor.decompressObject(getIntent().getByteArrayExtra("compressedApiController"));
            run();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void run(){
                int i = 0;
                for(User u : apic.getUserList()){
                    i++;
                }
                pb1.setVisibility(View.INVISIBLE);
                updateTotalPlayers(String.valueOf(i));
                long tpt = 0;
                for (User u : apic.getUserList()){
                    tpt += u.getTotalPlayTime();
                }
                pb2.setVisibility(View.INVISIBLE);
                updatePlayersStats(tpt);

    }
    private void setUpWindowAnimations() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Log.i("ANIM", "Setting up transitions.");
            Fade fadeIN = new Fade(Visibility.MODE_IN);
            fadeIN.setDuration(1000);
            getWindow().setEnterTransition(fadeIN);
        }
    }
    private void updatePlayersStats(long time){
        Zeitsterung zeitsterung = new Zeitsterung();
        totalPlayTime.setText(zeitsterung.convertMillisToStdDHMSFormat(time));
        totalPlayTime.setVisibility(View.VISIBLE);
    }
    private void updateTotalPlayers(String playersCount){

        totalPlayer.setText(playersCount);
        totalPlayer.setVisibility(View.VISIBLE);
    }
}
