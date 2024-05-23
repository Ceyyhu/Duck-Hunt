
public class Player {
    private int ammo;

    /**
     * Sets the ammo attribute to the given number, if type parameter is 'minus' then substracts the amount instead of setting.
     * @param amount the amount that we are setting / subtracting.
     * @param type minus or default
     */
    public void setAmmo(int amount, String type){
        if(type.equals("minus")){
            ammo -= amount;
        } else{
            ammo = amount;
        }
    }

    /**
     * returns the ammo attribute.
     * @return ammo
     */
    public int getAmmo(){
        return ammo;
    }
}
