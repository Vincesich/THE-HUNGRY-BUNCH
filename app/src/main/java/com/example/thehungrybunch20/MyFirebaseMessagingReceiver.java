package com.example.thehungrybunch20;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
public class MyFirebaseMessagingReceiver extends BroadcastReceiver {

    private static final String TAG = "MyFcmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            Log.d(TAG, "Received action: " + action);

            // Process the received action as needed
        }
    }
}
