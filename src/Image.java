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

    Image() throws IOException {
        backGroundImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("background.png")));
        titleText = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("titletext.png")));
        settingsMenu = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("settingsmenutest.png")));
        startButtonIdleImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("startbuttonidle.png")));
        startButtonHoverImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("startbuttonhover.png")));
        startButtonClickImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("startbuttonclick.png")));
        redBuilding = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("redbuilding5.png")));
    }
}
