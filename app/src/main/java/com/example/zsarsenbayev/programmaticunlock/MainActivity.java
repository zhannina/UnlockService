package com.example.zsarsenbayev.programmaticunlock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button unlockServiceButton;
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unlockServiceButton = findViewById(R.id.unlockButton);

        unlockServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activateUnlockService();
            }
        });
    }

    private void activateUnlockService() {
        serviceIntent = new Intent(this, UnlockService.class);

        startService(serviceIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
    }
}
