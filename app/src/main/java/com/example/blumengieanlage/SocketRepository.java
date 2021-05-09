package com.example.blumengieanlage;

import android.os.Handler;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;

interface SocketCallback<T> {
    void onComplete(Result<T> result);
}

public class SocketRepository implements Serializable {

    private final Executor executor;
    private final Handler resultHandler;

    public SocketRepository(Executor executor, Handler resultHandler)
    {
        this.executor = executor;
        this.resultHandler = resultHandler;
    }

    public void makeOpenSocketRequest(final SocketCallback<MyPi> socketCallback, final Socket socket, final String address, final int port) {
        executor.execute( new Runnable()  {
            @Override
            public void run() {
                try {
                    Result<MyPi> result = openSocket(socket,address,port);
                    notifyResult(result, socketCallback);
                } catch (Exception e) {
                    Result<MyPi> errorResult = new Result.Error<>(e);
                    notifyResult(errorResult, socketCallback);
                }
            }

        });
    }

    public void makeCloseSocketRequest(final SocketCallback<MyPi> socketCallback, final Socket socket) {
        executor.execute( new Runnable()  {
            @Override
            public void run() {
                try {
                    Result<MyPi> result = closeSocket(socket);
                    notifyResult(result, socketCallback);
                } catch (Exception e) {
                    Result<MyPi> errorResult = new Result.Error<>(e);
                    notifyResult(errorResult, socketCallback);
                }
            }

        });
    }

    private void notifyResult(
            final Result<MyPi> result,
            final SocketCallback<MyPi> callback
    ) {
        resultHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onComplete(result);
            }
        });
    }

    private Result<MyPi> openSocket(Socket socket, String address, int port)
    {
        try {
            socket = new Socket(InetAddress.getByName(address), port);
        } catch (UnknownHostException eHost) {
            System.out.println(eHost.getMessage());
            return new Result.Error<>(eHost);
        } catch (IOException eIo) {
            System.out.println(eIo.getMessage());
            return new Result.Error<>(eIo);
        }
        return new Result.Success<MyPi>(new MyPi(3.14));
    }

    private Result<MyPi> closeSocket(Socket socket)
    {
        try {
            socket.close();
        } catch (IOException eIo) {
            System.out.println(eIo.getMessage());
            return new Result.Error<>(eIo);
        }
        return new Result.Success<MyPi>(new MyPi(3.14));
    }
}
