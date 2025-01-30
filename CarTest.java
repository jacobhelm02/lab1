import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

    @Test
    public void testSaab95Turbo() {
        Saab95 saab = new Saab95(Color.red);
        assertEquals(0, saab.getCurrentSpeed());
        saab.setTurboOn();
        assertEquals(1.625, saab.speedFactor(), 0.01);
        saab.setTurboOff();
        assertEquals(1.25, saab.speedFactor(), 0.01);
    }

    @Test
    public void testVolvo240SpeedFactor() {
        Volvo240 volvo = new Volvo240(Color.black);
        assertEquals(1.25, volvo.speedFactor(), 0.01);
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
        assertEquals(0.1, saab.getPositionY(), 0.01);
        assertEquals(0, saab.getPositionX(), 0.01);

        saab.turnRight();
        saab.move();
        assertEquals(0.1, saab.getPositionY(), 0.01);
        assertEquals(0.1, saab.getPositionX(), 0.01);

        saab.turnLeft();
        saab.turnLeft();
        saab.move();
        assertEquals(0.1, saab.getPositionY(), 0.01);
        assertEquals(0.0, saab.getPositionX(), 0.01);
    }
}

