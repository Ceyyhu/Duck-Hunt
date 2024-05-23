import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.io.File;

public class Duck {
    private String color;
    private double speedX;
    private double speedY;
    int curAnim;
    private double initialX;
    private double initialY;
    private boolean dying = false;
    private ImageView body;
    private Timeline flyingAnim;

    /**
     * Sets the attributes of the duck.
     * @param speedX velocity of the duck in the X coordinate.
     * @param speedY velocity of the duck in the Y coordinate.
     * @param color color of the duck.
     * @param initialX Initial position ratio(compared to the scene) of the duck. Goes from 0 to 1. X coordinate.
     * @param initialY Initial position ratio(compared to the scene) of the duck. Goes from 0 to 1. Y coordinate.
     */
    Duck(double speedX, double speedY, String color, double initialX, double initialY){
        this.color = color;
        this.speedX = speedX;
        this.speedY = speedY;
        this.initialX = initialX;
        this.initialY = initialY;
        curAnim = 0;

        playAnim();
    }

    /**
     * This method, moves the duck on the screen, and plays the flapping animation.
     * This method is also responsible for the reflection from the walls.
     */
    public void playAnim(){
        Image first = new Image(new File(String.format("C:/Users/PC/Desktop/as4/src/assets/duck_%s/4.png",color)).toURI().toString());
        body = new ImageView(first);
        body.setFitWidth(first.getWidth() * DuckHunt.SCALE);
        body.setFitHeight(first.getHeight() * DuckHunt.SCALE);

        body.setX((ScreenGui.gameRoot.getWidth() - body.getBoundsInLocal().getWidth()) * initialX);
        body.setY((ScreenGui.gameRoot.getHeight() - body.getBoundsInLocal().getHeight()) * initialY);

        body.setScaleX(speedX / Math.abs(speedX));
        if(speedY != 0){
            body.setScaleY(-speedY / Math.abs(speedY));
        }

        ScreenGui.gameRoot.getChildren().add(body);


        flyingAnim = new Timeline(new KeyFrame(Duration.millis(250),event -> {
            Image chosenImage;
            curAnim = (curAnim + 1) % 3;
            if(speedY != 0){
                chosenImage =  new Image(new File(String.format("C:/Users/PC/Desktop/as4/src/assets/duck_%s/%d.png",color, curAnim + 1)).toURI().toString());
            }else{
                chosenImage = new Image(new File(String.format("C:/Users/PC/Desktop/as4/src/assets/duck_%s/%d.png",color, curAnim + 4)).toURI().toString());
            }

            body.setFitWidth(chosenImage.getWidth() * DuckHunt.SCALE);
            body.setFitHeight(chosenImage.getHeight() * DuckHunt.SCALE);
            body.setImage(chosenImage);

            body.setX(body.getX() + speedX);
            body.setY(body.getY() + speedY);

            if(body.getX() <= 0 || body.getX() >= ScreenGui.gameRoot.getWidth() - body.getBoundsInLocal().getWidth()){
                speedX *= -1;
                double absValue = Math.abs(speedX);
                body.setScaleX(speedX / absValue);
            }

            if(body.getY() <= 0 || body.getY() >= ScreenGui.gameRoot.getHeight() - body.getBoundsInLocal().getHeight()){
                speedY *= -1;
                double absValue = Math.abs(speedY);
                body.setScaleY(-speedY / absValue);
            }

        }));
        flyingAnim.setCycleCount(-1);
        flyingAnim.play();
    }

    /**
     * This method plays the falling animation for the ducks, and moves them downwards.
     */
    public void playDyingAnim(){
        flyingAnim.stop();

        Image firstAnim = new Image(new File(String.format("C:/Users/PC/Desktop/as4/src/assets/duck_%s/%d.png",color, 7)).toURI().toString());
        body.setFitWidth(firstAnim.getWidth() * DuckHunt.SCALE);
        body.setFitHeight(firstAnim.getHeight() * DuckHunt.SCALE);
        body.setImage(firstAnim);

        if(body.getScaleY() == -1){
            body.setScaleY(1);
        }

        Timeline dyingAnim = new Timeline(new KeyFrame(Duration.millis(250),event -> {
            Image chosenImage = new Image(new File(String.format("C:/Users/PC/Desktop/as4/src/assets/duck_%s/%d.png",color, 8)).toURI().toString());

            body.setFitWidth(chosenImage.getWidth() * DuckHunt.SCALE);
            body.setFitHeight(chosenImage.getHeight() * DuckHunt.SCALE);
            body.setImage(chosenImage);

            body.setY(body.getY() + ScreenGui.gameRoot.getHeight()/10);

        }));
        dyingAnim.setCycleCount(-1);
        dyingAnim.play();
    }

    /**
     * This method returns the ImageView attribute of the ducks, which allows us to manipulate the image.
     * @return the imageview object which holds the image of the duck.
     */
    public ImageView getBody(){
        return body;
    }

    /**
     * returns true if duck has been shot.
     * @return true or false
     */
    public boolean getDying(){
        return dying;
    }

    /**
     * Sets dying attribute to true if the duck has been shot.
     */
    public void setDying(){
        dying = true;
    }

    /**
     * Removes the duck image from the root.
     */
    public void removeDuck(){ScreenGui.gameRoot.getChildren().remove(body);}
}
