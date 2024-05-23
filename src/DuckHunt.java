

import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.application.Application;

import java.io.File;

public class DuckHunt extends Application {

    public static Stage myStage;
    public static final double SCALE = 3;
    public static final double VOLUME = 0.025;

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage primaryStage){

        myStage = new Stage();
        myStage.setTitle("Duck Hunt");
        myStage.setResizable(false);

        Image myIcon = new Image(new File("assets/favicon/1.png").toURI().toString());
        myStage.getIcons().add(myIcon);

        new ScreenGui(myStage);

    }
}
