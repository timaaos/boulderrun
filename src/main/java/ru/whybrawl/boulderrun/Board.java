package ru.whybrawl.boulderrun;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private java.util.Timer phys;
    private Man man;
    private List<Dirt> dirts;
    private List<Boulder> boulders;
    private List<Monster> monsters;
    private List<Diamond> diamonds;
    private List<Diamond> eatdiamonds;
    private final int DELAY = 10;

    public Board() {

        initBoard();
    }
    public void reset(){
        phys.cancel();
        timer.stop();
        initBoard();
    }
    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        dirts = new ArrayList<>();
        boulders = new ArrayList<>();
        monsters = new ArrayList<>();
        diamonds = new ArrayList<>();
        eatdiamonds = new ArrayList<>();
        man = new Man();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Dirt dirtloc = new Dirt();
                if(i*32 == man.getX() && j*32 == man.getY()){
                    continue;
                }
                dirtloc.setX(i*32);
                dirtloc.setY(j*32);
                dirts.add(dirtloc);
            }
        }
        for (int i = 0; i < 5; i++) {
            Diamond diamondloc = new Diamond();
            int x = new Random().nextInt(20);
            int y = new Random().nextInt(20);
            if(x*32 == man.getX() && y*32 == man.getY()){
                continue;
            }
            diamondloc.setX(x*32);
            diamondloc.setY(y*32);
            deleteAnything(x*32,y*32);
            diamonds.add(diamondloc);
        }
        int randint = new Random().nextInt(15)+5;
        for (int i = 0; i < randint; i++) {
            int x = new Random().nextInt(20);
            int y = new Random().nextInt(20);
            for (int j = 0; j < new Random().nextInt(5)+3; j++) {
                if(x*32==man.getX() && y*32==man.getY()){
                    continue;
                }
                Boulder dirtloc = new Boulder();
                dirtloc.setX(x*32);
                dirtloc.setY(y*32);
                boulders.add(dirtloc);
                deleteDirt(x*32,y*32);
                int randnum = new Random().nextInt(3);
                switch (randnum){
                    case 0:
                        x+=1;
                        break;
                    case 1:
                        x-=1;
                        break;
                    case 2:
                        y+=1;
                        break;
                    case 3:
                        y-=1;
                        break;
                }
            }
        }

        randint = new Random().nextInt(3)+2;
        for (int i = 0; i < randint; i++) {
            int x = new Random().nextInt(20);
            int y = new Random().nextInt(20);
            deleteDirt(x*32,y*32);
            Monster monloc = new Monster();
            monloc.setX(x*32);
            monloc.setY(y*32);
            monsters.add(monloc);
            for (int j = 0; j < new Random().nextInt(5)+2; j++) {
                if(x*32==man.getX() && y*32==man.getY()){
                    continue;
                }
                /*Boulder dirtloc = new Boulder();
                dirtloc.setX(x*32);
                dirtloc.setY(y*32);
                boulders.add(dirtloc);*/
                deleteDirt(x*32,y*32);
                deleteDirt(x*32,(y+1)*32);
                deleteDirt(x*32,(y-1)*32);
                deleteDirt((x+1)*32,y*32);
                deleteDirt((x-1)*32,y*32);
                int randnum = new Random().nextInt(3);
                switch (randnum){
                    case 0:
                        x+=1;
                        break;
                    case 1:
                        x-=1;
                        break;
                    case 2:
                        y+=1;
                        break;
                    case 3:
                        y-=1;
                        break;
                }
            }
        }
        timer = new Timer(DELAY, this);
        timer.start();
        phys = new java.util.Timer();
        phys.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                for (Boulder boulder:boulders) {
                    if(!isBlocking(boulder.getX(),boulder.getY()+32)){
                        boulder.moveplus(0,32);
                    }else if(!isBlocking(boulder.getX()-32,boulder.getY()+32) && !isBlocking(boulder.getX()-32,boulder.getY()) && !isBlocking(boulder.getX()+32,boulder.getY()+32) && !isBlocking(boulder.getX()+32,boulder.getY())){
                        if(new Random().nextBoolean()){
                            boulder.moveplus(32,0);
                        }else{
                            boulder.moveplus(-32,0);
                        }
                    }else if(!isBlocking(boulder.getX()+32,boulder.getY()+32) && !isBlocking(boulder.getX()+32,boulder.getY())){
                        boulder.moveplus(32,0);
                    }else if(!isBlocking(boulder.getX()-32,boulder.getY()+32) && !isBlocking(boulder.getX()-32,boulder.getY())){
                        boulder.moveplus(-32,0);
                    }else{
                        boulder.setFallspeed(0);
                    }
                    if(isPlayer(boulder.getX(),boulder.getY()) && boulder.getFallspeed() > 1){
                        Lose();
                    }
                    if(isMonster(boulder.getX(),boulder.getY())){
                        KillMonster(boulder.getX(),boulder.getY());
                    }
                    repaint(boulder.getX()-32,boulder.getY()-32,boulder.getWidth()+64,boulder.getHeight()+64);
                }
                for (Diamond diamond:diamonds) {
                    int xd = 0,yd = 0;
                    if(!isBlocking(diamond.getX(),diamond.getY()+32)){
                        yd = 32;
                    }
                    if(!isBlocking(diamond.getX()-32,diamond.getY()+32) && !isBlocking(diamond.getX()-32,diamond.getY()) && !isBlocking(diamond.getX()+32,diamond.getY()+32) && !isBlocking(diamond.getX()+32,diamond.getY())){
                        if(new Random().nextBoolean()){
                            xd = 32;
                        }else{
                            xd = -32;
                        }
                    }
                    if(!isBlocking(diamond.getX()+32,diamond.getY()+32) && !isBlocking(diamond.getX()+32,diamond.getY())){
                        xd = 32;
                    }
                    if(!isBlocking(diamond.getX()-32,diamond.getY()+32) && !isBlocking(diamond.getX()-32,diamond.getY())){
                        xd = -32;
                    }
                    diamond.moveplus(xd,yd);
                    repaint(diamond.getX()-32,diamond.getY()-32,diamond.getWidth()+64,diamond.getHeight()+64);
                }
                for(Monster monster:monsters){
                    List<Integer> pos = new ArrayList<>();
                    pos.add(new Random().nextInt(3)-1);
                    pos.add(new Random().nextInt(3)-1);
                    if(!isBlocking(monster.getX()+pos.get(0)*32,monster.getY()+pos.get(1)*32)){
                        monster.moveplus(pos.get(0)*32,pos.get(1)*32);
                        repaint(monster.getX()-32,monster.getY()-32,monster.getWidth()+64,monster.getHeight()+64);
                    }
                }
                if(isMonster(man.getX(),man.getY())){
                    Lose();
                }
            }
        }, 250,250);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        try {
            for (Boulder boulder:boulders) {
                g2d.drawImage(boulder.getImage(), boulder.getX(),
                        boulder.getY(), this);
            }
            for (Dirt dirt:dirts) {
                g2d.drawImage(dirt.getImage(), dirt.getX(),
                        dirt.getY(), this);
            }
            for (Diamond diamond:diamonds) {
                g2d.drawImage(diamond.getImage(), diamond.getX(),
                        diamond.getY(), this);
            }
            for (Monster monster:monsters) {
                g2d.drawImage(monster.getImage(), monster.getX(),
                        monster.getY(), this);
            }
        }catch (java.util.ConcurrentModificationException exception){
            System.out.println("error was here");
        }

        g2d.drawImage(man.getImage(), man.getX(),
                man.getY(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        step();
    }

    private void step() {

        if(man.needmove()){
            if(!isBoulder(man.getToX(),man.getToY()) && man.getToX() >= 0 && man.getToY() >= 0){
                man.move();
                deleteDirt(man.getX(),man.getY());
            }else{
                man.setDx(man.getX());
                man.setDy(man.getY());
            }
        }
        if(isDiamond(man.getX(),man.getY())){
            eatDiamond(man.getX(),man.getY());
            if(diamonds.size() == 0){
                Win();
            }
        }
        repaint(man.getX()-32, man.getY()-32,
                man.getWidth()+64, man.getHeight()+64);
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            man.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            man.keyPressed(e);
        }
    }
    public void deleteDirt(int x, int y){
        Dirt dirttoremove = new Dirt();
        for (Dirt dirt:dirts) {
            if(dirt.getX() == x && dirt.getY() == y){
                dirttoremove = dirt;
            }
        }
        if(dirts.contains(dirttoremove)){
            dirts.remove(dirttoremove);
        }
    }
    public void deleteAnything(int x, int y){
        Dirt dirttoremove = new Dirt();
        for (Dirt dirt:dirts) {
            if(dirt.getX() == x && dirt.getY() == y){
                dirttoremove = dirt;
            }
        }
        if(dirts.contains(dirttoremove)){
            dirts.remove(dirttoremove);
        }
        Boulder bouldertoremove = new Boulder();
        for (Boulder boulder:boulders) {
            if(boulder.getX() == x && boulder.getY() == y){
                bouldertoremove = boulder;
            }
        }
        if(boulders.contains(bouldertoremove)){
            boulders.remove(bouldertoremove);
        }
    }
    public void KillMonster(int x, int y){
        Monster monstertokill = new Monster();
        for (Monster monster:monsters) {
            if(monster.getX() == x && monster.getY() == y){
                monstertokill = monster;
            }
        }
        if(monsters.contains(monstertokill)){
            monsters.remove(monstertokill);
        }

    }
    public void eatDiamond(int x, int y){
        Diamond diamondtoeat = new Diamond();
        for (Diamond diamond:diamonds) {
            if(diamond.getX() == x && diamond.getY() == y){
                diamondtoeat = diamond;
            }
        }
        if(diamonds.contains(diamondtoeat)){
            diamonds.remove(diamondtoeat);
            eatdiamonds.add(diamondtoeat);
        }

    }
    public boolean isBlocking(int x, int y){
        try{
            for (Dirt dirt:dirts) {
                if(dirt == null){
                    continue;
                }
                if(dirt.getX() == x && dirt.getY() == y){
                    return true;
                }
            }
        }catch (java.util.NoSuchElementException exception){
            System.out.println("nosuchelement exception");
        }
        for (Boulder boulder:boulders) {
            if(boulder.getX() == x && boulder.getY() == y){
                return true;
            }
        }
        for (Diamond boulder:diamonds) {
            if(boulder.getX() == x && boulder.getY() == y){
                return true;
            }
        }
        return false;
    }
    public boolean isPlayer(int x, int y){
        if(man.getX() == x && man.getY() == y){
            return true;
        }
        return false;
    }
    public boolean isMonster(int x, int y){
        for (Monster monster:monsters) {
            if(monster.getX() == x && monster.getY() == y){
                return true;
            }
        }
        return false;
    }
    public boolean isBoulder(int x, int y){
        for (Boulder boulder:boulders) {
            if(boulder.getX() == x && boulder.getY() == y){
                return true;
            }
        }
        return false;
    }
    public boolean isDiamond(int x, int y){
        for (Diamond diamond:diamonds) {
            if(diamond.getX() == x && diamond.getY() == y){
                return true;
            }
        }
        return false;
    }
    public void Lose(){
        JOptionPane.showMessageDialog(null,"You Lose!","BoulderRun",JOptionPane.ERROR_MESSAGE);
        reset();
    }
    public void Win(){
        Object[] options = {"Restart game!",
                "Exit!"};
        int n = JOptionPane.showOptionDialog(null,
                "You won!",
                "BoulderRun",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title
        if(n == 0){
            reset();
        }else if(n == 1){
            System.exit(0);
        }

    }
    public List<Integer> snakeAIpath(int x, int y){
        List<List<Integer>> poses = new ArrayList<>();
        poses.add(posList(x,y));
        boolean stop = true;
        while (stop){
            for (int i = 0; i < poses.size(); i++) {
                List<Integer> ls = poses.get(i);
                if(containsTwo(Collections.singletonList(poses),ls)){
                    poses.remove(ls);
                }
                if(isBlocking(ls.get(0)*32,ls.get(1)*32)){
                    poses.remove(ls);
                }
                if(isPlayer(ls.get(0)*32,ls.get(1)*32)){
                    return poses.get(1);
                }
                if(ls.get(0)*32 < 0 || ls.get(1)*32 < 0){
                    poses.remove(ls);
                }
                poses.add(posList(ls.get(0)+1,ls.get(1)));
                poses.add(posList(ls.get(0)-1,ls.get(1)));
                poses.add(posList(ls.get(0),ls.get(1)-1));
                poses.add(posList(ls.get(0),ls.get(1)+1));
            }
        }
        return poses.get(1);
    }
    public boolean containsTwo(List<Object> list, Object obj){
        int containsnum = 0;
        for (Object objls:list) {
            if (objls.equals(obj)){
                containsnum++;
            }
        }
        if(containsnum > 1){
            return true;
        }
        return false;
    }
    public List<Integer> posList(int x, int y){
        List<Integer> pos = new ArrayList<>();
        pos.add(x);
        pos.add(y);
        return pos;
    }

}