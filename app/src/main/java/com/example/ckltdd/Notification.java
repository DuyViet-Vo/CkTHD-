package com.example.ckltdd;

import android.graphics.Color;
import android.os.Handler;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.cardview.widget.CardView;

public class Notification {
    Button notification;
    CardView card_notification;

    public Notification(Button notification, CardView card_notification) {
        this.notification = notification;
        this.card_notification = card_notification;
    }

    public void Notify(String msg, String type) {
        notification.setText(msg);
        if(type.equals("success")) notification.setBackgroundColor(Color.parseColor("#6CD06A"));
        if(type.equals("warning")) notification.setBackgroundColor(Color.parseColor("#F8B95B"));
        if(type.equals("danger")) notification.setBackgroundColor(Color.parseColor("#FF7373"));

        Handler uiHandler = new Handler();
        uiHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                RelativeLayout.LayoutParams layout = (RelativeLayout.LayoutParams) card_notification.getLayoutParams();
                layout.height = FrameLayout.LayoutParams.WRAP_CONTENT;
                layout.width = FrameLayout.LayoutParams.MATCH_PARENT;
                card_notification.setLayoutParams(layout);
            }
        });
        uiHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                RelativeLayout.LayoutParams layout = (RelativeLayout.LayoutParams) card_notification.getLayoutParams();
                layout.height = 0;
                layout.width = FrameLayout.LayoutParams.MATCH_PARENT;
                card_notification.setLayoutParams(layout);
            }
        },2000);
    }
}
