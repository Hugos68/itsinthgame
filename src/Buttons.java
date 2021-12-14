import javax.swing.*;
import java.awt.*;

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
    JButton moveScreenButtonLeft; int currentMoveScreenButtonStateLeft;

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
    }
}
