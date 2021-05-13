package com.example.blumengieanlage;

import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    public void makeOpenSocketRequest(final SocketCallback<MySocket> socketCallback, final Socket socket, final String address, final int port) {
        executor.execute( new Runnable()  {
            @Override
            public void run() {
                try {
                    Result<MySocket> result = openSocket(socket,address,port);
                    notifyResult(result, socketCallback);
                } catch (Exception e) {
                    Result<MySocket> errorResult = new Result.Error<>(e);
                    notifyResult(errorResult, socketCallback);
                }
            }

        });
    }

    public void makeCloseSocketRequest(final SocketCallback<MySocket> socketCallback, final Socket socket) {
        executor.execute( new Runnable()  {
            @Override
            public void run() {
                try {
                    Result<MySocket> result = closeSocket(socket);
                    notifyResult(result, socketCallback);
                } catch (Exception e) {
                    Result<MySocket> errorResult = new Result.Error<>(e);
                    notifyResult(errorResult, socketCallback);
                }
            }

        });
    }

    private void notifyResult(
            final Result<MySocket> result,
            final SocketCallback<MySocket> callback
    ) {
        resultHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onComplete(result);
            }
        });
    }

    private Result<MySocket> openSocket(Socket socket, String address, int port)
    {
        try {
            socket = new Socket(InetAddress.getByName(address), port);

            socket.setKeepAlive(true);

            /*StringBuilder sb = new StringBuilder();
            try {
                OutputStream out = socket.getOutputStream();
                out.write(("Test123").getBytes());

                System.out.println("Input");
                InputStream in = socket.getInputStream();
                byte buf[] = new byte[256];
                int nbytes;
                System.out.println("while");
                while ((nbytes = in.read(buf)) != -1) {
                    System.out.println(".");
                    sb.append(new String(buf, 0, nbytes));
                }
                System.out.println(sb.toString());



            } catch (IOException eIo) {
                System.out.println(eIo.getMessage());
            }*/


        } catch (UnknownHostException eHost) {
            System.out.println(eHost.getMessage());
            return new Result.Error<>(eHost);
        } catch (IOException eIo) {
            System.out.println(eIo.getMessage());
            return new Result.Error<>(eIo);
        }
        return new Result.Success<MySocket>(new MySocket(socket));
    }

    private Result<MySocket> closeSocket(Socket socket)
    {
        try {
            socket.setKeepAlive(false);
            socket.close();
        } catch (IOException eIo) {
            System.out.println(eIo.getMessage());
            return new Result.Error<>(eIo);
        }
        return new Result.Success<MySocket>(null);
    }
}
