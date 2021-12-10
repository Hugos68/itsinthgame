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
    BufferedImage redcarpixel;
    BufferedImage greencarpixel;
    BufferedImage pinkcarpixel;
    BufferedImage bluecarpixel;
    BufferedImage orangecarpixel;
    BufferedImage purplecarpixel;
    BufferedImage stopButtonIdleImage;
    BufferedImage stopButtonHoverImage;
    BufferedImage stopButtonClickImage;
    BufferedImage cloud1;
    BufferedImage cloud2;

    BufferedImage menuButtonIdleImage;
    BufferedImage menuButtonHoverImage;
    BufferedImage menuButtonClickImage;

    BufferedImage arrowRechtsImage;
    BufferedImage arrowLinksImage;

    BufferedImage donerGuy;

    //redbuilding
    BufferedImage redBuilding1;
    BufferedImage redBuilding2;
    BufferedImage redBuilding3;
    BufferedImage redBuilding4;
    BufferedImage redBuilding5;

    //yellowbuilding
    BufferedImage yellowbuilding;
    BufferedImage yellowbuilding2;

    Image() throws IOException {
        backGroundImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("background.jpg")));
        titleText = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("titletext.png")));
        settingsMenu = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("settingsmenutest.png")));
        startButtonIdleImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("startbuttonidle.png")));
        startButtonHoverImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("startbuttonhover.png")));
        startButtonClickImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("startbuttonclick.png")));
        redcarpixel = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("redcarpixel.png")));
        greencarpixel = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("greencarpixel.png")));
        pinkcarpixel = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("pinkcarpixel.png")));
        bluecarpixel = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("bluecarpixel.png")));
        orangecarpixel = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("orangecarpixel.png")));
        purplecarpixel= ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("purplecarpixel.png")));
        stopButtonIdleImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("stopbuttonidle.png")));
        stopButtonHoverImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("stopbuttonhover.png")));
        stopButtonClickImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("stopbuttonclick.png")));
        cloud1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("midcloud1.png")));
        cloud2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("midcloud2.png")));

        menuButtonIdleImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("menubuttonidle.png")));
        menuButtonHoverImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("menubuttonhover.png")));
        menuButtonClickImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("menubuttonclick.png")));

        arrowLinksImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("Pijllinks.png")));
        arrowRechtsImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("Pijlrechts.png")));

        donerGuy = ImageIO.read(Objects.requireNonNull((getClass().getClassLoader().getResource("donerpausemeneer.png"))));

        //redbuilding
        redBuilding1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("redbuilding1.png")));
        redBuilding2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("redbuilding2.png")));
        redBuilding3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("redbuilding3.png")));
        redBuilding4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("redbuilding4.png")));
        redBuilding5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("redbuilding5.png")));

        //yellowbuilding
        yellowbuilding = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("yellowbuilding.png")));
        yellowbuilding2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("yellowbuilding2.png")));



    }
}
