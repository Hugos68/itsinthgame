import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(button);
        this.add(startScreenPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        ImageIcon image = new ImageIcon("pics\\tycoonLogo.png");
        this.setIconImage(image.getImage());
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

    Image background = new ImageIcon("pics\\saxionBackground.png").getImage();
    Image titleText = new ImageIcon("pics\\titletext.png").getImage();
    Image redCar = new ImageIcon("pics\\redcarpixel.png").getImage();

    Timer timer = new Timer(12,this);

    int redCarXVelocity = 5;
    int redCarX = 0;
    int redCarY = 0;
    int redCarHeight = redCar.getHeight(null);
    int redCarWidth = redCar.getWidth(null);

    StartScreenPanel() {
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));
        timer.start();
    }

    public void paint(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(background,0,0,null);
        g2D.drawImage(titleText,0,0,null);

        if (redCarXVelocity < 0) {
            g2D.drawImage(redCar, redCarX, redCarY, null);
        } else {
            g2D.drawImage(redCar, redCarX + redCarWidth, redCarY, -redCarWidth, redCarHeight, null);
        }

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
        //bottom road y = 610
        //top road y = 510
        if (redCarX >= gameWidth || redCarX < -redCarWidth) {
            redCarXVelocity = redCarXVelocity * -1;
        }
        redCarX = redCarX + redCarXVelocity;
        redCarY = 610;

        repaint();
    }
}



