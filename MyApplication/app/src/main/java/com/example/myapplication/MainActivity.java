package com.example.myapplication;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.function.functiontest.aidl.ICompute;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ICompute iCompute;
    private final String TAG = "AppCompatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.btn_aidl);
        button.setOnClickListener(this);
        Intent intent = new Intent();
        intent.setAction("com.xqz.apppayprovider.MyService");
        intent.setPackage("com.function.functiontest");
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
        Log.d(TAG, "onCreate: ");
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iCompute = ICompute.Stub.asInterface(service);
            Log.d(TAG, "onServiceConnected: ");
            Toast.makeText(MainActivity.this, "onServiceConnected", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
            Toast.makeText(MainActivity.this, "onServiceDisconnected", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_aidl) {
            try {
                if (iCompute == null) {
                    Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
                    return;
                }
                int number = iCompute.add(9, 3);
                Toast.makeText(this, "number" + number, Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
                Toast.makeText(this, "erro", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
