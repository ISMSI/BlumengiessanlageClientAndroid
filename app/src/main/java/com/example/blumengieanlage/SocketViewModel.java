package com.example.blumengieanlage;

import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.net.Socket;

public class SocketViewModel implements Serializable {

    private final SocketRepository socketRepository;
    Socket socket;

    public SocketViewModel(SocketRepository socketRepository) {
        this.socketRepository = socketRepository;
    }

    public void makeOpenSocketRequest(final SocketCallback<MyPi> socketCallback, final AppCompatActivity mainActivity, final Intent requestIntent, final String address, final int port) {
        socketRepository.makeOpenSocketRequest(socketCallback,
                socket,
                address,
                port);
    }

    public void makeCloseSocketRequest() {
        socketRepository.makeCloseSocketRequest(new SocketCallback<MyPi>() {
            @Override
            public void onComplete(Result<MyPi> result) {
                if (result instanceof Result.Success) {
                   System.out.println("Disconnected");
                } else {
                   System.out.println("Disconnected error");
                }
            }
        },
                socket);
    }
}
