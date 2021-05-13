package com.example.blumengieanlage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.Observer;

import android.app.Activity;
import android.content.Context;
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
    SocketViewModel socketViewModel;
    Context myContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        socketViewModel = new SocketViewModel(getApplication());

        final Observer<MyPi> myPiObserver = new Observer<MyPi>() {
            @Override
            public void onChanged(MyPi myPi) {
                if (myPi.getPi() == 1)
                {
                    Intent intent = new Intent(myContext, RequestActivity.class);
                    startActivity(intent);
                }
            }
        };

        socketViewModel.connected.observe(this, myPiObserver);

        setContentView(R.layout.activity_main);

        final Button clientOpen = findViewById( R.id.clientOpen);
        final TextView portField = findViewById(R.id.port);
        final TextView ipAdresseField = findViewById(R.id.ip_adresse);

        clientOpen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int port;

                try {
                    port = Integer.parseInt(portField.getText().toString());
                } catch (Exception e)
                {
                    port = 3131;
                }


                socketViewModel.makeOpenSocketRequest(ipAdresseField.getText().toString(), port);
            }
        });



    }
}