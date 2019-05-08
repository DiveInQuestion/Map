package com.example.a19360.map;

import java.io.Serializable;
import java.util.Date;

public class Songs implements Serializable
{
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    private String num;
    private String name;
    private int pictureId;
}