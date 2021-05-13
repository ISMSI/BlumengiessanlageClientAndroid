package com.example.blumengieanlage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class RequestActivity extends AppCompatActivity {

    SocketViewModel socketViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        socketViewModel = new SocketViewModel(getApplication());

    }

    @Override
    public void onBackPressed() {
        socketViewModel.makeCloseSocketRequest();
        System.out.println("disconnect");
        finish();
    }

    @Override
    public void onDestroy()
    {
        socketViewModel.makeCloseSocketRequest();
        System.out.println("disconnect");
        super.onDestroy();
    }
}