import java.util.*;


public class Garage <C extends CarModels>{
    private List<C> cars;
    private int maxCars;

    public Garage(int maxCars){
        this.maxCars = maxCars;
        this.cars = new ArrayList<>();
    }

    public void loadCar(C car){
        if (cars.size() < maxCars) {
            cars.add(car);
        }
        else {
            throw new IllegalStateException("Garage is full.");
        }
    }

    public C unloadCar(C car){
        if (cars.contains(car)) {
            cars.remove(car);
            System.out.println("Unloading " + car.getModelName() + " from garage");
            return car;
        }
        else {throw new IllegalStateException(car + " is not in garage.");}
    }

    public List getGarage() {
        return cars;
    }

    public int getSizeGarage() {
        return cars.size();
    }

}
