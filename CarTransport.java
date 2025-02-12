import java.awt.*;
import java.util.Stack;

public class CarTransport extends Truck{
    private Stack<CarModels> loadedCars;

    public CarTransport(Color color){
        super(color, "CarTransport", 1);
        this.loadedCars = new Stack<>();
    }
    // 0 - ramp uppe, går ej skicka på bilar
    // 1 - ramp nere, går ej skicka på bilar

    public void loadCar(CarModels car) {
        double xDistance = getPositionX() - car.getPositionX();
        double yDistance = getPositionY() - car.getPositionY();

        if (getLorryAngle() == 1 && Math.abs(xDistance) <= 1 && Math.abs(yDistance) <= 1 && loadedCars.size()<8
            && !(car instanceof CarTransport)) {
            loadedCars.push(car);
            car.setCurrentPositionX(getPositionX());
            car.setCurrentPositionY(getPositionY());
        }
        else {throw new IllegalArgumentException("if-statement not ok!");}
    }

    public CarModels unloadCar() {
        if (getLorryAngle() == 1 && ! loadedCars.isEmpty()){
            CarModels car = loadedCars.pop();
            switch (getDirection()) {
                case 0 -> car.setCurrentPositionY((getPositionY() - 1));
                case 1 -> car.setCurrentPositionX((getPositionX() - 1));
                case 2 -> car.setCurrentPositionY((getPositionY() + 1));
                default -> car.setCurrentPositionX((getPositionX() + 1));
            }
            return car;
        }
        else {throw new IllegalArgumentException("Angle not ok!");}

    }

    @Override
    public void move(){
        super.move();
        for (CarModels car: loadedCars) {
           car.setCurrentPositionX(getPositionX());
           car.setCurrentPositionY(getPositionY());
        }
    }

    public void raiseLorry() {
        super.raiseLorry(1);
    }

    public void lowerLorry() {
        super.lowerLorry(1);
    }
}


