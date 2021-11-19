import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainGameScreenFrame extends JFrame implements ActionListener, MouseListener {

    final int gameWidth = 1280;
    final int gameHeight = (int) (gameWidth * 0.5625);

    MainGameScreenPanel mainGameScreenPanel;

    MainGameScreenFrame() {
        mainGameScreenPanel = new MainGameScreenPanel();

        this.setTitle("Saxion Campus Tycoon");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(mainGameScreenPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}

class MainGameScreenPanel extends JPanel {

    final int gameWidth = 1280;
    final int gameHeight = (int) (gameWidth * 0.5625);

    Image background;

    MainGameScreenPanel() {
        //TODO MAKE MAIN GAME BACKGROUND
        background = new ImageIcon("gameScreen.png").getImage();
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(background, 0, 0, null);

    }
}
