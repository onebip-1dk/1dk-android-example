package com.onebip.onedk.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.onebip.onedk.Gender;
import com.onebip.onedk.OneDk;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1dk init
        OneDk.getInstance().init(this, R.string.onedk_app_id);

        // when available, pass information about the user
        OneDk.getInstance().setUserProfileData("john.doe@gmail.com", 20, 30, Gender.MALE);

        setContentView(R.layout.activity_main);

        Button buttonInterstitial = (Button) findViewById(R.id.button_interstitial);
        buttonInterstitial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial();
            }
        });

        Button buttonVideo = (Button) findViewById(R.id.button_video);
        buttonVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVideo();
            }
        });

        Button buttonPurchase = (Button) findViewById(R.id.button_purchase);
        buttonPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPurchase();
            }
        });
    }

    private void showInterstitial() {
        OneDk.getInstance().showInterstitial(this, R.string.onedk_interstitial_id);
    }

    private void showVideo() {
        OneDk.getInstance().showVideo(this, R.string.onedk_video_id);
    }

    private void startPurchase() {
        startActivity(new Intent(this, PurchaseActivity.class));
    }

}
