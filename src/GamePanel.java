import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class GamePanel extends JPanel implements Runnable, MouseListener {

    final int gameWidth = 1920;
    final int gameHeight = (int) (gameWidth * 0.5625);
    final int FPS = 60;

    int currentScreenActive;
    Thread gameThread;

    Image backGroundImage, titleText;
    Image startButtonIdleImage, startButtonHoverImage, startButtonClickImage;
    Image redBuilding;

    JButton startButton;

    boolean mouseHoveringStartButton;
    boolean mouseClickedStartButton;

    Audio audio = new Audio();
    Vehicle firstVehicle = new Vehicle("left");
    Vehicle secondVehicle = new Vehicle("right");

    //game screen variables
    int balance;
    int frameCounter = 0;
    int placeBuildingY;

    public GamePanel() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        //panel properties
        this.setPreferredSize(new Dimension(gameWidth,gameHeight));
        this.setDoubleBuffered(true);
        this.setLayout(null);

        currentScreenActive = 0;
        // 0 = start screen
        // 1 = game screen

        startGame();
    }
    public void update() {
        updateAesthetics();
        updateScreen();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        drawAesthetics(g2D);
        drawScreen(g2D);
        g2D.dispose();
    }


    //UPDATE AND DRAW AESTHETICS
    public void updateAesthetics() {
        firstVehicle.updateVehicles();
        secondVehicle.updateVehicles();
    }
    public void drawAesthetics(Graphics2D g2D) {
        g2D.drawImage(backGroundImage, 0, 0, null);
        firstVehicle.drawVehicles(g2D);
        secondVehicle.drawVehicles(g2D);
    }

    //UPDATE AND DRAW SCREENS
    public void updateScreen() {
        if (currentScreenActive==0) {
            updateStartScreen();
        }
        if (currentScreenActive==1) {
            updateGameScreen();
        }
    }
    public void drawScreen(Graphics2D g2D) {
        if (currentScreenActive==0) {
            drawStartScreen(g2D);
        }
        if (currentScreenActive==1) {
            drawGameScreen(g2D);
        }
    }

    public void updateStartScreen() {

    }
    public void drawStartScreen(Graphics2D g2D) {
        g2D.drawImage(titleText, gameWidth/2-titleText.getWidth(null)/2, gameHeight/7, null);
        if (mouseClickedStartButton) {
            g2D.drawImage(startButtonClickImage,gameWidth/2- startButtonClickImage.getWidth(null)/2, gameHeight/2- startButtonClickImage.getHeight(null)/2,null);
        }
        else if (mouseHoveringStartButton) {
            g2D.drawImage(startButtonHoverImage,gameWidth/2- startButtonHoverImage.getWidth(null)/2, gameHeight/2- startButtonHoverImage.getHeight(null)/2,null);
        }
        else {
            g2D.drawImage(startButtonIdleImage,gameWidth/2- startButtonIdleImage.getWidth(null)/2, gameHeight/2- startButtonIdleImage.getHeight(null)/2,null);
        }
    }

    public void updateGameScreen() {
        frameCounter++;
        if (frameCounter==120) {
            updateBalance();
            frameCounter=0;
        }
    }
    public void drawGameScreen(Graphics2D g2D) {
        drawBalance(g2D);
    }

    public void updateBalance() {
        //TODO add building variables
    }
    public void drawBalance(Graphics2D g2D) {
        g2D.setColor(Color.YELLOW);
        g2D.fillRect(1800,0,120,100);
        g2D.setColor(Color.BLACK);
        g2D.drawRect(1800,0,120,100);
        g2D.drawString(String.valueOf(balance),1860,50);
    }







    public void gameScreenVariables() {
        balance = 0;
        placeBuildingY = gameHeight/9*2;
    }
    public void setBooleans() {
        mouseHoveringStartButton = false;
        mouseClickedStartButton = false;
    }
    public void importImages() {
        backGroundImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("background.png"))).getImage();
        titleText = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("titletext.png"))).getImage();
        startButtonIdleImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("startbuttonidle.png"))).getImage();
        startButtonHoverImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("startbuttonhover.png"))).getImage();
        startButtonClickImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("startbuttonclick.png"))).getImage();
        redBuilding = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("redbuilding.png"))).getImage();
    }
    public void createButtons() {
        startButton = new JButton();
        startButton.setBounds(gameWidth/2-75, gameHeight/2-75,150,150);
        startButton.setOpaque(false);
        startButton.setBorderPainted(false);
        startButton.addMouseListener(this);
        this.add(startButton);
    }

    public void startGame() {
        gameThread = new Thread(this);
        setBooleans();
        importImages();
        createButtons();
        gameScreenVariables();
        Audio.playSoundtrack(0);
        gameThread.start();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource()==startButton) {
            Audio.playClickSound();
            mouseClickedStartButton = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseClickedStartButton = false;
        if (e.getSource()==startButton && mouseHoveringStartButton) {
            startButton.setVisible(false);
            Audio.stopSoundTrack(0);
            currentScreenActive = 1;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource()==startButton) {
            mouseHoveringStartButton = true;
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == startButton) {
            mouseHoveringStartButton = false;
        }
    }

    @Override
    public void run() {

        double frameTime = 1000000000f/FPS;
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
}