import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;


public class StartScreenFrame extends JFrame implements ActionListener, MouseListener {

    final int gameWidth = 1280;
    final int gameHeight = (int) (gameWidth * 0.5625);

    StartScreenPanel startScreenPanel;
    JButton button;

    StartScreenFrame() {
        startScreenPanel = new StartScreenPanel();
        button = new JButton();
        button.setBounds(gameWidth/2-100, gameHeight/2-50,200,100);
        button.setContentAreaFilled(false);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
        button.setBorder(border);
        button.setBorderPainted(false);
        button.addActionListener(this);
        button.addMouseListener(this);
        this.setTitle("Saxion Campus Tycoon");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(button);
        this.add(startScreenPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==button) {
            this.dispose();
            new MainGameScreenFrame();
        }
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
        button.setBorderPainted(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        button.setBorderPainted(false);
    }
}
class StartScreenPanel extends JPanel {

    final int gameWidth = 1280;
    final int gameHeight = (int) (gameWidth * 0.5625);

    Image background;

    StartScreenPanel() {
        background = new ImageIcon("saxionBackground.png").getImage();
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));

    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(background,0,0,null);
        g2D.setColor(Color.yellow);
        g2D.setFont(new Font("Bold", Font.BOLD, 75));
        g2D.drawString("Saxion Campus Tycoon",225,150);
        g2D.fillRect(gameWidth /2-100, gameHeight /2-50,200,100);
        g2D.setStroke(new BasicStroke(2));
        g2D.setColor(Color.black);
        g2D.drawRect(gameWidth /2-100, gameHeight /2-50,200,100);
        g2D.setFont(new Font("Bold", Font.BOLD, 40));
        g2D.drawString("Start",592,375);
    }

}


