import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class TextHandler {
    public static Timeline flasingTitle;
    public static Text textAmmo;
    public static Text textLevel;
    public static Text upperText = new Text();
    public static Text middleText = new Text();
    public static Text lowerText = new Text();
    public static Timeline flashingText;

    /**
     * this method makes the given texts disappear and appear in loop.
     * @param text first text
     * @param text2 second text
     * param  titleScreen , is true if is used on title screen
     */
    public static void setFlasingText(Text text, Text text2, boolean titleScreen){
        if (titleScreen){
            flasingTitle = new Timeline(new KeyFrame(Duration.millis(500), event -> {text.setVisible(true);text2.setVisible(true);}), new KeyFrame(Duration.millis(1000),event -> {text.setVisible(false);text2.setVisible(false);}));
            flasingTitle.setCycleCount(-1);
            flasingTitle.play();
        } else {
            flashingText = new Timeline(new KeyFrame(Duration.millis(500), event -> {text.setVisible(true);text2.setVisible(true);}), new KeyFrame(Duration.millis(1000),event -> {text.setVisible(false);text2.setVisible(false);}));
            flashingText.setCycleCount(-1);
            flashingText.play();
        }
    }

    /**
     * this method makes the given single text disappear and appear in loop.
     * @param text text to use the effect on
     */
    public static void setFlasing(Text text){
        flashingText = new Timeline(new KeyFrame(Duration.millis(500), event -> text.setVisible(true)), new KeyFrame(Duration.millis(1000),event -> text.setVisible(false)));
        flashingText.setCycleCount(-1);
        flashingText.play();
    }


    /**
     * This method creates texts for the remaining ammo, and current level
     */
    public static void createTextLevelAmmo(){
        textLevel = new Text("Level 1/6");
        textLevel.setFont(Font.font("Arial",FontWeight.BOLD,6 * DuckHunt.SCALE));
        textLevel.setFill(Color.ORANGE);
        textLevel.setY(textLevel.getLayoutBounds().getHeight());
        textLevel.setX(DuckHunt.myStage.getScene().getWidth()/2 - textLevel.getLayoutBounds().getWidth()/2);

        textLevel.setText("Level 1/6");
        ScreenGui.gameRoot.getChildren().add(textLevel);

        textAmmo = new Text("Ammo Left: 6");
        textAmmo.setFont(Font.font("Arial", FontWeight.BOLD,6 * DuckHunt.SCALE));
        textAmmo.setFill(Color.ORANGE);
        textAmmo.setY(textLevel.getLayoutBounds().getHeight());
        textAmmo.setX(DuckHunt.myStage.getScene().getWidth()- textAmmo.getLayoutBounds().getWidth() * 21/20);
        ScreenGui.gameRoot.getChildren().add(textAmmo);
    }

    /**
     * This method updates the level text to the new value.
     * @param level new value
     */
    public static void updateTextLevel(int level){
        String newText = String.format("Level %d/6", level);
        textLevel.setText(newText);
    }

    /**
     * This method updates the ammo text to the new value.
     * @param ammo new value, remaining ammo
     */
    public static void updateTextAmmo(int ammo){
        String newText = String.format("Ammo Left: %d", ammo);
        textAmmo.setText(newText);
    }

    /**
     * Initial setup for the between level texts, such as game over, game completed, level completed.
     */
    public static void setLevelBetweenText(){
        upperText.setFont(Font.font("Arial", FontWeight.BOLD,12 * DuckHunt.SCALE));
        upperText.setFill(Color.ORANGE);
        upperText.setVisible(false);

        middleText.setFont(Font.font("Arial", FontWeight.BOLD,12 * DuckHunt.SCALE));
        middleText.setFill(Color.ORANGE);
        middleText.setVisible(false);

        lowerText.setFont(Font.font("Arial", FontWeight.BOLD,12 * DuckHunt.SCALE));
        lowerText.setFill(Color.ORANGE);
        lowerText.setVisible(false);


        //TextHandler.setFlasing(middleText);
        ScreenGui.gameRoot.getChildren().add(upperText);
        ScreenGui.gameRoot.getChildren().add(middleText);
        ScreenGui.gameRoot.getChildren().add(lowerText);
    }

    /**
     * Sets the Level Completed screen. Plays the flashing effects for texts.
     */
    public static void levelCompleted(){
        upperText.setText("YOU WIN!");
        upperText.setY(DuckHunt.myStage.getScene().getHeight()/2 );
        upperText.setX(DuckHunt.myStage.getScene().getWidth()/2 - upperText.getLayoutBounds().getWidth()/2);
        upperText.setVisible(true);
        upperText.toFront();

        middleText.setText("Press ENTER to play next level!");
        middleText.setY(upperText.getY() * 11/10 );
        middleText.setX(DuckHunt.myStage.getScene().getWidth()/2 - middleText.getLayoutBounds().getWidth()/2);
        middleText.setVisible(true);
        middleText.toFront();

        TextHandler.setFlasing(middleText);
    }

    /**
     * Sets the Game Finished screen. Plays the flashing effects for texts.
     */
    public static void gameFinished(){
        upperText.setText("You have completed the game!");
        upperText.setY(DuckHunt.myStage.getScene().getHeight()/2 );
        upperText.setX(DuckHunt.myStage.getScene().getWidth()/2 - upperText.getLayoutBounds().getWidth()/2);
        upperText.setVisible(true);
        upperText.toFront();

        middleText.setText("Press ENTER to play again!");
        middleText.setY(upperText.getY() * 11/10 );
        middleText.setX(DuckHunt.myStage.getScene().getWidth()/2 - middleText.getLayoutBounds().getWidth()/2);
        middleText.setVisible(true);
        middleText.toFront();

        lowerText.setText("Press ESC to exit!");
        lowerText.setY(middleText.getY() * 11/10 );
        lowerText.setX(DuckHunt.myStage.getScene().getWidth()/2 - lowerText.getLayoutBounds().getWidth()/2);
        lowerText.setVisible(true);
        lowerText.toFront();

        TextHandler.setFlasingText(middleText, lowerText, false);
    }

    /**
     * Sets the Game Over screen. Plays the flashing effects for texts.
     */
    public static void gameOver(){
        upperText.setText("GAME OVER!");
        upperText.setY(DuckHunt.myStage.getScene().getHeight()/2 );
        upperText.setX(DuckHunt.myStage.getScene().getWidth()/2 - upperText.getLayoutBounds().getWidth()/2);
        upperText.setVisible(true);
        upperText.toFront();

        middleText.setText("Press ENTER to play again");
        middleText.setY(upperText.getY() * 11/10 );
        middleText.setX(DuckHunt.myStage.getScene().getWidth()/2 - middleText.getLayoutBounds().getWidth()/2);
        middleText.setVisible(true);
        middleText.toFront();

        lowerText.setText("Press ESC to exit");
        lowerText.setY(middleText.getY() * 11/10 );
        lowerText.setX(DuckHunt.myStage.getScene().getWidth()/2 - lowerText.getLayoutBounds().getWidth()/2);
        lowerText.setVisible(true);
        lowerText.toFront();

        TextHandler.setFlasingText(middleText, lowerText, false);
    }

    /**
     * Stops the flashing effect for the between level texts, and sets their visible to false.
     */
    public static void hideText(){
        flashingText.stop();
        upperText.setVisible(false);
        middleText.setVisible(false);
        lowerText.setVisible(false);
    }

}
