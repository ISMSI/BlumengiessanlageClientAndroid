package com.example.blumengieanlage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class MainActivity extends AppCompatActivity {
    AppCompatActivity mainActivity = this;
    ExecutorService executorService = Executors.newFixedThreadPool(1);
    Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    SocketRepository socketRepository = new SocketRepository(executorService,mainThreadHandler);
    SocketViewModel socketViewModel = new SocketViewModel(socketRepository);

    Intent requestIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button clientOpen = findViewById( R.id.clientOpen);
        final TextView portField = findViewById(R.id.port);
        final TextView ipAdresseField = findViewById(R.id.ip_adresse);

        requestIntent = new Intent(this, RequestActivity.class);
        requestIntent.putExtra("socketViewModel", socketViewModel);

        clientOpen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int port;

                try {
                    port = Integer.parseInt(portField.getText().toString());
                } catch (Exception e)
                {
                    port = 3131;
                }

                SocketCallback<MyPi> socketCallback = new SocketCallback<MyPi>() {
                    @Override
                    public void onComplete(Result<MyPi> result) {
                        if (result instanceof Result.Success) {
                            System.out.println("Connected");
                            mainActivity.startActivity(requestIntent);
                        } else {
                            System.out.println("Connection error");
                        }
                    }
                };

                socketViewModel.makeOpenSocketRequest(socketCallback, mainActivity, requestIntent, ipAdresseField.getText().toString(), port);
            }
        });



    }
}