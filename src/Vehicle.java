import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Vehicle {

    final int gameWidth = 1920;
    final int gameHeight = (int) (gameWidth * 0.5625);

    ArrayList<Image> vehicles;
    Image randomImage;
    Image image;
    int velocity;
    int X;
    int Y;
    int width;
    int height;
    int rightBorder;
    int leftBorder;

    public Vehicle(String vehicleType) {
        vehicles = new ArrayList<>(5);
        vehicles.add(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("redcarpixel.png"))).getImage());
        vehicles.add(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("greencarpixel.png"))).getImage());
        vehicles.add(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("pinkcarpixel.png"))).getImage());
        vehicles.add(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("bluecarpixel.png"))).getImage());
        vehicles.add(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("orangecarpixel.png"))).getImage());
        vehicles.add(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("purplecarpixel.png"))).getImage());

        this.randomImage = vehicles.get(getRandomIntBetween(0, 5));
        this.image = this.randomImage;
        this.width = this.randomImage.getWidth(null);
        this.height = this.randomImage.getHeight(null);

        if (vehicleType=="left") {
            this.velocity = 7;
            this.X = -getRandomIntBetween(250, 2500);
            this.Y = (int) ((double) gameHeight * 0.81944444444); //885
            this.rightBorder = gameWidth;
            this.leftBorder = this.X - 1;
        }
        if (vehicleType=="right") {
            this.velocity = -7;
            this.X = gameWidth + getRandomIntBetween(250, 2500);
            this.Y = (int) ((double) gameHeight * 0.91666666666); //990
            this.rightBorder = this.X + this.width + 1;
            this.leftBorder = 0;
        }
    }
    public void updateVehicles() {

        //check for collisions and set corresponding velocity
        if (this.X > this.rightBorder || this.X < this.leftBorder) {

            //set new random color that isn't previous color
            Image previousthis = this.image;
            do {this.image = vehicles.get(getRandomIntBetween(0,5));} while (this.image == previousthis);

            this.velocity = this.velocity * -1;
        }
        this.X = this.X + this.velocity;

        //set randomized borders when vehicle crosses screen
        if (this.X > gameWidth/2-5 && this.X < gameWidth/2+5) {
            this.rightBorder = gameWidth + getRandomIntBetween(250, 2500);
            this.leftBorder = -getRandomIntBetween(250, 2500);
        }
    }
    
    public void drawVehicles(Graphics2D g2D) {
        if (this.velocity < 0) {
            g2D.drawImage(this.image, this.X, this.Y, null);
        }

        else {
            g2D.drawImage(this.image, this.X + this.width, this.Y, -this.width, this.height, null);
        }

    }

    public int getRandomIntBetween(int min, int max) {
        return  ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}