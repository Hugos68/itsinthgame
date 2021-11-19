import javax.swing.*;

public class MyFrame extends JFrame {
    GameInfo info = new GameInfo();
    StartScreen startScreen;
    MyFrame(){
        startScreen = new StartScreen();
        GameScreen gameScreen = new GameScreen();


        //frame properties
        this.setTitle("Saxion Campus Tycoon");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(info.GAMEWIDTH,info.GAMEHEIGHT);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.add(startScreen);
    }
}
