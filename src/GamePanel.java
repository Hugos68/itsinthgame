import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class GamePanel extends JPanel implements Runnable, MouseListener {

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
    int mostRecentScreen;
    boolean settingsMenuActive = false;
    // 0 = start screen
    // 1 = game screen

    //game screen variables
    int gameState;
    int balance;
    int frameCounter;
    int placeBuildingY;
    double moneyMultiplier;
    String currentBuilding;
    int upgradePrice;
    String buyScreenBuilding;

    public GamePanel() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        startGame();
    }

    public void setGameScreenVariables() {
        gameState = 0;
        balance = 1000;
        frameCounter = 0;
        placeBuildingY = (int) (Constants.GAMEHEIGHT*0.35);
        moneyMultiplier = 1.05;
      }

    //START GAME
    public void startGame() {
        this.setPreferredSize(new Dimension(Constants.GAMEWIDTH,Constants.GAMEHEIGHT));
        this.setDoubleBuffered(true);
        this.setLayout(null);
        //create escape key bind
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escapeAction");
        this.getActionMap().put("escapeAction", escape);

        //add buttons
        this.add(button.startButton);
        button.startButton.addMouseListener(this);
        this.add(button.exitButton);
        button.exitButton.addMouseListener(this);
        this.add(button.menuButton);
        button.menuButton.addMouseListener(this);
        this.add(button.buyButton);
        button.buyButton.addMouseListener(this);
        setGameScreenVariables();
        setSettingsMenuButtonStates(false);
        audio.playSoundtrack(0);
        gameState = 1;
        gameThread.start();
    }

    //UPDATES
    public void update() {
        if (!settingsMenuActive) {
            updateAesthetics();
            updateScreen();
        }
        else {

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
        button.buyButton.setVisible(false);
        button.startButton.setVisible(true);
    }
    public void updateGameScreen() {
        button.startButton.setVisible(false);
        button.buyButton.setVisible(true);
        frameCounter++;
        if (frameCounter==120) {
            updateBalance();
            frameCounter=0;
        }
        updateNextBuilding();
    }
    public void updateNextBuilding() {
        currentBuilding = "Villa Serphos" ;
        upgradePrice = 1000;
        buyScreenBuilding = "" + currentBuilding + " Version: " + ((gameState / 5)+1) + "." + gameState % 5;

    }
    public void updateBalance() {
        switch (gameState) {
            case 1:
                balance += (int) (100 * moneyMultiplier);
            case 2:

        }

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
        drawBuildings(g2D);
        drawBuyButton(g2D);
    }
    public void drawBuildings(Graphics2D g2D) {
        switch (gameState) {
            case 1:
                g2D.drawImage(image.redBuilding1, Constants.GAMEWIDTH / 2 - image.redBuilding1.getWidth(null) / 2, placeBuildingY, null); break;
            case 2:
                g2D.drawImage(image.redBuilding1, Constants.GAMEWIDTH / 2 - image.redBuilding1.getWidth(null) / 2, placeBuildingY, null);
                g2D.drawImage(image.redBuilding2, Constants.GAMEWIDTH / 2 - image.redBuilding1.getWidth(null) / 2 + image.redBuilding1.getWidth(null), placeBuildingY, null); break;
        }
    }
    public void drawBuyButton(Graphics2D g2D) {
        g2D.setPaint(Color.GRAY);
        if (button.currentBuyButtonState == 0) {
            g2D.fillRoundRect(4,68,100,60,10,10);
            g2D.setPaint(Color.BLACK);
            g2D.drawString("BUY", 15,110);
        }
        else if (button.currentBuyButtonState == 1) {
            g2D.setPaint(Color.BLACK);
            g2D.fillRoundRect(4,68,100,60,10,10);
            g2D.setPaint(Color.GRAY);
            g2D.drawString("BUY", 15,110);
        }
        else {
            g2D.setPaint(Color.GRAY);
            g2D.fillRoundRect(4,68,100,60,10,10);
            g2D.setPaint(Color.BLACK);
            g2D.drawString("BUY", 15,110);

        }

        g2D.setPaint(Color.BLACK);
        g2D.setStroke(new BasicStroke(3));
        g2D.drawRoundRect(2,2,250,60,10,10);
        g2D.setFont(new Font("Ariel", Font.BOLD, 20));
        g2D.drawString("Next building is:  €" + upgradePrice, 6, 24);
        g2D.drawString(buyScreenBuilding, 6,51);
    }
    public void drawBalance(Graphics2D g2D) {
        //BACKGROUND
        Color saxionGreen = new Color(50,120,50);
        g2D.setPaint(saxionGreen);
        g2D.fillRoundRect((int)((Constants.GAMEWIDTH/10) *8.958333333333333) -1, 2, 200,100, 10,10);
        //BORDER
        g2D.setPaint(Color.BLACK);
        Stroke oldStroke = g2D.getStroke();
        g2D.setStroke(new BasicStroke(2));
        g2D.drawRoundRect((int)((Constants.GAMEWIDTH/10) *8.958333333333333) -1, 2, 200,100, 10,10);
        g2D.setStroke(oldStroke);
        //TEKST
        g2D.setPaint(Color.BLACK);
        g2D.setFont(new Font("Ariel", Font.BOLD, 30));
        g2D.drawString("Your money: ", (Constants.GAMEWIDTH/10)*9 -1, 40);
        g2D.setFont(new Font("Ariel", Font.BOLD, 36));
        g2D.drawString("€ " + balance , (Constants.GAMEWIDTH/10)*9 -1, 85);
    }
    public void drawSettingsScreen(Graphics2D g2D) {
        g2D.drawImage(image.settingsMenu,0,0,null);
        drawStopButton(g2D);
        drawMenuButton(g2D);

    }
    public void drawStopButton(Graphics2D g2D) {

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
    public void drawMenuButton(Graphics2D g2D) {
        if (button.currentMenuButtonState == 0) {
            g2D.drawImage(image.menuButtonIdleImage, Constants.GAMEWIDTH / 2 - image.menuButtonIdleImage.getWidth(null) / 2, Constants.GAMEHEIGHT / 2-121, null);
        } else if (button.currentMenuButtonState == 1) {
            g2D.drawImage(image.menuButtonHoverImage, Constants.GAMEWIDTH / 2 - image.menuButtonHoverImage.getWidth(null) / 2, Constants.GAMEHEIGHT / 2-109, null);
        } else {
            g2D.drawImage(image.menuButtonClickImage, Constants.GAMEWIDTH / 2 - image.menuButtonClickImage.getWidth(null) / 2, Constants.GAMEHEIGHT / 2-109, null);
        }
    }

    //STOP GAME
    public void stopGame() {
        System.exit(1);
    }


    private void setSettingsMenuButtonStates(boolean visibility) {
        button.exitButton.setVisible(visibility);
        button.menuButton.setVisible(visibility);
        if (currentScreenState==0) {
            button.startButton.setVisible(!visibility);
            button.currentStartButtonState=0;
        }
        if (currentScreenState==1) {
            button.buyButton.setVisible(!visibility);
            button.currentBuyButtonState=0;
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
        if (!settingsMenuActive) {
            if (e.getSource()==button.startButton && SwingUtilities.isLeftMouseButton(e)) {
                audio.playClickSound();
                button.currentStartButtonState=2;
            }
            if (e.getSource()==button.buyButton && SwingUtilities.isLeftMouseButton(e)){
                audio.playClickSound();
                button.currentBuyButtonState = 2;
            }
        }
        if (e.getSource()==button.exitButton && SwingUtilities.isLeftMouseButton(e)) {
            audio.playClickSound();
            button.currentExitButtonState=2;
        }
        if (e.getSource()== button.menuButton && mostRecentScreen!=0 && SwingUtilities.isLeftMouseButton(e)) {
            audio.playClickSound();
            button.currentMenuButtonState=2;
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (!settingsMenuActive) {
            if (e.getSource() == button.startButton && button.currentStartButtonState == 2 && SwingUtilities.isLeftMouseButton(e)) {
                audio.stopSoundTrack(0);
                audio.stopSoundTrack(1);
                audio.playSoundtrack(1);
                button.currentMenuButtonState = 0;
                button.currentExitButtonState = 0;
                currentScreenState = 1;
            }
            if (e.getSource() == button.buyButton && button.currentBuyButtonState == 2 && SwingUtilities.isLeftMouseButton(e)){
                audio.playBuildSound();
                gameState += 1;
                button.currentBuyButtonState = 2;
            }
        }
        else {
            if (e.getSource() == button.exitButton && button.currentExitButtonState == 2 && SwingUtilities.isLeftMouseButton(e)) {
                stopGame();
            }
            if (e.getSource() == button.menuButton && button.currentMenuButtonState == 2 && mostRecentScreen != 0 && SwingUtilities.isLeftMouseButton(e)) {
                button.currentMenuButtonState = 0;
                audio.playSoundtrack(0);
                currentScreenState = 0;
                setSettingsMenuButtonStates(false);
                settingsMenuActive = false;
            }
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        //TODO DRAW NEXT BUILDING IN GRAY WHEN HOVERING ON BUY BUTTON
        if (!settingsMenuActive) {
            if (e.getSource() == button.startButton) {
                button.currentStartButtonState = 1;
            }
            if (e.getSource() == button.buyButton){
                button.currentBuyButtonState = 1;
            }
        }
        else {
            if (e.getSource() == button.exitButton) {
                button.currentExitButtonState = 1;
            }
            if (e.getSource() == button.menuButton) {
                button.currentMenuButtonState = 1;
            }
        }
    }
    @Override
    public void mouseExited(MouseEvent e) {
        if (!settingsMenuActive) {
            if (e.getSource() == button.startButton) {
                button.currentStartButtonState = 0;
            }
            if (e.getSource() == button.buyButton){
                button.currentBuyButtonState = 0;
            }
        }
        else {
            if (e.getSource() == button.exitButton) {
                button.currentExitButtonState = 0;
            }
            if (e.getSource() == button.menuButton) {
                button.currentMenuButtonState = 0;
            }
        }
    }

    public class EscapeAction extends  AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!settingsMenuActive) {
                audio.pauseSoundTrack(mostRecentScreen);
                setSettingsMenuButtonStates(true);
                settingsMenuActive = true;
            }
            else {
                currentScreenState = mostRecentScreen;
                audio.playSoundtrack(mostRecentScreen);
                setSettingsMenuButtonStates(false);
                settingsMenuActive = false;
            }
        }
    }
}