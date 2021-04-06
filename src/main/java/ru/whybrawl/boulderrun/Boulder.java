package ru.whybrawl.boulderrun;

import javax.swing.*;
import java.awt.*;

public class Boulder {
    private int fallspeed = 0;
    private int dx;
    private int dy;
    private int x = 0;
    private int y = 0;
    private int w;
    private int h;
    private Image image;

    public Boulder() {

        loadImage();
    }

    private void loadImage() {

        ImageIcon ii = new ImageIcon("src/main/resources/boulder.png");
        image = ii.getImage();

        w = image.getWidth(null);
        h = image.getHeight(null);
    }

    public boolean move() {
        if(x != dx || y != dy){
            x = dx;
            y = dy;
            dx = x;
            dy = y;
            return true;
        }
        return false;
    }
    public void moveplus(int xd, int yd) {
        if(x+xd >= 0 && y+yd >= 0 && x+xd < 600 && y+yd < 600){
            x += xd;
            y += yd;
            fallspeed++;
        }

    }
    public void setFallspeed(int setfs){
        fallspeed = setfs;
    }

    public int getFallspeed() {
        return fallspeed;
    }

    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }
    public void setX(int pos) {
        x = pos;
    }

    public void setY(int pos) {
        y = pos;
    }

    public int getWidth() {

        return w;
    }

    public int getHeight() {

        return h;
    }

    public Image getImage() {

        return image;
    }
}