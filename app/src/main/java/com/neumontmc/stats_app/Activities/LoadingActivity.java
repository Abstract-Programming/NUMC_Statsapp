package com.neumontmc.stats_app.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.transition.Fade;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.neumontmc.stats_app.Controllers.APIController;
import com.neumontmc.stats_app.Controllers.ObjCompressor;
import com.neumontmc.stats_app.R;

public class LoadingActivity extends AppCompatActivity {
    private ActivityOptions options;
    private Button retryConnectionButton;
    private TextView errorMessage;
    private AnimationDrawable animDrawable;
    private ImageView logoCasket;
    private ImageView logo;
    private Intent nextIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setUpWindowAnimations();
        options = ActivityOptions.makeSceneTransitionAnimation(this);
        setContentView(R.layout.activity_loading);
        run();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void run() {
        runOnUiThread(() -> {
            errorMessage = findViewById(R.id.loadingErrorMessage);
            retryConnectionButton = findViewById(R.id.retryConnection);
            logo = findViewById(R.id.imageView);
            logo.setImageDrawable(getResources().getDrawable(R.drawable.gradient_animation));
            logoCasket = findViewById(R.id.logoCasket);
            animDrawable = (AnimationDrawable) logo.getDrawable();
            animDrawable.setEnterFadeDuration(10);
            animDrawable.setExitFadeDuration(500);
            ((Animatable) animDrawable).start();
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startUI();
            }
        }, 2000);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void startUI() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (isNetworkAvailable()) {
                    try {
                        APIController apiController = new APIController();
                        apiController.init(); //Load datasets from remote
                        nextIntent = new Intent(getApplicationContext(), SearchUsersActivity.class); //Create new APIController parcel
                        byte[] compressedObj = new ObjCompressor().compressObject(apiController);//Compress apiController (needed to prevent BINDER exception)
                        nextIntent.putExtra("compressedApiController", compressedObj); //Pass parcel to intent
                        moveToMainActivity();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    setErrorNetMessage();
                }
            }
        }).start();

    }

    public void setErrorNetMessage(){
        runOnUiThread(() -> {
            errorMessage.setText("Could not connect to the internet.");
            errorMessage.setVisibility(View.VISIBLE);
            retryConnectionButton.setClickable(true);
            retryConnectionButton.setEnabled(true);
            retryConnectionButton.setVisibility(View.VISIBLE);
        });
    }

    public void onClickRetryConnection(View v) {
        errorMessage.setText("Trying to connect to the internet...");
        retryConnectionButton.setClickable(false);
        retryConnectionButton.setEnabled(false);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startUI();
            }
        }, 1000);
    }

    private void moveToMainActivity() {
        runOnUiThread(() -> {
            transitionOutImageView(logo, 200);
            transitionOutImageView(logoCasket, 1000);
        });
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Activity activity = (Activity) retryConnectionButton.getContext();
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(activity).toBundle();
                startActivity(nextIntent);
                activity.finish();
            }
        }, 2000);
    }

    private void setUpWindowAnimations() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Log.i("ANIM", "Setting up transitions.");
            Fade fadeI = new Fade(Visibility.MODE_IN);
            fadeI.setDuration(1000);
            getWindow().setEnterTransition(fadeI);
        }
    }
    private void transitionOutImageView(ImageView targetImageView, int duration){
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(duration);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
                targetImageView.setVisibility(View.GONE);
            }
        });
        targetImageView.startAnimation(fadeOut);
    }
}