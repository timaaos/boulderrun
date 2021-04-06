package ru.whybrawl.boulderrun;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main extends JFrame {
    public int width = 655;
    public int height = 700;
    public Board board;
    public Main() {

        initUI();
    }

    private void initUI() {
        board = new Board();
        add(board);
        setSize(width, height);
        JMenuBar bar = new JMenuBar();
        bar.add(createMenu());
        setJMenuBar(bar);
        setTitle("BoulderRun");
        ImageIcon ii = new ImageIcon("src/main/resources/icon.png");
        this.setIconImage(ii.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }
    private JMenu createMenu()
    {
        JMenu file = new JMenu("Actions");
        JMenuItem open = new JMenuItem("Reset");
        open.setIcon(new ImageIcon("src/main/resources/reset.png"));
        JMenuItem exit = new JMenuItem("Exit");
        exit.setIcon(new ImageIcon("src/main/resources/exit.png"));
        file.add(open);
        file.addSeparator();
        exit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        file.add(exit);

        open.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                board.reset();
            }
        });
        return file;
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Main ex = new Main();
            ex.setVisible(true);
        });
    }
}
