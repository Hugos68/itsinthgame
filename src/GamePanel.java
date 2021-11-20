import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements Runnable, ActionListener {

    //final variables
    final int gameWidth = 1280;
    final int gameHeight = (int) (gameWidth * 0.5625);
    final int FPS = 60;
    final Image background = new ImageIcon("pics\\saxionBackground.png").getImage();

    //start screen variables
    boolean startScreenActive = true;
    Image titleText = new ImageIcon("pics\\titletext.png").getImage();
    Image redCar = new ImageIcon("pics\\redcarpixel.png").getImage();
    int redCarXVelocity = 5;
    int redCarX = 0;
    int redCarY = 0;
    int redCarHeight = redCar.getHeight(null);
    int redCarWidth = redCar.getWidth(null);


    Thread gameThread;
    JButton startButton;


    public GamePanel() {
        //panel properties
        this.setPreferredSize(new Dimension(gameWidth,gameHeight));
        this.setDoubleBuffered(true);
        this.setLayout(null);

        if (startScreenActive) {
            startButton = new JButton();
            startButton.setBackground(Color.YELLOW);
            startButton.setBounds(gameWidth/2-100, gameHeight/2-50,200,100);
            startButton.setOpaque(false);
            startButton.setBorderPainted(false);
            startButton.addActionListener(this);
            this.add(startButton);

        }

        //create thread
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {

        double frameTime = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + frameTime;



        while (!Thread.currentThread().isInterrupted()) {

            update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += frameTime;

            } catch(InterruptedException e){

                e.printStackTrace();
            }
        }
    }

    public void update() {

        if (startScreenActive) {

            if (redCarX >= gameWidth || redCarX < -redCarWidth) {
                redCarXVelocity = redCarXVelocity * -1;
            }
            redCarX = redCarX + redCarXVelocity;
            redCarY = 610;
        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(background,0,0,null);

        //draw start screen
        if (startScreenActive) {

            g2D.drawImage(titleText, 0, 0, null);

            if (redCarXVelocity < 0) {
                g2D.drawImage(redCar, redCarX, redCarY, null);
            }

            else {
                g2D.drawImage(redCar, redCarX + redCarWidth, redCarY, -redCarWidth, redCarHeight, null);
            }

            g2D.setColor(Color.yellow);
            g2D.fillRect(gameWidth / 2 - 100, gameHeight / 2 - 50, 200, 100);
            g2D.setStroke(new BasicStroke(2));
            g2D.setColor(Color.black);
            g2D.drawRect(gameWidth / 2 - 100, gameHeight / 2 - 50, 200, 100);
            g2D.setFont(new Font("Bold", Font.BOLD, 40));
            g2D.drawString("Start", 592, 375);
            //TODO turn start button into a picture
        }
        //TODO draw game screen

        g2D.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==startButton) {
            startButton.setVisible(false);
            startScreenActive = false;
        }
    }
}