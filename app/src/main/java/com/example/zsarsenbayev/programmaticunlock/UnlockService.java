package com.example.zsarsenbayev.programmaticunlock;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class UnlockService extends Service {

    private UnlockReceiver unlockReceiver;
    public static final int notify = 15*60*1000;  //interval between two services(Here Service run every 15 Minutes)
    private Handler mHandler = new Handler();   //run on another Thread to avoid crash
    private Timer mTimer = null;    //timer handling

    //IntentFilter filter;


    public UnlockService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        unlockReceiver = new UnlockReceiver();
        if (mTimer != null) // Cancel if already existed
            mTimer.cancel();
        else
            mTimer = new Timer();   //recreate new
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
  //      throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mTimer.scheduleAtFixedRate(new TimeDisplay(this), 0, notify);   //Schedule task

        return START_STICKY;
    }


    class TimeDisplay extends TimerTask {

        final UnlockService mSuperService;

        TimeDisplay(UnlockService serviceInstance) {
            super();
            this.mSuperService = serviceInstance;
        }

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {

                    Log.d("UnlockService", "service : " + System.currentTimeMillis());

                    new CountDownTimer(5000, 1000) {

                        boolean mIsOn = false;

                        public void onTick(long millisUntilFinished) {
                            if(!mIsOn) {
                                mIsOn = true;
                                PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
                                PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "UnlockService");
                                wakeLock.acquire();
                                KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
                                KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("UnlockService");
                                keyguardLock.disableKeyguard();
                                wakeLock.release();
                                Log.d("UnlockService", "started : " + System.currentTimeMillis());
                            }
                            Log.d("UnlockService", "doing nothing on tick : " + System.currentTimeMillis());

                        }

                        public void onFinish() {
                            Log.d("UnlockService", "finished: " + System.currentTimeMillis());
                        }
                    }.start();
                }
            });
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
