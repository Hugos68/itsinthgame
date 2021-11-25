import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class GamePanel extends JPanel implements Runnable, MouseListener {

    public GamePanel() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        startGame();
    }

    //game properties
    final int gameWidth = 1920;
    final int gameHeight = (int) (gameWidth * 0.5625);
    final int FPS = 60;

        //objects
        Thread gameThread = new Thread(this);
        Action escape = new EscapeAction();
        Audio audio = new Audio();
        Image image = new Image();
        Vehicle firstVehicle = new Vehicle(7, (this.getRandomIntBetween(250, 2500) * -1), (int) ((double) gameHeight * 0.81944444444));
        Vehicle secondVehicle = new Vehicle(-7, gameWidth + this.getRandomIntBetween(250, 2500), (int) ((double) gameHeight * 0.91666666666));

        //button variables
        //button states:
        //0 = idle
        //1 = hover
        //2 = click
        JButton startButton; int currentStartButtonState;
        JButton exitButton; int currentExitButtonState;

        //screen variables
        int currentScreenState = 0;
        int mostRecentScreen = 0;
        boolean settingsMenuActive = false;
        // 0 = start screen
        // 1 = game screen



        //game screen variables
        int balance;
        int frameCounter;
        int placeBuildingY;

    public void createButtons() {
        startButton = new JButton();
        startButton.setBounds(gameWidth/2-75, gameHeight/2-75,150,150);
        startButton.setOpaque(false);
        startButton.setBorderPainted(false);
        startButton.setFocusable(false);
        startButton.addMouseListener(this);
        currentStartButtonState = 0;

        exitButton = new JButton();
        exitButton.setBounds(gameWidth/2-150,gameHeight/2,300,100);
        exitButton.setOpaque(false);
        exitButton.setBorderPainted(false);
        exitButton.setFocusable(false);
        exitButton.addMouseListener(this);
        currentExitButtonState = 0;

        this.add(startButton);
        this.add(exitButton);
    }
    public void setGameScreenVariables() {
        balance = 1000;
        frameCounter = 0;
        placeBuildingY = (int) (gameHeight*0.188);
    }



    //START GAME
    public void startGame() {
        this.setPreferredSize(new Dimension(gameWidth,gameHeight));
        this.setDoubleBuffered(true);
        this.setLayout(null);
        //create escape key bind
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escapeAction");
        this.getActionMap().put("escapeAction", escape);
        createButtons();
        setGameScreenVariables();
        audio.playSoundtrack(0);
        gameThread.start();
    }

    //UPDATES
    public void update() {
        if (!settingsMenuActive) {
            updateAesthetics();
            updateScreen();
        }
    }
    public void updateAesthetics() {
        firstVehicle.updateVehicles();
        secondVehicle.updateVehicles();
    }
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
    public void updateStartScreen() {
    }
    public void updateGameScreen() {
        startButton.setVisible(false);
        frameCounter++;
        if (frameCounter==120) {
            updateBalance();
            frameCounter=0;
        }
    }
    public void updateBalance() {
        //TODO add building variables
    }
    public void updateSettingsScreen() {

    }

    //REPAINT
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        drawAesthetics(g2D);
        drawScreen(g2D);
        g2D.dispose();
    }
    public void drawAesthetics(Graphics2D g2D) {
        g2D.drawImage(image.backGroundImage, 0, 0, null);
        firstVehicle.drawVehicles(g2D);
        secondVehicle.drawVehicles(g2D);
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
    public void drawStartScreen(Graphics2D g2D) {
        g2D.drawImage(image.titleText, gameWidth/2-image.titleText.getWidth(null)/2, gameHeight/7, null);
        drawStartScreenButton(g2D);
    }
    public void drawStartScreenButton(Graphics2D g2D) {
        if (currentStartButtonState == 0) {
            g2D.drawImage(image.startButtonIdleImage,gameWidth/2- image.startButtonHoverImage.getWidth(null)/2, gameHeight/2- image.startButtonHoverImage.getHeight(null)/2,null);
        }
        else if (currentStartButtonState == 1) {
            g2D.drawImage(image.startButtonHoverImage,gameWidth/2- image.startButtonHoverImage.getWidth(null)/2, gameHeight/2- image.startButtonHoverImage.getHeight(null)/2,null);
        }
        else {
            g2D.drawImage(image.startButtonClickImage,gameWidth/2- image.startButtonClickImage.getWidth(null)/2, gameHeight/2- image.startButtonClickImage.getHeight(null)/2,null);
        }
    }
    public void drawGameScreen(Graphics2D g2D) {
        drawBalance(g2D);
        g2D.drawImage(image.redBuilding,gameWidth/2-image.redBuilding.getWidth(null)/2,placeBuildingY,null);
    }
    public void drawBalance(Graphics2D g2D) {
        //TODO draw balance
    }
    public void drawSettingsScreen(Graphics2D g2D) {
        g2D.fillRect(gameWidth/2-300,gameHeight/2-450,600,900);
        g2D.drawImage(image.settingsMenu,0,0,null);
        drawSettingsExitButton(g2D);

    }
    public void drawSettingsExitButton(Graphics2D g2D) {
        //TODO get exit button in 3 different states 300x100
        if (currentExitButtonState == 0) {
            g2D.drawImage(image.stopButtonIdleImage,gameWidth/2- image.startButtonHoverImage.getWidth(null)/2, gameHeight/2- image.startButtonHoverImage.getHeight(null)/2,null);
            //g2D.fillRect(gameWidth/2-150,gameHeight/2,300,100);
        }
        else if (currentExitButtonState == 1) {
            g2D.drawImage(image.stopButtonHoverImage,gameWidth/2- image.startButtonHoverImage.getWidth(null)/2, gameHeight/2- image.startButtonHoverImage.getHeight(null)/2,null);
        }
        else {
            g2D.drawImage(image.stopButtonClickImage,gameWidth/2- image.startButtonClickImage.getWidth(null)/2, gameHeight/2- image.startButtonClickImage.getHeight(null)/2,null);
        }
    }

    //STOP GAME
    public void stopGame() {
        System.exit(1);
    }

    private void setCurrentScreenButtons(boolean visible) {
        //reminder: always set button states to 0
        exitButton.setVisible(!visible);
        if (mostRecentScreen==0) {
            startButton.setVisible(visible);
            currentStartButtonState=0;
        }
        else {

        }
    }
    private int getRandomIntBetween(int min, int max) {
        return  ThreadLocalRandom.current().nextInt(min, max + 1);
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
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!settingsMenuActive) {

        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if (settingsMenuActive) {
            if (e.getSource()==exitButton && SwingUtilities.isLeftMouseButton(e)) {
                audio.playClickSound();
                currentExitButtonState=2;
            }
        }
        if (e.getSource()==startButton && SwingUtilities.isLeftMouseButton(e)) {
            audio.playClickSound();
            currentStartButtonState=2;
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (!settingsMenuActive) {
            if (e.getSource() == startButton && currentStartButtonState == 2 && SwingUtilities.isLeftMouseButton(e)) {
                startButton.setVisible(false);
                audio.stopSoundTrack(0);
                audio.playSoundtrack(1);
                currentScreenState = 1;
            }
        }
        else {
            if (e.getSource() == exitButton && currentExitButtonState == 2 && SwingUtilities.isLeftMouseButton(e)) {
                stopGame();
            }
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        if (!settingsMenuActive) {
            if (e.getSource() == startButton) {
                currentStartButtonState = 1;
            }
        }
        else {
            if (e.getSource() == exitButton) {
                currentExitButtonState = 1;
            }
        }
    }
    @Override
    public void mouseExited(MouseEvent e) {
        if (!settingsMenuActive) {
            if (e.getSource() == startButton) {
                currentStartButtonState = 0;
            }
        }
        else {
            if (e.getSource() == exitButton) {
                currentExitButtonState = 0;
            }
        }
    }

    public class EscapeAction extends  AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!settingsMenuActive) {
                audio.pauseSoundTrack(mostRecentScreen);
                setCurrentScreenButtons(false);
                settingsMenuActive = true;
            }
            else {
                currentScreenState = mostRecentScreen;
                audio.playSoundtrack(mostRecentScreen);
                setCurrentScreenButtons(true);
                settingsMenuActive = false;
            }
        }
    }
}