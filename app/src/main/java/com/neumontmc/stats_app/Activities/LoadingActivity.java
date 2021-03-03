package com.neumontmc.stats_app.Activities;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.neumontmc.stats_app.Controllers.APIController;
import com.neumontmc.stats_app.R;

import static androidx.core.content.ContextCompat.startActivity;

public class LoadingActivity extends AppCompatActivity {
    private ImageView logo;
    AnimationDrawable animDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_loading);
        run();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void run() {
        runOnUiThread(() -> {
            logo = findViewById(R.id.imageView);
            logo.setImageDrawable(getResources().getDrawable(R.drawable.gradient_animation));
            animDrawable = (AnimationDrawable) logo.getDrawable();
            animDrawable.setEnterFadeDuration(10);
            animDrawable.setExitFadeDuration(500);
            ((Animatable) animDrawable).start();
        });
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        APIController apiController = new APIController();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    apiController.init(); //Load datasets from remote
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class); //Create new APIController parcel
                    intent.putExtra("apiController", apiController); //Pass parcel to intent
                    startActivity(intent, options.toBundle());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}