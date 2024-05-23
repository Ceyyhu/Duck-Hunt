
import java.util.ArrayList;

public class Level {
    private int deadDuck;
    private int level;
    private int maxAmmo;
    private boolean gameOver = false;



    public ArrayList<Duck> duckList = new ArrayList<>();

    /**
     * sets the level number of the level.
     * calls the setLevel method to start the level.
     * @param level 1-6
     */
    Level(int level){
        this.level = level;
        setLevel();
    }

    /**
     * This method is called when a duck is shot. Increases the deadDuck attribute by 1.
     */
    public void setDeadDuck(){
        deadDuck += 1;
    }
    public void setGameOver(){gameOver = true;}
    public boolean getGameOver(){return gameOver;}

    /**
     * Returns the amount of ducks that has been shot.
     * @return amount of dead ducks.
     */
    public int getDeadDuck(){
        return deadDuck;
    }
    public int getLevel(){return level;}

    /**
     * The amount of ammo you can use on this particular level.
     * @return max amount
     */
    public int getMaxAmmo(){return maxAmmo;}

    /**
     * Sets the ammo and spawns the ducks.
     */
    public void setLevel(){
        switch(level){
            case 1:
                maxAmmo = 3;
                duckList.add(new Duck(60,0, "black",0, 0.25));
                break;
            case 2:
                maxAmmo = 3;
                duckList.add(new Duck(60,0, "black",0, 0.25));
                duckList.add(new Duck(-60,0, "red", 1, 0.25));
                break;
            case 3:
                maxAmmo = 3;
                duckList.add(new Duck(-45,-45, "blue",1, 1));
                break;
            case 4:
                maxAmmo = 6;
                duckList.add(new Duck(-45,-45, "blue",1, 1));
                duckList.add(new Duck(45,45, "blue",0, 0));
                break;
            case 5:
                maxAmmo = 6;
                duckList.add(new Duck(60,0, "black",0, 0.25));
                duckList.add(new Duck(-60,0, "red", 1, 0.25));
                duckList.add(new Duck(-45,-45, "blue",1, 1));
                duckList.add(new Duck(45,45, "blue",0, 0));
                break;
            case 6:
                maxAmmo = 6;
                duckList.add(new Duck(-45,-45, "blue",1, 1));
                duckList.add(new Duck(45,45, "red",0, 0));
                duckList.add(new Duck(45,-45, "black",0, 1));
                duckList.add(new Duck(-45,45, "blue",1, 0));
                break;
        }
    }

}
