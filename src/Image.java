import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Image {

    BufferedImage backGroundImage;
    BufferedImage titleText;
    BufferedImage settingsMenu;
    BufferedImage startButtonIdleImage;
    BufferedImage startButtonHoverImage;
    BufferedImage startButtonClickImage;
    BufferedImage redBuilding;
    BufferedImage redcarpixel;
    BufferedImage greencarpixel;
    BufferedImage pinkcarpixel;
    BufferedImage bluecarpixel;
    BufferedImage orangecarpixel;
    BufferedImage purplecarpixel;
    BufferedImage stopButtonIdleImage;
    BufferedImage stopButtonHoverImage;
    BufferedImage stopButtonClickImage;

    Image() throws IOException {
        backGroundImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("background.png")));
        titleText = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("titletext.png")));
        settingsMenu = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("settingsmenutest.png")));
        startButtonIdleImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("startbuttonidle.png")));
        startButtonHoverImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("startbuttonhover.png")));
        startButtonClickImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("startbuttonclick.png")));
        redBuilding = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("redbuilding5.png")));
        redcarpixel = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("redcarpixel.png")));
        greencarpixel = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("greencarpixel.png")));
        pinkcarpixel = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("pinkcarpixel.png")));
        bluecarpixel = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("bluecarpixel.png")));
        orangecarpixel = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("orangecarpixel.png")));
        purplecarpixel= ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("purplecarpixel.png")));
        stopButtonIdleImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("stopbuttonidle.png")));
        stopButtonHoverImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("stopbuttonhover.png")));
        stopButtonClickImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("stopbuttonclick.png")));
    }
}
