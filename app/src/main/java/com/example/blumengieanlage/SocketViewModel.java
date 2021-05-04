package com.example.blumengieanlage;

public class SocketViewModel {

    private final SocketRepository socketRepository;

    public SocketViewModel(SocketRepository socketRepository) {
        this.socketRepository = socketRepository;
    }

    public void makeSocketRequest() {
        socketRepository.makeSocketRequest(new SocketCallback<MyPi>() {
            @Override
            public void onComplete(Result<MyPi> result) {
                if (result instanceof Result.Success) {

                } else {

                }
            }
        });
    }
}
