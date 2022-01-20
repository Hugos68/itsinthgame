import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Image {

    BufferedImage backGroundImage;
    BufferedImage endGameScreen;
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

    BufferedImage arrowLinksImageGrey;
    BufferedImage arrowRechtsImageGrey;

    BufferedImage arrowRechtsImageRed;
    BufferedImage arrowLinksImageRed;

    BufferedImage donerGuy;
    BufferedImage supplyManager;
    BufferedImage buildingManager;
    BufferedImage elonmusk;

    //redbuilding
    BufferedImage redBuilding1;
    BufferedImage redBuilding2;
    BufferedImage redBuilding3;
    BufferedImage redBuilding4;
    BufferedImage redBuilding5;

    //yellowbuilding
    BufferedImage yellowbuilding;
    BufferedImage yellowbuilding2;
    BufferedImage yellowbuilding3;
    BufferedImage yellowbuilding4;
    BufferedImage yellowbuilding5;

    //Bleubuilding
    BufferedImage bluebuilding1;
    BufferedImage bluebuilding2;
    BufferedImage bluebuilding3;
    BufferedImage bluebuilding4;


    BufferedImage textLastWarning;



    ArrayList<BufferedImage> buildingListRedBuilding;
    ArrayList<BufferedImage> buildingListYellowBuilding;
    ArrayList<BufferedImage> buildinglistBlueBuilding;


    Image() throws IOException {
        backGroundImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("background.jpg")));
        endGameScreen = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("endscreen.png")));
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
        purplecarpixel = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("purplecarpixel.png")));
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

        arrowLinksImageGrey = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("PijllinksGrey.png")));
        arrowRechtsImageGrey = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("PijlrechtsGrey.png")));

        arrowRechtsImageRed = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("PijlrechtsRood.png")));
        arrowLinksImageRed = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("PijllinksRood.png")));

        supplyManager = ImageIO.read(Objects.requireNonNull((getClass().getClassLoader().getResource("supplymeneer.png"))));

        buildingManager = ImageIO.read(Objects.requireNonNull((getClass().getClassLoader().getResource("buildingmeneer.png"))));

        donerGuy = ImageIO.read(Objects.requireNonNull((getClass().getClassLoader().getResource("donerpausemeneer.png"))));

        elonmusk = ImageIO.read(Objects.requireNonNull((getClass().getClassLoader().getResource("elonmusk.png"))));

        textLastWarning = ImageIO.read(Objects.requireNonNull((getClass().getClassLoader().getResource("lastwarningtext.png"))));



        //redbuilding
        redBuilding1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("redbuilding1.png")));
        redBuilding2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("redbuilding2.png")));
        redBuilding3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("redbuilding3.png")));
        redBuilding4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("redbuilding4.png")));
        redBuilding5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("redbuilding5.png")));

        //yellowbuilding
        yellowbuilding = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("yellowbuilding.png")));
        yellowbuilding2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("yellowbuilding2.png")));
        yellowbuilding3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("yellowbuilding3.png")));
        yellowbuilding4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("yellowbuilding4.png")));
        yellowbuilding5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("yellowbuilding5.png")));

        //Bluebuilding
        bluebuilding1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("bluebuilding1.png")));
        bluebuilding2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("bluebuilding2.png")));
        bluebuilding3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("bluebuilding3.png")));
        bluebuilding4 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("bluebuilding4.png")));

        buildingListRedBuilding = new ArrayList<>();
        buildingListRedBuilding.add(redBuilding1);
        buildingListRedBuilding.add(redBuilding2);
        buildingListRedBuilding.add(redBuilding3);
        buildingListRedBuilding.add(redBuilding4);
        buildingListRedBuilding.add(redBuilding5);

        buildingListYellowBuilding = new ArrayList<>();
        buildingListYellowBuilding.add(yellowbuilding);
        buildingListYellowBuilding.add(yellowbuilding2);
        buildingListYellowBuilding.add(yellowbuilding3);
        buildingListYellowBuilding.add(yellowbuilding4);
        buildingListYellowBuilding.add(yellowbuilding5);

        buildinglistBlueBuilding = new ArrayList<>();
        buildinglistBlueBuilding.add(bluebuilding1);
        buildinglistBlueBuilding.add(bluebuilding2);
        buildinglistBlueBuilding.add(bluebuilding3);
        buildinglistBlueBuilding.add(bluebuilding4);
    }
}