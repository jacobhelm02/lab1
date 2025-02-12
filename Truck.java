import java.awt.*;

public class Truck extends CarModels{
    private int lorryAngle;
    private int maxAngle;

    public Truck(Color color, String modelName, int maxAngle) {
        super(2, 100, color, modelName);
        this.lorryAngle = 0;
        this.maxAngle = maxAngle;
    }


    public int getLorryAngle(){
        return lorryAngle;
    }

    public void raiseLorry(int amount){
        if (amount >=0 && getCurrentSpeed() == 0) {
            lorryAngle = Math.min((lorryAngle + amount), maxAngle);
        } else {
            throw new IllegalArgumentException("The vehicle is moving or amount is smaller than 0");
        }

    }

    public void lowerLorry(int amount){
        if (amount >=0 && getCurrentSpeed() == 0) {
            lorryAngle = Math.max((lorryAngle - amount), 0);
        } else {
            throw new IllegalArgumentException("The vehicle is moving or amount is smaller than 0");
        }
    }

    @Override
    public void gas(double amount) {
        if (lorryAngle == 0){
            super.gas(amount);
        }
        else {throw new IllegalArgumentException("The lorry is raised");}
    }


    @Override
    protected double speedFactor() {
        return getEnginePower()*0.01;
    }
}
