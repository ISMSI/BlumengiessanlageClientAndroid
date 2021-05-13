package com.example.blumengieanlage;

import java.net.Socket;

public class MySocket {
    private Socket socket;

    public MySocket(Socket socket)
    {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
