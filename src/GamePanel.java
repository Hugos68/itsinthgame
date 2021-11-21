import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable, MouseListener {

    //game properties
    final int gameWidth = 1280;
    final int gameHeight = (int) (gameWidth * 0.5625);
    final int FPS = 60;
    final Image background = new ImageIcon("assets\\background.png").getImage();
    final Thread gameThread;

    //sound inputs
    final AudioInputStream titleScreenSoundTrackStream = AudioSystem.getAudioInputStream(new File("assets\\titlescreensong.wav"));
    final Clip titleScreenSoundTrack = AudioSystem.getClip();
    final AudioInputStream clickSoundStream = AudioSystem.getAudioInputStream(new File("assets\\click.wav"));
    final Clip clickSound = AudioSystem.getClip();
    final AudioInputStream buildSoundStream = AudioSystem.getAudioInputStream(new File("assets\\build.wav"));
    final Clip buildSound = AudioSystem.getClip();


    //start screen variables
    boolean startScreenActive = true;
    boolean startButtonHovering = false;
    boolean startButtonClicked = false;
    Image titleText = new ImageIcon("assets\\titletext.png").getImage();
    Image startButtonIdle = new ImageIcon("assets\\Startbuttonidle.png").getImage();
    Image startButtonHover = new ImageIcon("assets\\Startbuttonhover.png").getImage();
    Image startButtonClick = new ImageIcon("assets\\Startbuttonclick.png").getImage();
    Image redCar = new ImageIcon("assets\\redcarpixel.png").getImage();

    int redCarXVelocity = 5;
    int redCarX = 0;
    int redCarY = 665;
    int redCarHeight = redCar.getHeight(null);
    int redCarWidth = redCar.getWidth(null);
    JButton startButton;

    //game screen variables
    boolean gameScreenActive;


    public GamePanel() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        //panel properties
        this.setPreferredSize(new Dimension(gameWidth,gameHeight));
        this.setDoubleBuffered(true);
        this.setLayout(null);

        //panel addons
        playTitleScreenSoundTrack(true);
        startButton = new JButton();
        startButton.setBackground(Color.YELLOW);
        startButton.setBounds(gameWidth/2-75, gameHeight/2-75,150,150);
        startButton.setOpaque(false);
        startButton.setBorderPainted(false);
        startButton.addMouseListener(this);
        this.add(startButton);

        //create game thread
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
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void update() {

        if (startScreenActive) {

            //TODO set random collision borders
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

            g2D.drawImage(titleText, gameWidth/2-354, 75, null);

            if (startButtonClicked) {
                g2D.drawImage(startButtonClick,gameWidth/2-75, gameHeight/2-75,null);
            }
            else if (startButtonHovering) {
                g2D.drawImage(startButtonHover,gameWidth/2-75, gameHeight/2-75,null);
            }
            else {
                g2D.drawImage(startButtonIdle,gameWidth/2-75, gameHeight/2-75,null);
            }

            if (redCarXVelocity < 0) {
                g2D.drawImage(redCar, redCarX, redCarY, null);
            }
            else {
                g2D.drawImage(redCar, redCarX + redCarWidth, redCarY, -redCarWidth, redCarHeight, null);
            }

        }
        if (gameScreenActive) {
            //TODO draw game screen
        }

        g2D.dispose();
    }

    public void playTitleScreenSoundTrack(boolean play) {
        if (play) {
            try {
                titleScreenSoundTrack.open(titleScreenSoundTrackStream);
                titleScreenSoundTrack.loop(Clip.LOOP_CONTINUOUSLY);
                titleScreenSoundTrack.start();
            } catch (Exception e) {System.out.println(e.getMessage());}
        }
        if (!play) {
            try {
                titleScreenSoundTrack.stop();
                titleScreenSoundTrack.close();
            } catch (Exception e) {System.out.println(e.getMessage());}
        }
    }

    public void playClickSound() {
        try {
            if (!clickSound.isOpen()) {
                clickSound.open(clickSoundStream);
            }
            clickSound.setMicrosecondPosition(0);
            clickSound.start();
        } catch (Exception e) {System.out.println(e.getMessage());}
    }

    public void playBuildSound() {
        try {
            if (!buildSound.isOpen()) {
                buildSound.open(buildSoundStream);
            }
            buildSound.setMicrosecondPosition(0);
            buildSound.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        if (e.getSource()==startButton && startButtonHovering) {
            playClickSound();
            startButton.setVisible(false);
            startScreenActive = false;
            playTitleScreenSoundTrack(false);
            gameScreenActive = true;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource()==startButton) {
            startButtonHovering = true;
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == startButton) {
            startButtonHovering = false;
        }
    }


}