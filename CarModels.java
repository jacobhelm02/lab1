import java.awt.*;

public abstract class CarModels implements Movable{
    private int nrDoors; // Number of doors on the car
    private double enginePower; // Engine power of the car
    private double currentSpeed; // The current speed of the car
    private Color color; // Color of the car
    private String modelName; // The car model name
    private double currentPositionX;
    private double currentPositionY;
    private int direction; // 0=upp 1=höger 2=ner 3=vänster

    protected CarModels(int nrDoors,double enginePower, Color color, String modelName) {
        this.nrDoors=nrDoors;
        this.enginePower=enginePower;
        this.color=color;
        this.modelName=modelName;
        this.currentPositionX=0;
        this.currentPositionY=0;
        this.direction=0;
        stopEngine();
    }

    @Override
    public void move(){
        switch (direction) {
            case 0 -> currentPositionY+=currentSpeed;
            case 1 -> currentPositionX+=currentSpeed;
            case 2 -> currentPositionY -=currentSpeed;
            default -> currentPositionX -= currentSpeed;
        }
    }

    @Override
    public void turnLeft(){
        switch(direction){
            case 0 -> direction =3;
            default -> direction-=1;
        }
    }

    @Override
    public void turnRight(){
        switch(direction){
            case 3 -> direction =0;
            default -> direction+=1;
        }
    }

    public int getNrDoors(){
        return nrDoors;
    }

    public double getEnginePower(){
        return enginePower;
    }

    public double getCurrentSpeed(){
        return currentSpeed;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color clr){
	    color = clr;
    }

    public void startEngine(){
	    currentSpeed = 0.1;
    }

    public void stopEngine(){
	    currentSpeed = 0;
    }

    public double getPositionX(){
        return currentPositionX;
    }

    public double getPositionY(){
        return currentPositionY;
    }

    public int getDirection(){
        return direction;
    }


    
    protected abstract double speedFactor();

    public void incrementSpeed(double amount){
	    currentSpeed = Math.min(getCurrentSpeed() + speedFactor() * amount, enginePower);
    }

    public void decrementSpeed(double amount){
        currentSpeed = Math.max(getCurrentSpeed() - speedFactor() * amount,0);
    }

    // TODO fix this method according to lab pm
    public void gas(double amount){
        if (amount <=1 && amount >=0) {
            incrementSpeed(amount);
        } else {
            throw new IllegalArgumentException("Amount should be in [0,1]");
        }
    }

    // TODO fix this method according to lab pm
    public void brake(double amount){
        if (amount <=1 && amount >=0) {
            decrementSpeed(amount);
        } else {
            throw new IllegalArgumentException("Amount should be in [0,1]");
        }
    }
}
