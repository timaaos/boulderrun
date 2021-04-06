package ru.whybrawl.boulderrun;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Dirt {
    private int x = 40;
    private int y = 60;
    private int w;
    private int h;
    private Image image;

    public Dirt() {

        loadImage();
    }

    private void loadImage() {

        ImageIcon ii = new ImageIcon("src/main/resources/dirt.png");
        image = ii.getImage();

        w = image.getWidth(null);
        h = image.getHeight(null);
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