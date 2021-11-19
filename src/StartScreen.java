import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StartScreen extends JPanel implements ActionListener {
    GameInfo info = new GameInfo();
    JButton start;
    public StartScreen(){
        //button properties
        start = new JButton();
        start.setBounds(info.GAMEWIDTH/2-100,info.GAMEHEIGHT/2-50,200,100);
        start.setFont(new Font("Ink Free",Font.BOLD,40));
        start.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
        start.setText("Start");
        start.setBackground(Color.YELLOW);
        start.addActionListener(this);

        this.setLayout(null);
        this.add(start);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        //TODO DRAW BUTTON AND LINE BUTTON COLLISIONS WITH DRAWN BUTTON
        //draw background
        //TODO MAKE BACKGROUND LESS BRIGHT
        Image image = new ImageIcon("background (1).png").getImage();
        g2D.drawImage(image,0,0,null);
        //homescreen title
        g2D.setColor(Color.GRAY);
        g2D.setFont(new Font("Ink Free",Font.BOLD,70));
        g2D.drawString("Saxion Campus Tycoon",300,150);
        //copyright text
        g2D.setPaint(Color.BLACK);
        g2D.setFont(new Font("Ink Free",Font.BOLD,25));
        g2D.drawString("\u00a9"+" Copyrighted by Hugo, Cas and Robin",0,675);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==start) {
            //TODO GO TO GAMESCREEN
        }
    }
}
