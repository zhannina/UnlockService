package com.example.zsarsenbayev.programmaticunlock;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

public class UnlockService extends Service {
    Thread myThread;

//    public UnlockService() {
//    }

    @Override
    public void onCreate() {
        super.onCreate();

        myThread = new MyThread();
        myThread.start();
//        myRunnable = new MyThread(myTimer);
//        myThread = new Thread(myRunnable, "my thread");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
  //      throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }


    //class
    //thread class, define run and while loop here
    public class MyThread extends Thread {

        public void run() {
            Log.d("THREAD", "running");
            while (true) {
                PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
                PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "UnlockService");
                wakeLock.acquire();
                KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
                KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("UnlockService");
                keyguardLock.disableKeyguard();
                wakeLock.release();
                Log.d("UnlockService", "started : " + System.currentTimeMillis());
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        myTimer.start();
//        myRunnable.run();


        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // TODO Auto-generated method stub
        Intent restartService = new Intent(getApplicationContext(),
                this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);

        //Restart the service once it has been killed android


        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +100, restartServicePI);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


//    class TimeDisplay extends TimerTask {
//
//        final UnlockService mSuperService;
//
//        TimeDisplay(UnlockService serviceInstance) {
//            super();
//            this.mSuperService = serviceInstance;
//        }
//
//        @Override
//        public void run() {
//            // run on another thread
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//
//                    Log.d("UnlockService", "service : " + System.currentTimeMillis());
//
//                    new CountDownTimer(15000, 1000) {
//
//                        boolean mIsOn = false;
//
//                        public void onTick(long millisUntilFinished) {
//                            if(!mIsOn) {
//                                mIsOn = true;
//                                PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
//                                PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "UnlockService");
//                                wakeLock.acquire();
//                                KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
//                                KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("UnlockService");
//                                keyguardLock.disableKeyguard();
//                                wakeLock.release();
//                                Log.d("UnlockService", "started : " + System.currentTimeMillis());
//                                Log.d("UnlockService", "misOn 1: " + mIsOn);
//                            }
//                            Log.d("UnlockService", "doing nothing on tick : " + System.currentTimeMillis());
//                            Log.d("UnlockService", "misOn 2: " + mIsOn);
//                        }
//
//                        public void onFinish() {
//                            Log.d("UnlockService", "finished: " + System.currentTimeMillis());
//                            Log.d("UnlockService", "misOn 3: " + mIsOn);
//                            this.start();
//                            mIsOn = false;
//                        }
//                    }.start();
//                }
//            });
//        }
//    }

//        mTimer.scheduleAtFixedRate(new TimeDisplay(this), 0, notify);   //Schedule task
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                myTimer.start();
//
//            }
//        }).start();

//        if (mTimer != null){ // Cancel if already existed
//            mTimer.cancel();
//        }
//        else {
//            mTimer = new Timer();//recreate new
//        }

//        myTimer =  new CountDownTimer(15000, 1000) {
//            public void onTick(long millisUntilFinished) {
//
//            }
//
//            public void onFinish() {
//                PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
//                PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "UnlockService");
//                wakeLock.acquire();
//                KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
//                KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("UnlockService");
//                keyguardLock.disableKeyguard();
//                wakeLock.release();
//                Log.d("UnlockService", "started : " + System.currentTimeMillis());
//                this.start();
//            }
//        };

    //    while(true){
//        timer =  new CountDownTimer(15000, 1000) {
//            public void onTick(long millisUntilFinished) {
//            }
//            public void onFinish() {
//                PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
//                PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "UnlockService");
//                wakeLock.acquire();
//                KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
//                KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("UnlockService");
//                keyguardLock.disableKeyguard();
//                wakeLock.release();
//                Log.d("UnlockService", "started : " + System.currentTimeMillis());
//                this.start();
//            }
//        }.start();
//    }
}
