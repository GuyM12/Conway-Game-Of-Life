import jdk.nashorn.internal.ir.Block;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game extends JPanel implements MouseListener{

    private int[][] map;
    private int[][] nieboor;
    private int gen;

    private int block = 30;

    private JFrame frame;


    private int width;
    private int height;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (int i = 0; i < map.length; i++) {
            g.drawLine(0, block*i, getWidth(), block*i);
            g.drawLine(block*i, 0 , block*i, getHeight());
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if(map[i][j] == 1) {
                    g.fillRect(block * i, block * j, block, block);
                    System.out.println("drawing rect");
                }/*else
                    g.drawString(i + ", " + j, block*i,block*j);
                */}
        }
    }

    public Game(int length, int gen) throws InterruptedException {
        map = new int[length][length];
        nieboor = new int[length][length];
        width = length*block + block/2;
        height = length*block + 5*(block/4);
        //random();// making a random map
        blank();

        init(length); //Initiating frame

        this.gen = gen;
        Scanner sc = new Scanner(System.in);
        while(!sc.next().equals("stop")){
            //Waiting...
        }

        while (gen >= 0) {
            scan(); //scanning neighboors
            System.out.println("GENERATION: " + (this.gen-gen));
            TimeUnit.SECONDS.sleep(1);

            //Updating new Generation
            for (int i = 0; i < nieboor.length; i++) {
                for (int j = 0; j < nieboor.length; j++) {
                    if (nieboor[i][j] == 3) {
                        map[i][j] = 1;
                        //frame.getGraphics().setClip(block * i, block * j, block, block);
                    }else if (map[i][j] == 1 && (nieboor[i][j] > 3 || nieboor[i][j] < 2)) {
                        map[i][j] = 0;
                        //frame.getGraphics().setClip(block * i, block * j, block, block);
                        //System.out.println("[ " + i + ", " + j + "] n = " + nieboor[i][j]);
                    }
                }
            }

            //repainting
            frame.invalidate();
            frame.validate();
            frame.repaint();

            //scan();
            gen--;
        }
    }

    private void init(int length) {

        frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width,height) ;

        frame.add(this);

        frame.setVisible(true);

        addMouseListener(this);
    }

    private void random() {
        Random rnd = new Random();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if(rnd.nextBoolean())
                    map[i][j] = 1;
                if(map[i][j] == 1)
                    System.out.println(i + "," + j);
            }
        }
    }

    private void blank(){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j] = 0;
            }
        }
    }

    private void scan() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (i == 0 && j == 0)
                    nieboor[i][j] = map[i + 1][j] + map[i][j + 1] + map[i + 1][j + 1]; //down, left, both.
                else if (i == map.length - 1 && j == 0)
                    nieboor[i][j] = map[i - 1][j] + map[i][j + 1] + map[i - 1][j + 1]; //up, right, both.
                else if (i == 0 && j == map.length - 1)
                    nieboor[i][j] = map[i][j - 1] + map[i + 1][j] + map[i + 1][j - 1]; //left, down, both.
                else if (i == map.length - 1 && j == map.length - 1)
                    nieboor[i][j] = map[i - 1][j] + map[i][j - 1] + map[i - 1][j - 1]; //up, left, both.
                else if (i == 0)
                    nieboor[i][j] = map[i + 1][j] + map[i + 1][j - 1] + map[i + 1][j + 1] + map[i][j - 1] + map[i][j + 1]; //down, down-left, down-right, left, right.
                else if (i == map.length - 1)
                    nieboor[i][j] = map[i - 1][j] + map[i - 1][j - 1] + map[i - 1][j + 1] + map[i][j - 1] + map[i][j + 1]; //up, up-left, up-right, left, right.
                else if (j == 0)
                    nieboor[i][j] = map[i + 1][j] + map[i - 1][j] + map[i + 1][j + 1] + map[i][j + 1] + map[i - 1][j + 1]; //down, up, down-right, right, right-up.
                else if (j == map.length - 1)
                    nieboor[i][j] = map[i - 1][j] + map[i - 1][j - 1] + map[i][j - 1] + map[i + 1][j - 1] + map[i + 1][j]; //up, up-left, left, down-left, down.
                else
                    nieboor[i][j] = map[i + 1][j] + map[i][j + 1] + map[i - 1][j] + map[i][j - 1] + map[i - 1][j - 1] + map[i + 1][j + 1] + map[i - 1][j + 1] + map[i + 1][j - 1]; //down, right, up, left, up-left, down-right, up-right, down-left.
            }
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
       /* System.out.println("PRESSINGGGGGGGGGGG");
        isMousePressed = true;
        while(isMousePressed && isMouseInScreen){
            //System.out.println("INSINDEEEEEE");
            map[e.getX()/block][e.getY()/block] = 1;
            //repainting
            frame.invalidate();
            frame.validate();
            frame.repaint();

        }*/
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(map[e.getX()/block][e.getY()/block] == 1)
            map[e.getX()/block][e.getY()/block] = 0;
        else
            map[e.getX()/block][e.getY()/block] = 1;

        frame.invalidate();
        frame.validate();
        frame.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}


