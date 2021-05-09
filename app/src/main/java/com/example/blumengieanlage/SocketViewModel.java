package com.example.blumengieanlage;

import android.widget.TextView;

public class SocketViewModel {

    private final SocketRepository socketRepository;

    public SocketViewModel(SocketRepository socketRepository) {
        this.socketRepository = socketRepository;
    }

    public void makeSocketRequest(final TextView textView, int iterations) {
        socketRepository.makeSocketRequest(new SocketCallback<MyPi>() {
            @Override
            public void onComplete(Result<MyPi> result) {
                if (result instanceof Result.Success) {
                    MyPi pi = ((Result.Success<MyPi>) result).data;


                    textView.setText(Double.toString(pi.getPi()));
                } else {
                    textView.setText("Error");
                }
            }
        },
                iterations);
    }
}
