import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class StartScreenFrame extends JFrame implements ActionListener {

    final int gameWidth = 1280;
    final int gameHeight = (int) (gameWidth * 0.5625);

    StartScreenPanel startScreenPanel;
    JButton button;

    StartScreenFrame() {
        startScreenPanel = new StartScreenPanel();
        button = new JButton();
        button.setBounds(gameWidth/2-100, gameHeight/2-50,200,100);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addActionListener(this);
        this.setTitle("Saxion Campus Tycoon");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(button);
        this.add(startScreenPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==button) {
            this.dispose();
            new MainGameScreenFrame();
        }
    }
}

class StartScreenPanel extends JPanel implements ActionListener {

    final int gameWidth = 1280;
    final int gameHeight = (int) (gameWidth * 0.5625);

    Image background;
    Image titletext;
    Image redcar;
    Timer timer;
    int xVelocity = 1, yVelocity = 1;
    int x = 0, y = 0;

    StartScreenPanel() {
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));
        background = new ImageIcon("pics\\saxionBackground.png").getImage();
        titletext = new ImageIcon("pics\\titletext.png").getImage();
        redcar = new ImageIcon("pics\\redcarpixel.png").getImage();
        timer = new Timer(1,this);
        timer.start();
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(background,0,0,null);
        g2D.drawImage(titletext,0,0,null);
        g2D.drawImage(redcar,x,xVelocity,null);


        g2D.setColor(Color.yellow);
        g2D.fillRect(gameWidth /2-100, gameHeight /2-50,200,100);
        g2D.setStroke(new BasicStroke(2));
        g2D.setColor(Color.black);
        g2D.drawRect(gameWidth /2-100, gameHeight /2-50,200,100);
        g2D.setFont(new Font("Bold", Font.BOLD, 40));
        g2D.drawString("Start",592,375);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (x>=gameWidth-redcar.getWidth(null) || x<0) {
            xVelocity= xVelocity * -1;
        }
        x = x + xVelocity;
        repaint();
    }
    //TODO FLIP IMAGE
}



