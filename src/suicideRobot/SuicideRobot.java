package suicideRobot;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.TouchAdapter;
import lejos.utility.Delay;

public class SuicideRobot {

	static EV3 brick = (EV3) BrickFinder.getDefault();
	static int colorId;
	static int speed = 100;
	static int acceleration = 100;
	static boolean done = false;
	static TouchAdapter touchAdapter = null;
	static int angle = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Button.ESCAPE.addKeyListener(new lejos.hardware.KeyListener() {

			@Override
			public void keyPressed(Key k) {
				// TODO Auto-generated method stub
				done = true;

			}

			@Override
			public void keyReleased(Key k) {
				// TODO Auto-generated method stub
				done = true;
			}
		});
		act();
	}

	private static void act() {
		// TODO Auto-generated method stub
		try (EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(brick.getPort(("C")));
				EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(brick.getPort(("B")));
				EV3TouchSensor touchSensor = new EV3TouchSensor(brick.getPort("S2"));) {
			touchAdapter = new TouchAdapter(touchSensor);
			rightMotor.setAcceleration(acceleration);
			leftMotor.setAcceleration(acceleration);
			rightMotor.setSpeed(speed);
			leftMotor.setSpeed(speed);
			Sound.setVolume(100);

			while (!done) {
				if (!touchAdapter.isPressed()) {
					Sound.twoBeeps();
					leftMotor.backward();
					rightMotor.backward();
					Delay.msDelay(2500);
					angle = (int) (Math.random() * 269) + 1;
					LCD.drawString("Turning into: " + angle, 0, 0);
					leftMotor.rotate(angle);
				} else if (touchAdapter.isPressed()) {
					leftMotor.forward();
					rightMotor.forward();

				}
			}
		}

	}
}
