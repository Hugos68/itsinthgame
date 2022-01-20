import javax.swing.*;

public class Buttons {

    //button variables
    //button states:
    //0 = idle
    //1 = hover
    //2 = click
    JButton startButton; int currentStartButtonState;
    JButton exitButton; int currentExitButtonState;
    JButton menuButton; int currentMenuButtonState;
    JButton buyButton; int currentBuyButtonState;
    JButton moveScreenButton; int currentMoveScreenButtonState;
    JButton donerBreakAccept;  int currentDonerBreakAcceptState;
    JButton donerBreakDecline; int currentDonerBreakDeclineState;
    JButton moveScreenButtonLeft; int currentMoveScreenButtonStateLeft;
    JButton moveScreenButtonRight; int currentMoveScreenButtonStateRight;
    JButton supplyAccept;
    JButton supplyDecline;
    JButton buildingAccept;
    JButton buildingDecline;
    JButton buildingGameover;
    JButton elonboostAccept;
    JButton elonboostDecline;


    Buttons() {
        startButton = new JButton();
        startButton.setBounds(Constants.GAMEWIDTH/2-75, Constants.GAMEHEIGHT/2-75,150,150);
        startButton.setOpaque(false);
        startButton.setBorderPainted(false);
        startButton.setFocusable(false);
        currentStartButtonState = 0;

        exitButton = new JButton();
        exitButton.setBounds(Constants.GAMEWIDTH/2-86,Constants.GAMEHEIGHT/2,173,101);
        exitButton.setOpaque(false);
        exitButton.setBorderPainted(false);
        exitButton.setFocusable(false);
        currentExitButtonState = 0;

        buyButton = new JButton();
        buyButton.setBounds(4,68,100,60);
        buyButton.setOpaque(false);
        buyButton.setBorderPainted(false);
        buyButton.setFocusable(false);
        currentBuyButtonState = 0;

        menuButton = new JButton();
        menuButton.setBounds(Constants.GAMEWIDTH/2-86,Constants.GAMEHEIGHT/2-109,173,101);
        menuButton.setOpaque(false);
        menuButton.setBorderPainted(false);
        menuButton.setFocusable(false);
        currentMenuButtonState = 0;

        moveScreenButtonLeft = new JButton();
        moveScreenButtonLeft.setBounds(0, Constants.GAMEHEIGHT /2,150,150);
        moveScreenButtonLeft.setOpaque(false);
        moveScreenButtonLeft.setBorderPainted(false);
        moveScreenButtonLeft.setFocusable(false);
        currentMoveScreenButtonStateLeft = 0;


        moveScreenButtonRight = new JButton();
        moveScreenButtonRight.setBounds(Constants.GAMEWIDTH - 150, Constants.GAMEHEIGHT /2,150,150);
        moveScreenButtonRight.setOpaque(false);
        moveScreenButtonRight.setBorderPainted(false);
        moveScreenButtonRight.setFocusable(false);
        currentMoveScreenButtonStateRight = 0;

        donerBreakAccept = new JButton();
        donerBreakAccept.setBounds(1000,400,180,100);
        donerBreakAccept.setOpaque(false);
        donerBreakAccept.setBorderPainted(false);
        donerBreakAccept.setFocusable(false);
        donerBreakAccept.setVisible(false);
        currentDonerBreakAcceptState = 0;

        donerBreakDecline = new JButton();
        donerBreakDecline.setBounds(1225,400,180,100);
        donerBreakDecline.setOpaque(false);
        donerBreakDecline.setBorderPainted(false);
        donerBreakDecline.setFocusable(false);
        donerBreakDecline.setVisible(false);
        currentDonerBreakDeclineState = 0;

        supplyAccept = new JButton();
        supplyAccept.setBounds(1000,400,180,100);
        supplyAccept.setOpaque(false);
        supplyAccept.setBorderPainted(false);
        supplyAccept.setFocusable(false);
        supplyAccept.setVisible(false);


        supplyDecline = new JButton();
        supplyDecline.setBounds(1225,400,180,100);
        supplyDecline.setOpaque(false);
        supplyDecline.setBorderPainted(false);
        supplyDecline.setFocusable(false);
        supplyDecline.setVisible(false);

        buildingAccept = new JButton();
        buildingAccept.setBounds(1000,400,180,100);
        buildingAccept.setOpaque(false);
        buildingAccept.setBorderPainted(false);
        buildingAccept.setFocusable(false);
        buildingAccept.setVisible(false);


        buildingDecline = new JButton();
        buildingDecline.setBounds(1225,400,180,100);
        buildingDecline.setOpaque(false);
        buildingDecline.setBorderPainted(false);
        buildingDecline.setFocusable(false);
        buildingDecline.setVisible(false);

        buildingGameover = new JButton();
        buildingGameover.setBounds(1430,400,180,100);
        buildingGameover.setOpaque(false);
        buildingGameover.setBorderPainted(false);
        buildingGameover.setFocusable(false);
        buildingGameover.setVisible(false);

        elonboostAccept = new JButton();
        elonboostAccept.setBounds(1000,400,180,100);
        elonboostAccept.setOpaque(false);
        elonboostAccept.setBorderPainted(false);
        elonboostAccept.setFocusable(false);
        elonboostAccept.setVisible(false);


        elonboostDecline = new JButton();
        elonboostDecline.setBounds(1225,400,180,100);
        elonboostDecline.setOpaque(false);
        elonboostDecline.setBorderPainted(false);
        elonboostDecline.setFocusable(false);
        elonboostDecline.setVisible(false);


    }
}
