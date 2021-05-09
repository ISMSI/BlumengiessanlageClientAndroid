package com.example.blumengieanlage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class MainActivity extends AppCompatActivity {

    ExecutorService executorService = Executors.newFixedThreadPool(1);
    Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    SocketRepository socketRepository = new SocketRepository(executorService,mainThreadHandler);
    SocketViewModel socketViewModel = new SocketViewModel(socketRepository);
    int iterations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button clientOpen = findViewById( R.id.clientOpen);
        final TextView port = findViewById(R.id.port);
        final TextView ipAdresse = findViewById(R.id.ip_adresse);

        clientOpen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                try {
                    iterations = Integer.parseInt( ipAdresse.getText().toString() );
                } catch (Exception e)
                {
                    iterations = 500000;
                    e.printStackTrace();
                }

                socketViewModel.makeSocketRequest(port, iterations);
            }
        });
    }
}