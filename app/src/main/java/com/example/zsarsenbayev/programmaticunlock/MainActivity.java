package com.example.zsarsenbayev.programmaticunlock;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button unlockServiceButton;
    Intent serviceIntent;
    // handler needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unlockServiceButton = findViewById(R.id.unlockButton);

        unlockServiceButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                activateUnlockService();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void activateUnlockService() {
//        serviceIntent = new Intent(this, TestJobService.class);
//
//        startService(serviceIntent);

        ComponentName componentName = new ComponentName(this, TestJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(12, componentName)
                .setMinimumLatency(10000)
                .setOverrideDeadline(12000)
//                .setPeriodic(20000)
//                .setPersisted(true)
//                .setRequiresBatteryNotLow(false)
                .setRequiresCharging(false)
                .build();


        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = jobScheduler.schedule(jobInfo);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d("SCHEDULE", "Job scheduled!");
        } else {
            Log.d("SCHEDULE", "Job not scheduled");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
    }
}
