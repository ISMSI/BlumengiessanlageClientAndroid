package com.example.blumengieanlage;

import android.os.Handler;

import java.util.concurrent.Executor;

interface SocketCallback<T> {
    void onComplete(Result<T> result);
}

public class SocketRepository {

    private final Executor executor;
    private final Handler resultHandler;

    public SocketRepository(Executor executor, Handler resultHandler)
    {
        this.executor = executor;
        this.resultHandler = resultHandler;
    }

    public void makeSocketRequest( final SocketCallback<MyPi> socketCallback) {
        executor.execute( new Runnable()  {
            @Override
            public void run() {
                try {
                    Result<MyPi> result = openSocket();
                    socketCallback.onComplete(result);
                } catch (Exception e) {
                    Result<MyPi> errorResult = new Result.Error<>(e);
                    socketCallback.onComplete(errorResult);
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

    public Result<MyPi> openSocket()
    {
        try {
            MyPi res;
            double pi = 0;
            Boolean plus = true;

            for (int i = 1; i < 500000; i=i+2) {
                if(plus)
                {
                    pi = pi + (4/i);
                    plus = false;
                } else {
                    pi = pi - (4/i);
                    plus = true;
                }
            }



            res = new MyPi(pi);

            return new Result.Success<MyPi>(res);
        } catch (Exception e) {
            return new Result.Error<MyPi>(e);
        }
    }
}
