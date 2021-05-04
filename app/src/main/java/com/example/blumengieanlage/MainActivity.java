package com.example.blumengieanlage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class MainActivity extends AppCompatActivity {

    ExecutorService executorService = Executors.newFixedThreadPool(1);
    Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    SocketRepository socketRepository = new SocketRepository(executorService,mainThreadHandler);
    SocketViewModel socketViewModel = new SocketViewModel(socketRepository);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}