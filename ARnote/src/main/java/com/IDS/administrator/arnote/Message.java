package com.IDS.administrator.arnote;

public class Message {

    private double locationX; //geography location
    private double locationY; //geography location
    private String mes;
    private int lefeTime;
    //private GLRenderer glr;
    private float[] _color = new float[4];

    /*
    public final float[] blue = {0.f,0.f,1.0f,1.0f};
    public final float[] gnree = {0.f,1.f,0.0f,1.0f};
    public final float[] red = {1.f,0.f,0.0f,1.0f};
    public final float[] white = {1.f,1.f,1.0f,1.0f};
    */

    public Message()
    {
        this.lefeTime = 600;
    }

    public Message(double locationX, double locationY, String InputMes, int ST, float[] color) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.mes = InputMes;
        this.lefeTime = ST;
        _color = color;

    }

    public Message(double locationX, double locationY, String InputMes, float[] color) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.mes = InputMes;
        this.lefeTime = 600;
        _color = color;
    }

    public Message(double locationX, double locationY, String InputMes) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.mes = InputMes;
        this.lefeTime = 600;
    }

    public void editMessage(String InputMes){
        this.mes = InputMes;
    }

    public String getMessage(){ return this.mes;}

    public double getLocationX() {
        return locationX;
    }

    public double getLocationY() {
        return locationY;
    }


    public float[] getColor()
    {
        return this._color;

    }

    public void draw3D() // 画出3D 图形
    {

    }
}
