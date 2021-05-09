package com.example.blumengieanlage;

import java.io.Serializable;

public class MyPi implements Serializable {

    private double pi;

    public MyPi(double pi)
    {
        this.pi = pi;
    }

    public double getPi() {
        return pi;
    }
}
