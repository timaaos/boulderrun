package ru.whybrawl.boulderrun;

import javax.swing.*;
import java.awt.*;

public class Diamond {

    private int dx;
    private int dy;
    private int x = 0;
    private int y = 0;
    private int w;
    private int h;
    private Image image;

    public Diamond() {

        loadImage();
    }

    private void loadImage() {

        ImageIcon ii = new ImageIcon("src/main/resources/diamond.png");
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
        if(x+xd >= 0 && y+yd >= 0 && x+xd < 640 && y+yd < 640){
            x += xd;
            y += yd;
        }

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