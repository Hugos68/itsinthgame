import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
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

        variableDeceleration();

        startGame();
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
        checkAndSetVehiclePos();
        checkAndSetRandomVehicleBorders();

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

    public void checkAndSetVehiclePos() {
        //border collision checking + car switch
        if (firstVehicleX > firstVehicleRightBorder || firstVehicleX < firstVehicleLeftBorder) {
            firstVehicleXVelocity = firstVehicleXVelocity * -1;
            setRandomVehicleColor(0);
        } firstVehicleX = firstVehicleX + firstVehicleXVelocity;

        if (secondVehicleX > secondVehicleRightBorder || secondVehicleX < secondVehicleLeftBorder) {
            secondVehicleXVelocity = secondVehicleXVelocity * -1;
            setRandomVehicleColor(1);
        } secondVehicleX = secondVehicleX + secondVehicleXVelocity;

        checkAndSetRandomVehicleBorders();
    }

    public void checkAndSetRandomVehicleBorders() {
        if (firstVehicleX > gameWidth/2-5 && firstVehicleX < gameWidth/2+5) {
            firstVehicleRightBorder = gameWidth + getRandomIntBetween(250, 2500);
            firstVehicleLeftBorder = -getRandomIntBetween(250, 2500);
        }
        if (secondVehicleX > gameWidth/2-5 && secondVehicleX < gameWidth/2+5) {
            secondVehicleRightBorder = gameWidth + getRandomIntBetween(250, 2500);
            secondVehicleLeftBorder = -getRandomIntBetween(250, 2500);
        }
    }

    public void setRandomVehicleColor(int vehicleNumber) {
        if (vehicleNumber==0) {
            do {firstVehicle = vehicles.get(getRandomIntBetween(0,5));} while (firstVehicle==secondVehicle);
        }
        if (vehicleNumber==1) {
            do {secondVehicle = vehicles.get(getRandomIntBetween(0,5));} while (secondVehicle==firstVehicle);
        }
    }

    public int getRandomIntBetween(int min, int max) {
        return  ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public void playSoundtrackOfCurrentScreen(boolean play) {
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
            playSoundtrackOfCurrentScreen(false);
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

        //audio deceleration
        titleScreenSoundTrackStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource("titlescreensong.wav")));
        clickSoundStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource("click.wav")));
        buildSoundStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getClassLoader().getResource("build.wav")));
        titleScreenSoundTrack = AudioSystem.getClip();
        clickSound = AudioSystem.getClip();
        buildSound = AudioSystem.getClip();

        //Image imports
        backGroundImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("background.png"))).getImage();
        titleText = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("titletext.png"))).getImage();
        startButtonIdle = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("startbuttonidle.png"))).getImage();
        startButtonHover = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("startbuttonhover.png"))).getImage();
        startButtonClick = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("startbuttonclick.png"))).getImage();
        vehicles = new ArrayList<>(5);
        vehicles.add(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("redcarpixel.png"))).getImage());
        vehicles.add(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("greencarpixel.png"))).getImage());
        vehicles.add(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("pinkcarpixel.png"))).getImage());
        vehicles.add(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("bluecarpixel.png"))).getImage());
        vehicles.add(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("orangecarpixel.png"))).getImage());
        vehicles.add(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("purplecarpixel.png"))).getImage());

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
        firstRandomVehicle = vehicles.get(getRandomIntBetween(0,5));
        firstVehicle = firstRandomVehicle;
        firstVehicleXVelocity = 7;
        firstVehicleX = -getRandomIntBetween(250, 2500);
        firstVehicleY = 575;
        firstVehicleHeight = firstRandomVehicle.getHeight(null);
        firstVehicleWidth = firstRandomVehicle.getWidth(null);
        firstVehicleRightBorder = gameWidth;
        firstVehicleLeftBorder = firstVehicleX-1;

        //second vehicle properties
        secondRandomVehicle = vehicles.get(getRandomIntBetween(0,5));
        secondVehicle = secondRandomVehicle;
        secondVehicleXVelocity = -7;
        secondVehicleX = gameWidth + getRandomIntBetween(250, 2500);
        secondVehicleY = 665;
        secondVehicleHeight = secondRandomVehicle.getHeight(null);
        secondVehicleWidth = secondRandomVehicle.getWidth(null);
        secondVehicleRightBorder = secondVehicleX+secondVehicleWidth+1;
        secondVehicleLeftBorder = 0;

    }
    public void startGame() {
        playSoundtrackOfCurrentScreen(true);
        gameThread = new Thread(this);
        gameThread.start();
    }
}