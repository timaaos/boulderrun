package ru.whybrawl.boulderrun;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Man {

    private int dx;
    private int dy;
    private int x = 0;
    private int y = 0;
    private int w;
    private int h;
    private Image image;

    public Man() {

        loadImage();
    }

    private void loadImage() {

        ImageIcon ii = new ImageIcon("src/main/resources/man.png");
        image = ii.getImage();

        w = image.getWidth(null);
        h = image.getHeight(null);
    }

    public boolean needmove() {
        if(x != dx || y != dy){
            return true;
        }
        return false;
    }
    public void setDx(int toset){
        dx = toset;
    }
    public void setDy(int toset){
        dy = toset;
    }
    public void move() {
        if(dx > 650 || dy > 650 || dx < 0 || dy < 0){

        }else{
            x = dx;
            y = dy;
            dx = x;
            dy = y;
        }

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
    public int getToX() {

        return dx;
    }

    public int getToY() {

        return dy;
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

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = x;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = x;
        }

        if (key == KeyEvent.VK_UP) {
            dy = y;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = y;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = x-32;
        }else if (key == KeyEvent.VK_RIGHT) {
            dx = x+32;
        }else if (key == KeyEvent.VK_UP) {
            dy = y-32;
        }else if (key == KeyEvent.VK_DOWN) {
            dy = y+32;
        }
    }
}