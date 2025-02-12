import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

    @Test
    public void testSaab95Turbo() {
        Saab95 saab = new Saab95(Color.red);
        assertEquals(0, saab.getCurrentSpeed());
        saab.setTurboOn();
        assertEquals(1.625, saab.speedFactor());
        saab.setTurboOff();
        assertEquals(1.25, saab.speedFactor());
    }

    @Test
    public void testVolvo240SpeedFactor() {
        Volvo240 volvo = new Volvo240(Color.black);
        assertEquals(1.25, volvo.speedFactor());
    }

    @Test
    public void testGasAndBrake() {
        Volvo240 volvo = new Volvo240(Color.blue);
        volvo.startEngine();
        double initialSpeed = volvo.getCurrentSpeed();
        volvo.gas(0.5);
        assertTrue(volvo.getCurrentSpeed() > initialSpeed);

        double speedBeforeBrake = volvo.getCurrentSpeed();
        volvo.brake(0.5);
        assertTrue(volvo.getCurrentSpeed() < speedBeforeBrake);
    }

    @Test
    public void testGasAndBrakeLimits() {
        Volvo240 volvo = new Volvo240(Color.green);
        volvo.startEngine();

        // Test gas beyond limits
        assertThrows(IllegalArgumentException.class, () -> volvo.gas(-0.1));
        assertThrows(IllegalArgumentException.class, () -> volvo.gas(1.1));

        // Test brake beyond limits
        assertThrows(IllegalArgumentException.class, () -> volvo.brake(-0.1));
        assertThrows(IllegalArgumentException.class, () -> volvo.brake(1.1));
    }

    @Test
    public void testMoveAndTurn() {
        Saab95 saab = new Saab95(Color.red);
        saab.startEngine();
        saab.move();
        assertEquals(0.1, saab.getPositionY());
        assertEquals(0, saab.getPositionX());

        saab.turnRight();
        saab.move();
        assertEquals(0.1, saab.getPositionY());
        assertEquals(0.1, saab.getPositionX());

        saab.turnLeft();
        saab.turnLeft();
        saab.move();
        assertEquals(0.1, saab.getPositionY());
        assertEquals(0.0, saab.getPositionX());
    }

    @Test
    public void testLorryAngle() {
        Scania scania = new Scania(Color.blue);
        assertEquals(0, scania.getLorryAngle());
        double intialAngle = scania.getLorryAngle();
        scania.raiseLorry(50);
        double raisedAngle = scania.getLorryAngle();
        assertTrue(intialAngle < raisedAngle);

        scania.lowerLorry(25);
        double loweredAngle = scania.getLorryAngle();
        assertTrue(loweredAngle < raisedAngle);

        scania.raiseLorry(100);
        assertTrue(scania.getLorryAngle() <= 70);
        scania.lowerLorry(110);
        assertTrue(scania.getLorryAngle() >= 0);
    }

    @Test
    public void changeAngleSpeed() {
        Scania scania = new Scania(Color.magenta);
        scania.startEngine();
        assertThrows(IllegalArgumentException.class, () -> scania.raiseLorry(10));
        assertThrows(IllegalArgumentException.class, () -> scania.lowerLorry(10));

        scania.brake(1);
        scania.raiseLorry(25);
        assertThrows(IllegalArgumentException.class, () -> scania.gas(0.5));
    }

    @Test
    public void lorryAngleCartransport(){
        CarTransport carTransport = new CarTransport(Color.blue);
        assertEquals(0, carTransport.getLorryAngle());
        carTransport.raiseLorry();
        assertEquals(1,carTransport.getLorryAngle());

        carTransport.lowerLorry();
        assertEquals(0, carTransport.getLorryAngle());
    }

    @Test
    public void changeAngleSpeedCartransport() {
        CarTransport carTransport = new CarTransport(Color.cyan);
        carTransport.startEngine();
        assertThrows(IllegalArgumentException.class, () -> carTransport.raiseLorry());
        assertThrows(IllegalArgumentException.class, () -> carTransport.lowerLorry());

        carTransport.stopEngine();
        carTransport.raiseLorry();
        assertThrows(IllegalArgumentException.class, () -> carTransport.gas(0.5));
    }

    @Test
    public void testLoadCars(){
        CarTransport carTransport = new CarTransport(Color.black);
        Volvo240 volvo240 = new Volvo240(Color.gray);
        Saab95 saab95 = new Saab95(Color.red);
        CarTransport carTransport2 = new CarTransport(Color.blue);
        assertThrows(IllegalArgumentException.class, ()-> carTransport.loadCar(carTransport2));
        assertThrows(IllegalArgumentException.class, () -> carTransport.loadCar(volvo240));
        carTransport.raiseLorry();
        assertDoesNotThrow(() -> carTransport.loadCar(volvo240));

        saab95.setCurrentPositionX(5);
        saab95.setCurrentPositionY(3);
        assertThrows(IllegalArgumentException.class, () -> carTransport.loadCar(saab95));
    }

    @Test
    public void testUnloadCarsAndPosition() {
        CarTransport carTransport = new CarTransport(Color.black);
        Volvo240 volvo240 = new Volvo240(Color.gray);
        Saab95 saab95 = new Saab95(Color.red);
        carTransport.raiseLorry();
        volvo240.startEngine();
        volvo240.move();
        assertNotEquals(carTransport.getPositionY(), volvo240.getPositionY());
        carTransport.loadCar(volvo240);
        assertEquals(carTransport.getPositionY(), volvo240.getPositionY());
        carTransport.loadCar(saab95);

        carTransport.lowerLorry();
        carTransport.startEngine();
        carTransport.move();
        carTransport.turnRight();
        carTransport.move();
        assertEquals(carTransport.getPositionY(), volvo240.getPositionY());
        assertEquals(carTransport.getPositionX(), saab95.getPositionX());

        carTransport.stopEngine();
        carTransport.raiseLorry();
        assertEquals(saab95, carTransport.unloadCar());
        switch (carTransport.getDirection()) {
            case 0 -> assertEquals(carTransport.getPositionY() -1,saab95.getPositionY());
            case 1 -> assertEquals(carTransport.getPositionX() -1,saab95.getPositionX());
            case 2 -> assertEquals(carTransport.getPositionY() +1,saab95.getPositionY());
            default -> assertEquals(carTransport.getPositionX() +1,saab95.getPositionX());
        }
    }

    @Test
    public void testGarage() {
        Garage<Saab95> garage = new Garage<>(2);
        Saab95 saab = new Saab95(Color.red);
        Saab95 saab2 = new Saab95(Color.blue);
        Saab95 saab3 = new Saab95(Color.gray);
        Volvo240 volvo = new Volvo240(Color.CYAN);
        garage.loadCar(saab);
        garage.loadCar(saab2);
        assertThrows(IllegalStateException.class, () -> garage.loadCar(saab3));
        assertEquals(2, garage.getSizeGarage());
        garage.unloadCar(saab2);
        assertEquals(1, garage.getSizeGarage());
        assertThrows(IllegalStateException.class, () -> garage.unloadCar(saab3));

        Garage<CarModels> garageAll = new Garage<>(3);
        CarTransport carTransport = new CarTransport(Color.gray);
        Scania scania = new Scania(Color.GREEN);
        garageAll.loadCar(scania);
        garageAll.loadCar(carTransport);
        garageAll.loadCar(volvo);
        assertEquals(3, garageAll.getSizeGarage());
    }
}

