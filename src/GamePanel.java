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
    final int gameWidth = 1920; final int gameHeight = (int) (gameWidth * 0.5625);
    final int FPS = 60;

    //general variables
    int currentScreenActive;
    ArrayList<Image> vehicles;
    Thread gameThread;

    //audio variables
    AudioInputStream titleScreenSoundTrackStream; AudioInputStream clickSoundStream; AudioInputStream buildSoundStream;
    Clip titleScreenSoundTrack; Clip clickSound;  Clip buildSound;

    //Image variables
    Image backGroundImage; Image titleText;
    Image startButtonIdle; Image startButtonHover; Image startButtonClick;
    Image firstRandomVehicle; Image secondRandomVehicle;
    Image redBuilding;

    //JButton variables
    JButton startButton;

    //boolean variables
    boolean startButtonHovering; boolean startButtonClicked;

    //first vehicle variables
    Image firstVehicle;
    int firstVehicleVelocity;
    int firstVehicleX; int firstVehicleY;
    int firstVehicleWidth; int firstVehicleHeight;
    int firstVehicleLeftBorder; int firstVehicleRightBorder;

    //second vehicle variables
    Image secondVehicle;
    int secondVehicleVelocity;
    int secondVehicleX; int secondVehicleY;
    int secondVehicleWidth; int secondVehicleHeight;
    int secondVehicleLeftBorder; int secondVehicleRightBorder;

    //game screen variables
    int balance;

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
        setVehiclePosAndColor();
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

        //draw elements no matter what screen is active
        g2D.drawImage(backGroundImage, 0, 0, null);
        drawVehicles(g2D);

        //draw start screen elements
        if (currentScreenActive == 0) {
            g2D.drawImage(titleText, gameWidth/2-titleText.getWidth(null)/2, gameHeight/7, null);
            drawStartButton(g2D);
        }

        //draw game screen
        if (currentScreenActive==1) {
            //TODO draw game screen
        }

        g2D.dispose();
    }

    public void drawVehicles(Graphics2D g2D) {
        if (firstVehicleVelocity < 0) {
            g2D.drawImage(firstVehicle, firstVehicleX, firstVehicleY, null);
        }

        else {
            g2D.drawImage(firstVehicle, firstVehicleX + firstVehicleWidth, firstVehicleY, -firstVehicleWidth, firstVehicleHeight, null);
        }

        if (secondVehicleVelocity < 0) {
            g2D.drawImage(secondVehicle, secondVehicleX, secondVehicleY, null);
        }

        else {
            g2D.drawImage(secondVehicle, secondVehicleX + secondVehicleWidth, secondVehicleY, -secondVehicleWidth, secondVehicleHeight, null);
        }
    }

    public void drawStartButton(Graphics2D g2D) {
        if (startButtonClicked) {
            g2D.drawImage(startButtonClick,gameWidth/2-startButtonClick.getWidth(null)/2, gameHeight/2-startButtonClick.getHeight(null)/2,null);
        }

        else if (startButtonHovering) {
            g2D.drawImage(startButtonHover,gameWidth/2-startButtonHover.getWidth(null)/2, gameHeight/2-startButtonHover.getHeight(null)/2,null);
        }

        else {
            g2D.drawImage(startButtonIdle,gameWidth/2-startButtonIdle.getWidth(null)/2, gameHeight/2-startButtonIdle.getHeight(null)/2,null);
        }
    }

    public void setVehiclePosAndColor() {
        //border collision checking + car switch
        if (firstVehicleX > firstVehicleRightBorder || firstVehicleX < firstVehicleLeftBorder) {
            firstVehicleVelocity = firstVehicleVelocity * -1;
            getRandomVehicleColor(0);
        } firstVehicleX = firstVehicleX + firstVehicleVelocity;

        if (secondVehicleX > secondVehicleRightBorder || secondVehicleX < secondVehicleLeftBorder) {
            secondVehicleVelocity = secondVehicleVelocity * -1;
            getRandomVehicleColor(1);
        } secondVehicleX = secondVehicleX + secondVehicleVelocity;

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

    public void getRandomVehicleColor(int vehicleNumber) {
        //ensure vehicle is different from previous self AND other vehicle
        Image previousFirstVehicle = firstVehicle;
        Image previousSecondVehicle = secondVehicle;
        if (vehicleNumber==0) {
            do {firstVehicle = vehicles.get(getRandomIntBetween(0,5));} while (firstVehicle == secondVehicle && firstVehicle == previousFirstVehicle);
        }
        if (vehicleNumber==1) {
            do {secondVehicle = vehicles.get(getRandomIntBetween(0,5));} while (secondVehicle == firstVehicle && secondVehicle != previousSecondVehicle);
        }
    }

    public int getRandomIntBetween(int min, int max) {
        return  ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public void playSoundtrack() {
            if (currentScreenActive==0) {
                try {
                    titleScreenSoundTrack.open(titleScreenSoundTrackStream);
                    titleScreenSoundTrack.loop(Clip.LOOP_CONTINUOUSLY);
                    titleScreenSoundTrack.start();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (currentScreenActive==1) {
                //TODO start game soundtrack
            }
        }

    public void stopSoundTrack() {
        if (currentScreenActive==0) {
            try {
                titleScreenSoundTrack.stop();
                titleScreenSoundTrack.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (currentScreenActive==1) {
            //TODO stop game soundtrack
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            stopSoundTrack();
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
        redBuilding = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("redbuilding.png"))).getImage();
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
        firstVehicleVelocity = 7;
        firstVehicleX = -getRandomIntBetween(250, 2500);
        firstVehicleY =  (int) ((double) gameHeight * 0.81944444444); //885
        firstVehicleHeight = firstRandomVehicle.getHeight(null);
        firstVehicleWidth = firstRandomVehicle.getWidth(null);
        firstVehicleRightBorder = gameWidth;
        firstVehicleLeftBorder = firstVehicleX-1;

        //second vehicle properties
        do {secondRandomVehicle = vehicles.get(getRandomIntBetween(0,5));} while (secondRandomVehicle==firstRandomVehicle);
        secondVehicle = secondRandomVehicle;
        secondVehicleVelocity = -7;
        secondVehicleX = gameWidth + getRandomIntBetween(250, 2500);
        secondVehicleY = (int) ((double) gameHeight * 0.91666666666); //990
        secondVehicleHeight = secondRandomVehicle.getHeight(null);
        secondVehicleWidth = secondRandomVehicle.getWidth(null);
        secondVehicleRightBorder = secondVehicleX+secondVehicleWidth+1;
        secondVehicleLeftBorder = 0;

        //game screen properties
        balance = 0;
    }

    public void startGame() {
        playSoundtrack();
        gameThread = new Thread(this);
        gameThread.start();
    }
}