
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.File;

public class ScreenGui {
    private StackPane titleRoot;
    private Pane backgroundRoot;
    public static Pane gameRoot;
    private Stage myStage;
    private Scene myScene;
    private static MediaPlayer myPlayer;
    private MediaPlayer introPlayer;
    private AudioClip newPlayer;
    private Text titleText;
    private Text titleTextExit;
    private ImageView titleBackground;
    private ImageView gameBackground;
    private ImageView backgroundImage;
    private ImageView foregroundImage;
    private String  currentScreen = "";
    private int selectedCursor;
    private int selectedBackground;
    private Level currentLevel;
    private Player player;
    private EventHandler<MouseEvent> mouseClicked;
    private boolean canHit = true;

    ScreenGui(Stage myStage){
        this.myStage = myStage;

        setTitleScreen();
        setBackgroundScreen();
        setGameScreen();
    }

    public void setTitleScreen(){
        titleRoot = new StackPane();
        myScene = new Scene(titleRoot,300 * DuckHunt.SCALE,270 * DuckHunt.SCALE);

        Image myBackground = new Image(new File("assets/welcome/1.png").toURI().toString());
        titleBackground = new ImageView(myBackground);
        titleBackground.setFitWidth(300 * DuckHunt.SCALE);
        titleBackground.setFitHeight(270 * DuckHunt.SCALE);

        titleRoot.getChildren().add(titleBackground);

        myStage.setScene(myScene);

        playTitleAudio();


        titleText = new Text("PRESS ENTER TO START");
        titleText.setFill(Color.ORANGE);
        titleText.setFont(Font.font("Arial",FontWeight.BOLD,18 * DuckHunt.SCALE));

        titleTextExit = new Text("PRESS ESC TO EXIT");
        titleTextExit.setFill(Color.ORANGE);
        titleTextExit.setFont(Font.font("Arial",FontWeight.BOLD,18 * DuckHunt.SCALE));

        titleRoot.getChildren().add(titleText);
        titleRoot.getChildren().add(titleTextExit);

        TextHandler.setFlasingText(titleText, titleTextExit, true);

        titleText.setTranslateY(myScene.getHeight()/5);
        titleTextExit.setTranslateY(myScene.getHeight()/3.5);


        myStage.show();

        ScreenEvent();
    }

    public void setBackgroundScreen(){
        backgroundRoot = new Pane();
        Image myBackground = new Image(new File("assets/background/1.png").toURI().toString());
        backgroundImage = new ImageView(myBackground);
        backgroundImage.setFitWidth(300 * DuckHunt.SCALE);
        backgroundImage.setFitHeight(270 * DuckHunt.SCALE);

        backgroundRoot.getChildren().add(backgroundImage);

        String path = String.format("assets/crosshair/%d.png",selectedCursor+1);
        Image myCrosshair = new Image(new File(path).toURI().toString());
        Cursor myCursor = new ImageCursor(myCrosshair);
        backgroundRoot.setCursor(myCursor);

        Text textUpper = new Text("USE ARROW KEYS TO NAVIGATE");
        textUpper.setFont(Font.font("Arial",FontWeight.BOLD,6 * DuckHunt.SCALE));
        textUpper.setFill(Color.ORANGE);
        textUpper.setY(myScene.getHeight()/12);
        textUpper.setX(myScene.getWidth()/2 - textUpper.getLayoutBounds().getWidth()/2);

        Text textMiddle = new Text("PRESS ENTER TO START");
        textMiddle.setFont(Font.font("Arial",FontWeight.BOLD,6 * DuckHunt.SCALE));
        textMiddle.setFill(Color.ORANGE);
        textMiddle.setY(textUpper.getY() + textUpper.getLayoutBounds().getHeight());
        textMiddle.setX(myScene.getWidth()/2 - textMiddle.getLayoutBounds().getWidth()/2);

        Text textLower = new Text("PRESS ESC TO EXIT");
        textLower.setFont(Font.font("Arial",FontWeight.BOLD,6 * DuckHunt.SCALE));
        textLower.setFill(Color.ORANGE);
        textLower.setY(textMiddle.getY() + textMiddle.getLayoutBounds().getHeight());
        textLower.setX(myScene.getWidth()/2 - textLower.getLayoutBounds().getWidth()/2);

        backgroundRoot.getChildren().add(textUpper);
        backgroundRoot.getChildren().add(textMiddle);
        backgroundRoot.getChildren().add(textLower);
    }

    public void setGameScreen(){
        gameRoot = new Pane();

        Image myBackground = new Image(new File("assets/background/1.png").toURI().toString());
        gameBackground = new ImageView(myBackground);
        gameBackground.setFitWidth(300 * DuckHunt.SCALE);
        gameBackground.setFitHeight(270 * DuckHunt.SCALE);

        Image myForeground = new Image(new File("assets/foreground/1.png").toURI().toString());
        foregroundImage = new ImageView(myForeground);

        foregroundImage.setFitWidth(300 * DuckHunt.SCALE);
        foregroundImage.setFitHeight(270 * DuckHunt.SCALE);
        gameRoot.getChildren().add(foregroundImage);

        gameRoot.getChildren().add(gameBackground);

        TextHandler.createTextLevelAmmo();
        TextHandler.setLevelBetweenText();

    }

    /**
     * Returns to the Title Screen from the game. Disconnects the mouse clicked event.
     * Sets the cursor and background to default.
     */
    public void returnToTitle(boolean fromBackgroundSelection){
        selectedBackground = 0;
        selectedCursor = 0;
        changeBackground();
        changeCursor();

        if(!fromBackgroundSelection){
            myScene.removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseClicked);
            playTitleAudio();
        }
        myScene.setRoot(titleRoot);
        ScreenEvent();
    }

    /**
     * Changes the root to the backgroundRoot.
     * Calls the backgroundEvent function, which runs the keys pressed event.
     */
    public void backgroundSelection(){
        myScene.setRoot(backgroundRoot);
        backgroundEvent();
    }

    /**
     * Starts the game for the first time. ( Only runs if the game was started from title screen. )
     */
    public void startGame(){
        String path = String.format("assets/background/%d.png",selectedBackground+1);
        Image myBackground = new Image(new File(path).toURI().toString());
        gameBackground.setImage(myBackground);

        Image myCrosshair = new Image(new File(String.format("assets/crosshair/%d.png",selectedCursor+1)).toURI().toString());
        Cursor myCursor = new ImageCursor(myCrosshair);
        gameRoot.setCursor(myCursor);

        addForeGround();

        myPlayer.dispose();

        String introPath = "assets\\effects\\Intro.mp3";
        Media myMedia = new Media(new File(introPath).toURI().toString());
        introPlayer = new MediaPlayer(myMedia);
        introPlayer.setVolume(DuckHunt.VOLUME);
        introPlayer.play();

        introPlayer.setOnEndOfMedia(() -> {
            myScene.setRoot(gameRoot);
            player = new Player();
            startLevel();
            foregroundImage.toFront();
            hitEvent();
        });
    }

    /**
     * Title screen event. Connects the key pressed event. If enter is pressed, background selection screen appears, If esc is pressed, game is closed.
     */
    public void ScreenEvent(){
        EventHandler<KeyEvent> keyPressed =  new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ESCAPE ) {
                    myScene.removeEventHandler(KeyEvent.KEY_PRESSED, this);
                    myStage.close();
                } else if(event.getCode() == KeyCode.ENTER) {
                    myScene.removeEventHandler(KeyEvent.KEY_PRESSED, this);
                    backgroundSelection();
                }
            }
        };

        myScene.addEventHandler(KeyEvent.KEY_PRESSED, keyPressed);
    }

    /**
     * This event enables the background and cursor selection.
     */
    public void backgroundEvent(){
        EventHandler<KeyEvent> keyPressed =  new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case UP:
                        selectedCursor = (selectedCursor + 1) % 7;
                        changeCursor();
                        break;
                    case DOWN:
                        selectedCursor = (selectedCursor - 1 + 7) % 7;
                        changeCursor();
                        break;
                    case RIGHT:
                        selectedBackground = (selectedBackground + 1) % 6;
                        changeBackground();
                        break;
                    case LEFT:
                        selectedBackground = (selectedBackground - 1 + 6) % 6;
                        changeBackground();
                        break;
                    case ENTER:
                        myScene.removeEventHandler(KeyEvent.KEY_PRESSED, this);
                        startGame();
                        break;
                    case ESCAPE:
                        myScene.removeEventHandler(KeyEvent.KEY_PRESSED, this);
                        returnToTitle(true);
                }
            }
        };

        myScene.addEventHandler(KeyEvent.KEY_PRESSED, keyPressed);
    }

    /**
     * This method is called between levels. If enter is pressed, game is progressed to the next level. If Escape is pressed, then game returns to the title screen.
     */
    public void gameEvent(){
        EventHandler<KeyEvent> keyPressed =  new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case ESCAPE:
                        if(currentLevel.getLevel() != 6 && !currentLevel.getGameOver()){return;}
                        myScene.removeEventHandler(KeyEvent.KEY_PRESSED, this);
                        for(Duck duck: currentLevel.duckList){
                            duck.removeDuck();
                        }
                        currentLevel = null;
                        player = null;
                        canHit = true;
                        returnToTitle(false);
                        TextHandler.hideText();
                        newPlayer.stop();
                        break;
                    case ENTER:
                        myScene.removeEventHandler(KeyEvent.KEY_PRESSED, this);
                        canHit = true;
                        for(Duck duck: currentLevel.duckList){
                            duck.removeDuck();
                        }
                        startLevel();
                        TextHandler.hideText();
                        newPlayer.stop();
                        break;
                }
            }
        };

        myScene.addEventHandler(KeyEvent.KEY_PRESSED, keyPressed);
    }

    /**
     * changes the background to the currently selected background.
     */
    public void changeBackground(){
        String path = String.format("assets/background/%d.png",selectedBackground+1);
        Image myBackground = new Image(new File(path).toURI().toString());
        backgroundImage.setImage(myBackground);
    }

    /**
     * Adds foreground to the screen .
     */
    public void addForeGround(){
        Image myForeground = new Image(new File(String.format("assets/foreground/%d.png", selectedBackground+1)).toURI().toString());
        foregroundImage.setImage(myForeground);
        foregroundImage.toFront();
    }

    /**
     * Changes the mouse cursor to the selected cursor.
     */
    public void changeCursor(){

        String path = String.format("assets/crosshair/%d.png",selectedCursor+1);
        Image myCrosshair = new Image(new File(path).toURI().toString());
        ImageCursor myCursor = new ImageCursor(myCrosshair);
        backgroundRoot.setCursor(myCursor);
    }

    /**
     * This method is for the hitbox. Compares mouse position to all the ducks position on the screen and detects the hit.
     * If ducks has been hit, calls the necessary methods.
     * After every gun shot, checks if the level is completed or game is over.
     * Updates the ammo text after every gunshot.
     */
    public void hitEvent(){
        mouseClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!canHit){return;}
                playAudio("Gunshot");
                player.setAmmo(1,"minus");
                TextHandler.updateTextAmmo(player.getAmmo());

                double mousePosY = event.getSceneY();
                double mousePosX = event.getSceneX();

                for(Duck duck: currentLevel.duckList){
                    if(duck.getDying()){continue;}

                    ImageView duckBody = duck.getBody();

                    double duckPosY = duckBody.getY() + duckBody.getBoundsInLocal().getHeight()/2;
                    double duckPosX = duckBody.getX() + duckBody.getBoundsInLocal().getWidth()/2;
                    double hitboxRangeY = duckBody.getBoundsInLocal().getHeight()/2 * 1.25;
                    double hitboxRangeX = duckBody.getBoundsInLocal().getWidth()/2 * 1.25;

                    if(Math.abs(mousePosY - duckPosY) <= hitboxRangeY && Math.abs(mousePosX - duckPosX) <= hitboxRangeX ){
                        duck.setDying();
                        duck.playDyingAnim();
                        currentLevel.setDeadDuck();

                        playAudio("DuckFalls");
                    }
                }

                int deadDuck = currentLevel.getDeadDuck();
                int totalDuck = currentLevel.duckList.size();

                if(player.getAmmo() == 0 && deadDuck < totalDuck){

                    canHit = false;
                    gameOver();
                    gameEvent();
                } else if(deadDuck >= totalDuck){
                    canHit = false;
                    if(currentLevel.getLevel() != 6){
                        TextHandler.levelCompleted();
                        playAudio("LevelCompleted");
                    } else{
                        TextHandler.gameFinished();
                        playAudio("GameCompleted");
                    }
                    gameEvent();
                }
            }
        };
        myScene.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClicked);
    }

    /**
     * Starts the next level. If current level is null, then starts the game from 1st level.
     * Updates the level and ammo texts at the start of every level.
     */
    public void startLevel(){
        if(currentLevel != null){
            if (currentLevel.getGameOver()){
                currentLevel = null;
            }
        }

        if(currentLevel == null){
            currentLevel = new Level(1);
            player.setAmmo(currentLevel.getMaxAmmo(), "default");
            TextHandler.updateTextAmmo(player.getAmmo());
            TextHandler.updateTextLevel(currentLevel.getLevel());
            foregroundImage.toFront();
            return;
        }

        if(currentLevel.getLevel() == 6){
            // game finished
            currentLevel = null;
            startLevel();
            foregroundImage.toFront();
        } else {
            currentLevel = new Level(currentLevel.getLevel() + 1);
            player.setAmmo(currentLevel.getMaxAmmo(), "default");
            TextHandler.updateTextAmmo(player.getAmmo());
            TextHandler.updateTextLevel(currentLevel.getLevel());
            foregroundImage.toFront();
        }
    }

    /**
     * Plays the audio with the given name.
     * @param name Name of the audio to play.
     */
    public void playAudio(String name){
        String path = null;
        switch (name){
            case "Intro":
                path = "assets\\effects\\Intro.mp3";
                break;
            case "DuckFalls":
                path = "assets\\effects\\DuckFalls.mp3";
                break;
            case "Gunshot":
                path = "assets\\effects\\Gunshot.mp3";
                break;
            case "LevelCompleted":
                path = "assets\\effects\\LevelCompleted.mp3";
                break;
            case "GameCompleted":
                path = "assets\\effects\\GameCompleted.mp3";
                break;
            case "GameOver":
                path = "assets\\effects\\GameOver.mp3";
                break;
        }
        //Media myMedia = new Media(new File(path).toURI().toString());
        newPlayer = new AudioClip(new File(path).toURI().toString());
        newPlayer.setVolume(DuckHunt.VOLUME);
        newPlayer.play();
    }

    /**
     * Plays the title screen music in loop.
     */
    public void playTitleAudio(){
        String path = "assets\\effects\\Title.mp3";
        Media myMedia = new Media(new File(path).toURI().toString());

        myPlayer = new MediaPlayer(myMedia);
        myPlayer.setVolume(DuckHunt.VOLUME);
        myPlayer.setAutoPlay(true);
        myPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    /**
     * Plays the game over audio and sets the level's game over attribute to true.
     */
    public void gameOver(){
        playAudio("GameOver");
        currentLevel.setGameOver();
        TextHandler.gameOver();
    }
}
