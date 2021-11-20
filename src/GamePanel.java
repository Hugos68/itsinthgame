import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JPanel implements Runnable, ActionListener, MouseListener {

    //final variables
    final int gameWidth = 1280;
    final int gameHeight = (int) (gameWidth * 0.5625);
    final int FPS = 60;
    final Image background = new ImageIcon("pics\\saxionBackground.png").getImage();

    //start screen variables
    boolean startScreenActive = true;
    boolean startButtonHovering = false;
    boolean startButtonClicked = false;
    Image titleText = new ImageIcon("pics\\titletext.png").getImage();
    Image startButtonIdle = new ImageIcon("pics\\Startbutton.png").getImage();
    Image startButtonHover = new ImageIcon("pics\\Startbuttonhover.png").getImage();
    Image startButtonClick = new ImageIcon("pics\\Startbuttonclick.png").getImage();
    Image redCar = new ImageIcon("pics\\redcarpixel.png").getImage();
    int redCarXVelocity = 5;
    int redCarX = 0;
    int redCarY = 610;
    int redCarHeight = redCar.getHeight(null);
    int redCarWidth = redCar.getWidth(null);

    Thread gameThread;
    JButton startButton;


    public GamePanel() {

        //panel properties
        this.setPreferredSize(new Dimension(gameWidth,gameHeight));
        this.setDoubleBuffered(true);
        this.setLayout(null);

        //start screen panel adds
        if (startScreenActive) {
            startButton = new JButton();
            startButton.setBackground(Color.YELLOW);
            startButton.setBounds(gameWidth/2-75, gameHeight/2-75,150,150);
            startButton.setOpaque(false);
            startButton.setBorderPainted(false);
            startButton.addActionListener(this);
            startButton.addMouseListener(this);
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

        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(background,0,0,null);

        //draw start screen
        if (startScreenActive) {

            g2D.drawImage(titleText, 0, -15, null);

            //TODO FIX HOVERING AND CLICKING INTERACTIONS
            if (!startButtonHovering) {
                g2D.drawImage(startButtonIdle,gameWidth/2-75, gameHeight/2-75,null);
            }

            else if (startButtonClicked) {
                g2D.drawImage(startButtonClick,gameWidth/2-75, gameHeight/2-75,null);
            }

            else if (startButtonHovering){
                g2D.drawImage(startButtonHover,gameWidth/2-75, gameHeight/2-75,null);
            }

            if (redCarXVelocity < 0) {
                g2D.drawImage(redCar, redCarX, redCarY, null);
            }

            else {
                g2D.drawImage(redCar, redCarX + redCarWidth, redCarY, -redCarWidth, redCarHeight, null);
            }

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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource()==startButton) {
            startButtonClicked = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        startButtonClicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource()==startButton) {
            startButtonHovering = true;
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource()==startButton) {
            startButtonHovering = false;
        }
    }
}