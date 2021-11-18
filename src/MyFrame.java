import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MyFrame extends JFrame implements KeyListener, ActionListener {
    JButton clickMeButton;
    JLabel background;
    int screenX = 1280;
    int screenY = 720;
    MyFrame() {
        this.setTitle("Saxion Canvas Tycoon");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(screenX, screenY);
        this.setLayout(null);
        this.addKeyListener(this);
        clickMeButton = new JButton();
        clickMeButton.setText("Click me");
        clickMeButton.setBounds(screenX/2,screenY/2,200,100);
        clickMeButton.setBackground(Color.BLUE);
        clickMeButton.setOpaque(true);
        clickMeButton.addActionListener(this);

        //create background
        background = new JLabel();
        background.setBounds(0,0,1280,720);
        background.setBackground(Color.BLACK);
        background.setOpaque(true);
        this.setVisible(true);
        this.addKeyListener(this);
        this.add(background);
        this.add(clickMeButton);

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
        Random r = new Random ();
        Color randomColor = new Color (r.nextFloat (), r.nextFloat (), r.nextFloat ());
        if (e.getSource()==clickMeButton) {
            background.setBackground(randomColor);
        }
    }
}
