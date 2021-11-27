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

        //objects
        Thread gameThread = new Thread(this);
        Action escape = new EscapeAction();
        Buttons button = new Buttons();
        Audio audio = new Audio();
        Image image = new Image();
        Vehicle firstVehicle = new Vehicle(7, (this.getRandomIntBetween(250, 2500) * -1), (int) ((double) Constants.GAMEHEIGHT * 0.81944444444));
        Vehicle secondVehicle = new Vehicle(-7, Constants.GAMEWIDTH + this.getRandomIntBetween(250, 2500), (int) ((double) Constants.GAMEHEIGHT * 0.91666666666));


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


    public void setGameScreenVariables() {
        balance = 1000;
        frameCounter = 0;
        placeBuildingY = (int) (Constants.GAMEHEIGHT*0.188);
    }



    //START GAME
    public void startGame() {
        this.setPreferredSize(new Dimension(Constants.GAMEWIDTH,Constants.GAMEHEIGHT));
        this.setDoubleBuffered(true);
        this.setLayout(null);
        //create escape key bind
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escapeAction");
        this.getActionMap().put("escapeAction", escape);
        this.add(button.startButton);
        button.startButton.addMouseListener(this);
        this.add(button.exitButton);
        button.exitButton.addMouseListener(this);
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
        button.startButton.setVisible(false);
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
        g2D.drawImage(image.titleText, Constants.GAMEWIDTH/2-image.titleText.getWidth(null)/2, Constants.GAMEHEIGHT/7, null);
        drawStartScreenButton(g2D);
    }
    public void drawStartScreenButton(Graphics2D g2D) {
        if (button.currentStartButtonState == 0) {
            g2D.drawImage(image.startButtonIdleImage,Constants.GAMEWIDTH/2- image.startButtonHoverImage.getWidth(null)/2, Constants.GAMEHEIGHT/2- image.startButtonHoverImage.getHeight(null)/2,null);
        }
        else if (button.currentStartButtonState == 1) {
            g2D.drawImage(image.startButtonHoverImage,Constants.GAMEWIDTH/2- image.startButtonHoverImage.getWidth(null)/2, Constants.GAMEHEIGHT/2- image.startButtonHoverImage.getHeight(null)/2,null);
        }
        else {
            g2D.drawImage(image.startButtonClickImage,Constants.GAMEWIDTH/2- image.startButtonClickImage.getWidth(null)/2, Constants.GAMEHEIGHT/2- image.startButtonClickImage.getHeight(null)/2,null);
        }
    }
    public void drawGameScreen(Graphics2D g2D) {
        drawBalance(g2D);
        g2D.drawImage(image.redBuilding,Constants.GAMEWIDTH/2-image.redBuilding.getWidth(null)/2,placeBuildingY,null);
    }
    public void drawBalance(Graphics2D g2D) {
        //TODO draw balance
    }
    public void drawSettingsScreen(Graphics2D g2D) {
        g2D.fillRect(Constants.GAMEWIDTH/2-300,Constants.GAMEHEIGHT/2-450,600,900);
        g2D.drawImage(image.settingsMenu,0,0,null);
        drawSettingsButton(g2D);

    }
    public void drawSettingsButton(Graphics2D g2D) {

        if (button.currentExitButtonState == 0) {
            g2D.drawImage(image.stopButtonIdleImage,Constants.GAMEWIDTH/2-image.stopButtonIdleImage.getWidth(null)/2,Constants.GAMEHEIGHT/2,null);
        }
        else if (button.currentExitButtonState == 1) {
            g2D.drawImage(image.stopButtonHoverImage,Constants.GAMEWIDTH/2-image.stopButtonHoverImage.getWidth(null)/2,Constants.GAMEHEIGHT/2+12,null);
        }
        else {
            g2D.drawImage(image.stopButtonClickImage,Constants.GAMEWIDTH/2-image.stopButtonClickImage.getWidth(null)/2,Constants.GAMEHEIGHT/2+12,null);
        }
    }

    //STOP GAME
    public void stopGame() {
        System.exit(1);
    }

    private void setCurrentScreenButton(boolean visible) {
        //reminder: always set button states to 0
        button.exitButton.setVisible(!visible);
        if (mostRecentScreen==0) {
            button.startButton.setVisible(visible);
            button.currentStartButtonState=0;
        }
        else {

        }
    }
    private int getRandomIntBetween(int min, int max) {
        return  ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    @Override
    public void run() {
        double frameTime = 1000000000f/Constants.FPS;
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
            if (e.getSource()==button.exitButton && SwingUtilities.isLeftMouseButton(e)) {
                audio.playClickSound();
                button.currentExitButtonState=2;
            }
        }
        if (e.getSource()==button.startButton && SwingUtilities.isLeftMouseButton(e)) {
            audio.playClickSound();
            button.currentStartButtonState=2;
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (!settingsMenuActive) {
            if (e.getSource() == button.startButton && button.currentStartButtonState == 2 && SwingUtilities.isLeftMouseButton(e)) {
                button.startButton.setVisible(false);
                audio.stopSoundTrack(0);
                audio.playSoundtrack(1);
                currentScreenState = 1;
            }
        }
        else {
            if (e.getSource() == button.exitButton && button.currentExitButtonState == 2 && SwingUtilities.isLeftMouseButton(e)) {
                stopGame();
            }
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        if (!settingsMenuActive) {
            if (e.getSource() == button.startButton) {
                button.currentStartButtonState = 1;
            }
        }
        else {
            if (e.getSource() == button.exitButton) {
                button.currentExitButtonState = 1;
            }
        }
    }
    @Override
    public void mouseExited(MouseEvent e) {
        if (!settingsMenuActive) {
            if (e.getSource() == button.startButton) {
                button.currentStartButtonState = 0;
            }
        }
        else {
            if (e.getSource() == button.exitButton) {
                button.currentExitButtonState = 0;
            }
        }
    }

    public class EscapeAction extends  AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!settingsMenuActive) {
                audio.pauseSoundTrack(mostRecentScreen);
                setCurrentScreenButton(false);
                settingsMenuActive = true;
            }
            else {
                currentScreenState = mostRecentScreen;
                audio.playSoundtrack(mostRecentScreen);
                setCurrentScreenButton(true);
                settingsMenuActive = false;
            }
        }
    }
}