package titan.impl;

import java.util.Random;

public class Windmodule {

	private Vector2d wind;

	public Windmodule() {
		this.setWind(getRandomWind());
	}

	public void setWindforce(Vector2d wind) {
		this.setWind(wind);
	}

	public Vector2d getRandomWind() {
		Random generator = new Random();
		double windlevel = generator.nextInt(10);

		double x = 0;
		double y = 0;

		if (windlevel < 3) {
			x = generator.nextInt(50);
			y = generator.nextInt(50);
		} else if (windlevel >= 3) {
			x = generator.nextInt(80);
			y = generator.nextInt(80);
		}

		if (Math.random() < 0.5) {
			x = x - 5;
		}
		if (Math.random() < 0.5) {
			y = y - 5;
		}

		Vector2d wind = new Vector2d(x, y);
		return wind;
	}

	public Vector2d getStronger() {
		Random generator = new Random();
		double x = 0;
		double y = 0;
		while (x < 50 || y < 50) {
			x = generator.nextInt(90);
			y = generator.nextInt(90);
		}

		if (Math.random() < 0.5) {
			x = x - 5;
		}
		if (Math.random() < 0.5) {
			y = y - 5;
		}

		Vector2d wind = new Vector2d(x, y);
		return wind;
	}

	public vector2d influencedBydistanceFromSurface(){
		double y=Math.random()*100;
		for(int i=100;i>0;i--){
			if(y==i)
			y=generator.nextInt(100-i);
		}

		if (Math.random() < 0.5) {
			x = x - 5;
		}
		if (Math.random() < 0.5) {
			y = y - 5;
		}

		Vector2d wind = new Vector2d(x, y);
		return wind;
	}

	public Vector2d getWind() {
		return wind;
	}

	public void setWind(Vector2d wind) {
		this.wind = wind;
	}
}