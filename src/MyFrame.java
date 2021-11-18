import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyFrame extends JFrame implements KeyListener, ActionListener {
    final static int GAMEWIDTH = 1280;
    final static int GAMEHEIGHT = (int) (GAMEWIDTH * 0.5625);
    JButton clickMeButton;
    JLabel moneyCounter;
    JLabel background;

    MyFrame() {
            this.setTitle("Saxion Canvas Tycoon");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setBackground(Color.BLACK);
            this.setSize(GAMEWIDTH, GAMEHEIGHT);
            this.setLayout(null);

            //moneyCounter
            moneyCounter = new JLabel();
            moneyCounter.setBounds(1200,0, 80, 30);
            moneyCounter.setBackground(Color.GRAY);
            moneyCounter.setOpaque(true);

            this.setVisible(true);
            this.addKeyListener(this);
            this.add(background);
            this.add(clickMeButton);
            this.add(moneyCounter);
        }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==clickMeButton) {
        }
    }
}
