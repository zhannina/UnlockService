package com.example.zsarsenbayev.programmaticunlock;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.util.Log;

/**
 * Created by zsarsenbayev on 5/21/18.
 */

public class UnlockReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, Intent intent) {

        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "UnlockService");
                wakeLock.acquire();
                KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("UnlockService");
                keyguardLock.disableKeyguard();
                Log.d("UnlockService", "started : " + System.currentTimeMillis());
            }

            public void onFinish() {
                Log.d("UnlockService", "finished: " + System.currentTimeMillis());
            }
        }.start();

    }

}
