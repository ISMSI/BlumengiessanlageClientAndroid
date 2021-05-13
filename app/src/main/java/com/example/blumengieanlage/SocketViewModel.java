package com.example.blumengieanlage;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import java.io.Serializable;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketViewModel extends AndroidViewModel {

    ExecutorService executorService;
    Handler mainThreadHandler;
    private final SocketRepository socketRepository;
    Socket socket;
    public MutableLiveData<MyPi> connected;


    public SocketViewModel(Application application) {
        super(application);
        connected = new MutableLiveData<MyPi>();
        connected.postValue(new MyPi(0));
        executorService = Executors.newFixedThreadPool(1);
        mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        this.socketRepository = new SocketRepository(executorService, mainThreadHandler);
    }

    public void makeOpenSocketRequest(final String address, final int port) {
        socketRepository.makeOpenSocketRequest(new SocketCallback<MySocket>() {
            @Override
            public void onComplete(Result<MySocket> result) {
                if (result instanceof Result.Success) {
                    socket = ((Result.Success<MySocket>) result).data.getSocket();
                    System.out.println("Connected");
                    connected.postValue(new MyPi(1));

                } else {
                    System.out.println("Connection error");
                }
            }
        },
                socket,
                address,
                port);
    }

    public void makeCloseSocketRequest() {
        socketRepository.makeCloseSocketRequest(new SocketCallback<MySocket>() {
            @Override
            public void onComplete(Result<MySocket> result) {
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
