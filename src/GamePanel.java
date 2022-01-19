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
    MovingObject firstMovingObject = new MovingObject(7, (this.getRandomIntBetween(250, 2500) * -1), (int) ((double) Constants.GAMEHEIGHT * 0.78),"vehicle");
    MovingObject secondMovingObject = new MovingObject(-7, Constants.GAMEWIDTH + this.getRandomIntBetween(250, 2500), (int) ((double) Constants.GAMEHEIGHT * 0.89), "vehicle");

    MovingObject cloud1 = new MovingObject(2, (this.getRandomIntBetween(250, 2500) * -1), (int) ((double) Constants.GAMEHEIGHT * 0.22),"cloud");
    MovingObject cloud2 = new MovingObject(2, Constants.GAMEWIDTH + this.getRandomIntBetween(250, 2500), (int) ((double) Constants.GAMEHEIGHT * 0.11),"cloud");

    //screen variables
    int currentScreenState = 0;
    int mostRecentScreen;
    boolean settingsMenuActive = false;
    // 0 = start screen
    // 1 = game screen

    //game screen variables
    int gameState;
    int balance;
    int supplyAmount;
    int frameCounter;
    int placeBuildingY;
    int placeBuildingX;
    int upgradePrice;
    double moneyMultiplier;
    boolean priceUpdated;
    boolean outOfSupplies;
    boolean suppliesDeclined;
    int additionalPlaceBuildingY;
    boolean greyLeftButton;
    boolean greyRightButton;

    String buyScreenBuilding;
    boolean blink;
    boolean donerBreak;
    boolean donerBreakDeclined;
    public GamePanel() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        startGame();
    }

    public void setGameScreenVariables() {
        gameState = 0;
        balance = 100000;
        supplyAmount = 500;
        frameCounter = 0;
        placeBuildingY = (int) (Constants.GAMEHEIGHT*0.35);
        placeBuildingX = Constants.GAMEWIDTH/2-image.redBuilding5.getWidth()/2;
        donerBreak = false;
        donerBreakDeclined = false;
        upgradePrice = 1000;
        priceUpdated = false;
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

        this.add(button.moveScreenButtonLeft);
        button.moveScreenButtonLeft.addMouseListener(this);

        this.add(button.moveScreenButtonRight);
        button.moveScreenButtonRight.addMouseListener(this);

        this.add(button.donerBreakAccept);
        button.donerBreakAccept.addMouseListener(this);

        this.add(button.donerBreakDecline);
        button.donerBreakDecline.addMouseListener(this);

        this.add(button.supplyAccept);
        button.supplyAccept.addMouseListener(this);

        this.add(button.supplyDecline);
        button.supplyDecline.addMouseListener(this);

        setGameScreenVariables();
        setSettingsMenuButtonStates(false);
        audio.playSoundtrack(0);
        gameThread.start();
    }

    //UPDATES
    public void update() {
        if (!settingsMenuActive && !donerBreak && !outOfSupplies) {
            updateAesthetics();
            updateScreen();
        }
        else if (outOfSupplies) {
            moneyMultiplier = (gameState * 2) - 2;
            button.buyButton.setVisible(false);
            button.supplyAccept.setVisible(true);
            button.supplyDecline.setVisible(true);
        }
        else if (donerBreak) {
            moneyMultiplier = (gameState * 2) - 2;
            button.buyButton.setVisible(false);
            button.donerBreakAccept.setVisible(true);
            button.donerBreakDecline.setVisible(true);
        }
    }
    public void updateAesthetics() {
        firstMovingObject.update();
        secondMovingObject.update();

        cloud1.update();
        cloud2.update();
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

        button.buyButton.setVisible(true);
        updateGameStateVariables();
        updateScreenMove();

    }

    public void updateGameStateVariables() {
        frameCounter++;
        switch (gameState) {
            case 1:
                moneyMultiplier = 1.5;
                break;
            case 2:
                moneyMultiplier = 2;
                break;
            case 3:
                moneyMultiplier = 4;
                break;
            case 4:
                moneyMultiplier = 6;
                break;
            case 5:
                moneyMultiplier = 8;
                break;
            case 6:
                moneyMultiplier = 10;
                break;
            case 7:
                moneyMultiplier = 12;
                break;
            case 8:
                moneyMultiplier = 14;
                break;
        }
        /* Voor building only*/
        if (frameCounter%60==0) {
            if (suppliesDeclined) {
                moneyMultiplier*=0.50;
            }
            if (donerBreakDeclined) {
                moneyMultiplier*=0.25;
            }
            if (!donerBreakDeclined && !suppliesDeclined) {
                moneyMultiplier = (gameState * 2) - 2;
            }
            if (gameState!=0) {
                balance += (int) (10 * moneyMultiplier);
                supplyAmount-=gameState+1;
            }
        }
        if (supplyAmount<0) {
            supplyAmount = 0;
            outOfSupplies=true;
        }
        if (gameState != 0) {
            buyScreenBuilding = "Saxion Version: " + (((gameState / 6)+1) + "." + gameState % 6);
        }else{
            buyScreenBuilding = "Saxion Version: 0.0";
        }
        if (getRandomIntBetween(0,8000)==8000/2 && gameState!=0) {
            donerBreak = true;
        }
        if (gameState % 6 == 0 && gameState != 0 && !priceUpdated){
            upgradePrice += 1000;
            priceUpdated = true;
        }
        if (gameState % 6 != 0){
            priceUpdated = false;
        }

    }
    public void updateScreenMove() {
        if (button.currentMoveScreenButtonStateRight==1 && !greyRightButton) {
            placeBuildingX -= 5;
        }
        if (button.currentMoveScreenButtonStateLeft == 1 && !greyLeftButton) {
            placeBuildingX += 5;
        }
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
        firstMovingObject.draw(g2D);
        secondMovingObject.draw(g2D);
        cloud1.draw(g2D);
        cloud2.draw(g2D);

    }
    public void drawScreen(Graphics2D g2D) {
        if (currentScreenState == 0) {
            drawStartScreen(g2D);
        }
        if (currentScreenState == 1) {
            drawGameScreen(g2D);
        }
        if (outOfSupplies) {
            drawOutofSupplies(g2D);
        }
        else if (donerBreak) {
            drawDonerBreak(g2D);
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
        drawSupplyCountdown(g2D);
        drawMoveScreenButton(g2D);
        drawPreviewBuilding(g2D);
    }
    public void drawBuildings(Graphics2D g2D) {

        if (gameState == 3 ){
            placeBuildingY = 380;
        }
        if (gameState == 4 ){
            placeBuildingY = 190;
        }
        if (gameState>5 && gameState<8){
            additionalPlaceBuildingY = 120;
        }


        if (gameState>0 && gameState<=5) {
            g2D.drawImage(image.buildingListRedBuilding.get(gameState-1),placeBuildingX,placeBuildingY,null);
        }
        if (gameState>=6) {
            g2D.drawImage(image.buildingListRedBuilding.get(4),placeBuildingX,placeBuildingY,null);
            g2D.drawImage(image.buildingListYellowBuilding.get((gameState - image.buildingListRedBuilding.size())-1),placeBuildingX+1200,placeBuildingY + additionalPlaceBuildingY,null);
        }
        if(gameState > 8){
            g2D.drawImage(image.buildingListRedBuilding.get(4),placeBuildingX,placeBuildingY,null);
            g2D.drawImage(image.buildingListYellowBuilding.get(2),placeBuildingX,placeBuildingY,null);
            if(image.buildinglistBlueBuilding.size() > 0){
                g2D.drawImage(image.buildinglistBlueBuilding.get(gameState - (image.buildingListRedBuilding.size() + image.buildingListYellowBuilding.size())),placeBuildingX,placeBuildingY,null);
            }
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
        g2D.drawString("Next building is:  €", 6, 24);
        Color newGreen = new Color(50, 180, 50);
        if (upgradePrice > balance){
            g2D.setPaint(Color.RED);
        }else {
            g2D.setPaint(newGreen);
        }
        g2D.drawString("" + upgradePrice, 188, 24);
        g2D.setPaint(Color.BLACK);
        g2D.drawString(buyScreenBuilding, 6,51);
    }
    public void drawBalance(Graphics2D g2D) {
        //BACKGROUND
        g2D.setPaint(Constants.saxionGreen);
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
    public void drawSupplyCountdown(Graphics2D g2D) {
        //BACKGROUND
        g2D.setPaint(Color.CYAN);
        g2D.fillRoundRect((int)((Constants.GAMEWIDTH/10) *8.958333333333333) -1, 122, 200,100, 10,10);
        //BORDER
        g2D.setPaint(Color.BLACK);
        Stroke oldStroke = g2D.getStroke();
        g2D.setStroke(new BasicStroke(2));
        g2D.drawRoundRect((int)((Constants.GAMEWIDTH/10) *8.958333333333333) -1, 122, 200,100, 10,10);
        g2D.setStroke(oldStroke);
        //TEKST
        g2D.setPaint(Color.BLACK);
        g2D.setFont(new Font("Ariel", Font.BOLD, 30));
        g2D.drawString("Supplies: ", (Constants.GAMEWIDTH/10)*9 -1, 160);
        g2D.setFont(new Font("Ariel", Font.BOLD, 36));
        g2D.drawString(""+supplyAmount, (Constants.GAMEWIDTH/10)*9 -1, 205);
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
    public void drawMoveScreenButton(Graphics2D g2D){
        if (placeBuildingX >= 405){
            greyLeftButton = true;
        }else{
            greyLeftButton = false;
        }
        if (gameState < 5){
            greyLeftButton = true;
            greyRightButton = true;
        }else{
            greyRightButton = false;
        }
        //Left Button
        {
            if (button.currentMoveScreenButtonStateLeft == 0 && greyLeftButton) {
                g2D.drawImage(image.arrowLinksImageGrey, 0, Constants.GAMEHEIGHT / 2, null);
            }
            if (button.currentMoveScreenButtonStateLeft == 0 && !greyLeftButton) {
                g2D.drawImage(image.arrowLinksImage, 0, Constants.GAMEHEIGHT / 2, null);
            }
            if (button.currentMoveScreenButtonStateLeft == 1 && !greyLeftButton) {
                g2D.drawImage(image.arrowLinksImageRed, 0, Constants.GAMEHEIGHT / 2, null);

            }
            if (button.currentMoveScreenButtonStateLeft == 1 && greyLeftButton) {
                g2D.drawImage(image.arrowLinksImageGrey, 0, Constants.GAMEHEIGHT / 2, null);

            }
        }
        // Right Button
        {
            if (button.currentMoveScreenButtonStateRight == 0 && !greyRightButton) {
                g2D.drawImage(image.arrowRechtsImage, Constants.GAMEWIDTH - 150, Constants.GAMEHEIGHT / 2, null);
            }
            if (button.currentMoveScreenButtonStateRight == 1 && !greyRightButton) {
                g2D.drawImage(image.arrowRechtsImageRed, Constants.GAMEWIDTH - 150, Constants.GAMEHEIGHT / 2, null);
            }
            if (button.currentMoveScreenButtonStateRight == 0 && greyRightButton) {
                g2D.drawImage(image.arrowRechtsImageGrey, Constants.GAMEWIDTH - 150, Constants.GAMEHEIGHT / 2, null);
            }
            if (button.currentMoveScreenButtonStateRight == 1 && greyRightButton) {
                g2D.drawImage(image.arrowRechtsImageGrey, Constants.GAMEWIDTH - 150, Constants.GAMEHEIGHT / 2, null);
            }
        }

    }
    public void drawOutofSupplies(Graphics2D g2D) {
        //TODO draw supply manager dialog
        g2D.drawImage(image.supplyManager,0,0,null);
    }
    public void drawDonerBreak(Graphics2D g2D) {
        g2D.drawImage(image.donerGuy,0,0,null);
    }
    public void drawSettingsScreen(Graphics2D g2D) {
        g2D.drawImage(image.settingsMenu,0,0,null);
        drawStopButton(g2D);
        drawMenuButton(g2D);

    }
    public void drawPreviewBuilding(Graphics2D g2D){
        if (button.currentBuyButtonState == 1){
            if (frameCounter%15==0) {
                blink = !blink;
            }

            if (!blink) {
                try {
                    g2D.drawImage(image.buildingListRedBuilding.get(gameState),placeBuildingX,placeBuildingY,null);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }

    //STOP GAME
    private void stopGame() {
        System.exit(1);
    }
    private void areYouSureWindow() {
        if (JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?",null, JOptionPane.YES_NO_OPTION) == 0) {
            stopGame();
        }
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
            if (e.getSource()==button.donerBreakAccept && SwingUtilities.isLeftMouseButton(e)) {
                audio.playClickSound();
                button.currentDonerBreakAcceptState = 2;
            }
            if (e.getSource()==button.donerBreakDecline && SwingUtilities.isLeftMouseButton(e)) {
                audio.playClickSound();
                button.currentDonerBreakDeclineState = 2;
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
                button.startButton.setVisible(false);
                currentScreenState = 1;
            }
            if (e.getSource() == button.buyButton && button.currentBuyButtonState == 2 && SwingUtilities.isLeftMouseButton(e)){
                if (balance >= upgradePrice) {
                    gameState += 1;
                    balance -= upgradePrice;
                    donerBreakDeclined = false;
                    suppliesDeclined = false;
                    audio.playBuildSound();
                }else{
                    audio.playErrorSound();
                }
                button.currentBuyButtonState = 2;
            }
            if (e.getSource() == button.supplyAccept && SwingUtilities.isLeftMouseButton(e)) {
                if (balance >= 1500) {
                    audio.playClickSound();
                    balance-=1500;
                    supplyAmount=500;
                    outOfSupplies=false;

                }
                else {
                    audio.playErrorSound();
                }

            }
            if (e.getSource() == button.supplyDecline && SwingUtilities.isLeftMouseButton(e)) {
                audio.playClickSound();
                suppliesDeclined=true;
                supplyAmount=500;
                outOfSupplies=false;
            }
            if (e.getSource()==button.donerBreakAccept && button.currentDonerBreakAcceptState == 2 && SwingUtilities.isLeftMouseButton(e)) {
                if (balance >= 800) {
                    audio.playClickSound();
                    balance-=800;
                    donerBreak=false;
                }
                else {
                    audio.playErrorSound();
                }

            }
            if (e.getSource()==button.donerBreakDecline && button.currentDonerBreakDeclineState == 2 && SwingUtilities.isLeftMouseButton(e)) {
                audio.playClickSound();
                donerBreakDeclined = true;
                donerBreak=false;
            }
        }
        else {
            if (e.getSource() == button.exitButton && button.currentExitButtonState == 2 && SwingUtilities.isLeftMouseButton(e)) {
                areYouSureWindow();
                button.currentExitButtonState=0;
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
            if (e.getSource() == button.moveScreenButtonLeft){
                button.currentMoveScreenButtonStateLeft = 1;
                if(firstMovingObject.velocity <0 && !greyLeftButton){
                    firstMovingObject.velocity = -1;
                }
                if(secondMovingObject.velocity < 0 && !greyLeftButton) {
                    secondMovingObject.velocity =-1;
                }
                if(firstMovingObject.velocity >0 && !greyLeftButton){
                    firstMovingObject.velocity = 1;
                }
                if(secondMovingObject.velocity > 0 && !greyLeftButton){
                    secondMovingObject.velocity =1;
                }
            }
            if (e.getSource() == button.moveScreenButtonRight){
                button.currentMoveScreenButtonStateRight = 1;
                if(firstMovingObject.velocity > 0 && !greyRightButton){
                    firstMovingObject.velocity = 1;
                }
                if(secondMovingObject.velocity > 0 && !greyRightButton){
                    secondMovingObject.velocity =1;

                }
                if(firstMovingObject.velocity < 0 && !greyRightButton){
                    firstMovingObject.velocity = -1;
                }
                if(secondMovingObject.velocity < 0 && !greyRightButton){
                    secondMovingObject.velocity =-1;
                }
            }
            if (e.getSource() == button.donerBreakAccept) {
                button.currentDonerBreakAcceptState = 1;
            }
            if (e.getSource() == button.donerBreakDecline) {
                button.currentDonerBreakDeclineState = 1;
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
            if (e.getSource() == button.moveScreenButtonLeft){
                button.currentMoveScreenButtonStateLeft = 0;
                if(firstMovingObject.velocity <0){
                    firstMovingObject.velocity = -7;
                }
                if(secondMovingObject.velocity <0){
                    secondMovingObject.velocity = -7;
                }
                if(firstMovingObject.velocity >0){
                    firstMovingObject.velocity = 7;
                }
                if(secondMovingObject.velocity >0){
                    secondMovingObject.velocity = 7;
                }
            }
            if (e.getSource() == button.moveScreenButtonRight){
                button.currentMoveScreenButtonStateRight = 0;
                if(firstMovingObject.velocity > 0){
                    firstMovingObject.velocity = 7;
                }
                if(secondMovingObject.velocity > 0){
                    secondMovingObject.velocity = 7;
                }
                if(firstMovingObject.velocity < 0){
                    firstMovingObject.velocity = -7;
                }
                if(secondMovingObject.velocity < 0){
                    secondMovingObject.velocity = -7;
                }
            }
            if (e.getSource() == button.donerBreakAccept) {
                button.currentDonerBreakAcceptState = 0;
            }
            if (e.getSource() == button.donerBreakDecline) {
                button.currentDonerBreakDeclineState = 0;
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