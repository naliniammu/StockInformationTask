package com.example.stockinformationtask;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.Toast;

import com.example.stockinformationtask.activities.Stock_Details_Activity;

public class UpdateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(Constants.LOCAL_BROADCAST_ACTION.equals(action)) {
            String fromActivity = intent.getStringExtra(Constants.LOCAL_BROADCAST_SOURCE);
            Toast.makeText(context, "Receiver one receive broadcast from " + fromActivity, Toast.LENGTH_LONG).show();
        }

    }
}
