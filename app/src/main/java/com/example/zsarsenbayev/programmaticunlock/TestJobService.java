package com.example.zsarsenbayev.programmaticunlock;

import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.util.Log;

public class TestJobService extends JobService {
    public TestJobService() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
//        Intent serviceIntent = new Intent(getApplicationContext(), UnlockService.class);
//        getApplicationContext().startService(serviceIntent);
//        unlock();
        //Util.scheduleJob(getApplicationContext());
        startWorkOnNewThread();
        //unlock();
        //this.jobFinished(jobParameters, true);
        return true;
    }

    private void startWorkOnNewThread() {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    unlock();
                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //Log.d("SCHEDULE", "inside run");
            }
        }).start();
    }

    private void unlock() {
        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "UnlockService");
        wakeLock.acquire();
        KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("UnlockService");
        keyguardLock.disableKeyguard();
        wakeLock.release();
//        Log.d("UnlockService", "started : " + System.currentTimeMillis());
        Log.d("SCHEDULE", "inside unlock");
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("SCHEDULE", "inside onstop");
        return true;
    }

}
