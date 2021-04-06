package ru.whybrawl.boulderrun;

import javax.swing.*;
import java.awt.*;

public class Monster {

    private int dx;
    private int dy;
    private int x = 0;
    private int y = 0;
    private int w;
    private int h;
    private Image image;

    public Monster() {

        loadImage();
    }

    private void loadImage() {

        ImageIcon ii = new ImageIcon("src/main/resources/snake.png");
        image = ii.getImage();

        w = image.getWidth(null);
        h = image.getHeight(null);
    }
    public void moveplus(int xd, int yd) {
        x += xd;
        y += yd;
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