import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable, MouseListener {

    //game properties
    final int gameWidth = 1920;
    final int gameHeight = (int) (gameWidth * 0.5625);
    final int FPS = 60;

    Thread gameThread;

    //initialize images
    Image backGroundImage, titleText;
    Image startButtonIdleImage, startButtonHoverImage, startButtonClickImage;
    Image redBuilding;

    //initialize buttons
    JButton startButton;
    JButton exitGameButton;

    //key binds
    Action escape = new EscapeAction();
    boolean escapePressed = false;

    //initialize state variables
    boolean settingsMenuActive;
    int mostRecentScreen;
    int currentScreenState;
    // 0 = start screen
    // 1 = game screen=
    int currentStartButtonState;
    // 0 = idle
    // 1 = hovering
    // 2 = click

    //create class objects
    Audio audio = new Audio();
    Vehicle firstVehicle = new Vehicle("left");
    Vehicle secondVehicle = new Vehicle("right");

    //game screen variables
    int balance;
    int frameCounter;
    int placeBuildingY;

    public GamePanel() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        //panel properties
        this.setPreferredSize(new Dimension(gameWidth,gameHeight));
        this.setDoubleBuffered(true);
        this.setLayout(null);
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escapeAction");
        this.getActionMap().put("escapeAction", escape);

        startGame();
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
        startButton.setFocusable(false);
        startButton.addMouseListener(this);
        this.add(startButton);
    }
    public void setStates() {
        currentScreenState = 0;
        currentStartButtonState = 0;
        settingsMenuActive = false;
    }
    public void gameScreenVariables() {
        balance = 1000;
        frameCounter = 0;
        placeBuildingY = (int) (gameHeight*0.188);
    }

    public void startGame() {
        gameThread = new Thread(this);
        importImages();
        createButtons();
        setStates();
        gameScreenVariables();
        audio.playSoundtrack(0);
        gameThread.start();
    }

    //UPDATE AND DRAW
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
        if (!settingsMenuActive) {
            mostRecentScreen = currentScreenState;
        }
        if (currentScreenState == 0) {
            updateStartScreen();
        }
        if (currentScreenState == 1) {
            updateGameScreen();
        }

    }
    public void drawScreen(Graphics2D g2D) {
        if (currentScreenState == 0) {
            drawStartScreen(g2D);
        }
        if (currentScreenState == 1) {
            drawGameScreen(g2D);
        }
        if (settingsMenuActive) {
            drawSettingsScreen(g2D);
        }
    }

    //UPDATE AND DRAW START SCREEN
    public void updateStartScreen() {

    }
    public void drawStartScreen(Graphics2D g2D) {
        g2D.drawImage(titleText, gameWidth/2-titleText.getWidth(null)/2, gameHeight/7, null);
        if (currentStartButtonState == 2) {
            g2D.drawImage(startButtonClickImage,gameWidth/2- startButtonClickImage.getWidth(null)/2, gameHeight/2- startButtonClickImage.getHeight(null)/2,null);
        }
        else if (currentStartButtonState == 1) {
            g2D.drawImage(startButtonHoverImage,gameWidth/2- startButtonHoverImage.getWidth(null)/2, gameHeight/2- startButtonHoverImage.getHeight(null)/2,null);
        }
        else {
            g2D.drawImage(startButtonIdleImage,gameWidth/2- startButtonIdleImage.getWidth(null)/2, gameHeight/2- startButtonIdleImage.getHeight(null)/2,null);
        }
    }

    //UPDATE AND DRAW GAME SCREEN
    public void updateGameScreen() {
        frameCounter++;
        if (frameCounter==120) {
            updateBalance();
            frameCounter=0;
        }
    }
    public void drawGameScreen(Graphics2D g2D) {
        drawBalance(g2D);
        g2D.drawImage(redBuilding,gameWidth/2-redBuilding.getWidth(null)/2,placeBuildingY,null);
    }

    //UPDATE AND DRAW SETTINGS SCREEN
    public void updateSettingsScreen() {

    }
    public void drawSettingsScreen(Graphics2D g2D) {
        g2D.fillRect(0,0,400,400);
    }

    //DRAW AND UPDATE BALANCE
    public void updateBalance() {
        //TODO add building variables
    }
    public void drawBalance(Graphics2D g2D) {
        //TODO draw balance
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource()==startButton && SwingUtilities.isLeftMouseButton(e)) {
            audio.playClickSound();
            currentStartButtonState=2;
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource()==startButton && currentStartButtonState == 2 && SwingUtilities.isLeftMouseButton(e)) {
            startButton.setVisible(false);
            audio.stopSoundTrack(0);
            audio.playSoundtrack(1);
            currentScreenState = 1;
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource()==startButton) {
            currentStartButtonState = 1;
        }
    }
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource()==startButton) {
            currentStartButtonState = 0;
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

    public class EscapeAction extends  AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!settingsMenuActive) {
                settingsMenuActive = true;
            }
            else {
                currentScreenState = mostRecentScreen;
                settingsMenuActive = false;
            }
        }
    }
}