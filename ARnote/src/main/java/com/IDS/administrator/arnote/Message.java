package com.IDS.administrator.arnote;


public class Message {

    private double locationX; //geography location
    private double locationY; //geography location
    private String mes;
    private int lifeTime;
    //private GLRenderer glr;
    private int _color;
    private int index;


    public Message()
    {
        this.lifeTime = 600;
    }

    public Message(double locationX, double locationY, String InputMes, int ST, int color) {

        this.locationX = locationX;
        this.locationY = locationY;
        this.mes = InputMes;
        this.lifeTime = ST;
        _color = color;

    }

    public Message(double locationX, double locationY, String InputMes, int color) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.mes = InputMes;
        this.lifeTime = 600;
        _color = color;
    }

    public Message(double locationX, double locationY, String InputMes) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.mes = InputMes;
        this.lifeTime = 600;
    }

    public Message(Message externalMes)
    {
        this.mes = externalMes.mes;
        this.locationX = externalMes.locationX;
        this.locationY = externalMes.locationY;
        this._color = externalMes._color;
        this.lifeTime = externalMes.lifeTime;
        this.index = index;

    }


    public void editMessage(String InputMes){
        this.mes = InputMes;
    }

    public void setColor(int color) {this._color = color; }

    public void setLifeTime(int ST) {this.lifeTime = ST;}


    public String getMessage(){ return this.mes;}

    public double getLocationX() {
        return locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public int getIndex()
    {
        return this.index;
    }

    public int getColor()
    {
        return this._color;

    }




}
