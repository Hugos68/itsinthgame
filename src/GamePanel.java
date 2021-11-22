import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GamePanel extends JPanel implements Runnable, MouseListener {

    //general game properties
    final int gameWidth = 1280;
    final int gameHeight = (int) (gameWidth * 0.5625);
    final int FPS = 60;

    //general variables
    int currentScreenActive;
    ArrayList<Image> vehicles;
    Thread gameThread;

    //audio variables
    AudioInputStream titleScreenSoundTrackStream;
    AudioInputStream clickSoundStream;
    AudioInputStream buildSoundStream;
    Clip titleScreenSoundTrack;
    Clip clickSound;
    Clip buildSound;

    //Image variables
    Image backGroundImage;
    Image titleText;
    Image startButtonIdle;
    Image startButtonHover;
    Image startButtonClick;
    Image firstRandomVehicle;
    Image secondRandomVehicle;

    //JButton variables
    JButton startButton;

    //boolean variables
    boolean startButtonHovering;
    boolean startButtonClicked;

    //first vehicle variables
    Image firstVehicle;
    int firstVehicleXVelocity;
    int firstVehicleX;
    int firstVehicleY;
    int firstVehicleHeight;
    int firstVehicleWidth;
    int firstVehicleRightBorder;
    int firstVehicleLeftBorder;

    //second vehicle variables
    Image secondVehicle;
    int secondVehicleXVelocity;
    int secondVehicleX;
    int secondVehicleY;
    int secondVehicleHeight;
    int secondVehicleWidth;
    int secondVehicleRightBorder;
    int secondVehicleLeftBorder;


    public GamePanel() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        //panel properties
        this.setPreferredSize(new Dimension(gameWidth,gameHeight));
        this.setDoubleBuffered(true);
        this.setLayout(null);

        //decelerate and start
        variableDeceleration();

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

        //always update
        vehicleUpdate();

        //start screen updates
        if (currentScreenActive==0) {

        }

        //game screen updates
        if (currentScreenActive==1) {

        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        //draw always
        g2D.drawImage(backGroundImage,0,0,null);

        if (firstVehicleXVelocity < 0) {
            g2D.drawImage(firstVehicle, firstVehicleX, firstVehicleY, null);
        }
        else {
            g2D.drawImage(firstVehicle, firstVehicleX + firstVehicleWidth, firstVehicleY, -firstVehicleWidth, firstVehicleHeight, null);
        }

        if (secondVehicleXVelocity < 0) {
            g2D.drawImage(secondVehicle, secondVehicleX, secondVehicleY, null);
        }
        else {
            g2D.drawImage(secondVehicle, secondVehicleX + secondVehicleWidth, secondVehicleY, -secondVehicleWidth, secondVehicleHeight, null);
        }

        //draw start screen
        if (currentScreenActive==0) {

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

        }

        //draw game screen
        if (currentScreenActive==1) {
            //TODO draw game screen
        }

        g2D.dispose();
    }
    public void vehicleUpdate() {

        //border collision checking
        if (firstVehicleX <= firstVehicleRightBorder || firstVehicleX > firstVehicleLeftBorder) {
            firstVehicleXVelocity = firstVehicleXVelocity * -1;
            firstVehicle = vehicles.get(getRandomInt(0,5));
        }
        firstVehicleX = firstVehicleX + firstVehicleXVelocity;

        //border collision checking
        if (secondVehicleX < secondVehicleRightBorder || secondVehicleX > secondVehicleLeftBorder) {
            secondVehicleXVelocity = secondVehicleXVelocity * -1;
            secondVehicle = vehicles.get(getRandomInt(0,5));
        }
        secondVehicleX = secondVehicleX + secondVehicleXVelocity;

        //set random borders
        if (firstVehicleX == gameWidth/2) {
            firstVehicleRightBorder = gameWidth + getRandomInt(250, 750);
            firstVehicleLeftBorder = getRandomInt(-750, -250);
        }

        //set random borders
        if (secondVehicleX == gameWidth/2) {
            secondVehicleRightBorder = gameWidth + getRandomInt(250, 750);
            secondVehicleLeftBorder = getRandomInt(-750, -250);
        }

    }

    public int getRandomInt(int min, int max) {
        return  ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public void playSoundtrack(boolean play) {
        if (currentScreenActive==0) {
            if (play) {
                try {
                    titleScreenSoundTrack.open(titleScreenSoundTrackStream);
                    titleScreenSoundTrack.loop(Clip.LOOP_CONTINUOUSLY);
                    titleScreenSoundTrack.start();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            if (!play) {
                try {
                    titleScreenSoundTrack.stop();
                    titleScreenSoundTrack.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if (currentScreenActive==1) {
            //TODO import game screen music and make it playable
        }
    }

    public void playClickSound() {
        try {
            if (!clickSound.isOpen()) {
                clickSound.open(clickSoundStream);
            }
            clickSound.setMicrosecondPosition(0);
            clickSound.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
            playClickSound();
            startButtonClicked = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        startButtonClicked = false;
        if (e.getSource()==startButton && startButtonHovering) {
            startButton.setVisible(false);
            playSoundtrack(false);
            currentScreenActive = 1;
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

    public void variableDeceleration() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        //general variables
        currentScreenActive = 0; // 0 = start screen, 1 = game screen
        vehicles = new ArrayList<>(5);

        //audio deceleration
        titleScreenSoundTrackStream = AudioSystem.getAudioInputStream(new File("assets\\titlescreensong.wav"));
        clickSoundStream = AudioSystem.getAudioInputStream(new File("assets\\click.wav"));
        buildSoundStream = AudioSystem.getAudioInputStream(new File("assets\\build.wav"));
        titleScreenSoundTrack = AudioSystem.getClip();
        clickSound = AudioSystem.getClip();
        buildSound = AudioSystem.getClip();
        playSoundtrack(true);

        //image deceleration
        backGroundImage = new ImageIcon("assets\\background.png").getImage();
        titleText = new ImageIcon("assets\\titletext.png").getImage();
        startButtonIdle = new ImageIcon("assets\\Startbuttonidle.png").getImage();
        startButtonHover = new ImageIcon("assets\\Startbuttonhover.png").getImage();
        startButtonClick = new ImageIcon("assets\\Startbuttonclick.png").getImage();
        vehicles.add(new ImageIcon("assets\\redcarpixel.png").getImage());
        vehicles.add(new ImageIcon("assets\\greencarpixel.png").getImage());
        vehicles.add(new ImageIcon("assets\\pinkcarpixel.png").getImage());
        vehicles.add(new ImageIcon("assets\\bluecarpixel.png").getImage());
        vehicles.add(new ImageIcon("assets\\orangecarpixel.png").getImage());
        vehicles.add(new ImageIcon("assets\\purplecarpixel.png").getImage());

        //boolean deceleration
        startButtonHovering = false;
        startButtonClicked = false;

        //JButton deceleration
        startButton = new JButton();
        startButton.setBounds(gameWidth/2-75, gameHeight/2-75,150,150);
        startButton.setOpaque(false);
        startButton.setBorderPainted(false);
        startButton.addMouseListener(this);
        this.add(startButton);


        //first vehicle properties
        firstRandomVehicle = vehicles.get(getRandomInt(0,5));
        firstVehicle = firstRandomVehicle;
        firstVehicleXVelocity = 7;
        firstVehicleX = getRandomInt(-1000, -500);
        firstVehicleY = 575;
        firstVehicleHeight = firstRandomVehicle.getHeight(null);
        firstVehicleWidth = firstRandomVehicle.getWidth(null);
        firstVehicleRightBorder = -1000;
        firstVehicleLeftBorder = gameWidth + 1000;

        //second vehicle properties
        secondRandomVehicle = vehicles.get(getRandomInt(0,6));
        secondVehicle = secondRandomVehicle;
        secondVehicleXVelocity = 7;
        secondVehicleX = gameWidth + getRandomInt(250, 500);
        secondVehicleY = 665;
        secondVehicleHeight = secondRandomVehicle.getHeight(null);
        secondVehicleWidth = secondRandomVehicle.getWidth(null);
        secondVehicleRightBorder = -1000;
        secondVehicleLeftBorder = gameWidth + 1000;


        //game loop thread start
        gameThread = new Thread(this);
        gameThread.start();
    }
}