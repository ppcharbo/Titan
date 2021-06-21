package titan.impl;

import java.util.Random;
/**
 *  This class is responsible for creating the Wind.
 *  @author Group 12 
 */
public class Windmodule {

	private Vector2d wind;
	private Random generator;
	private final double airPressure=1.23995; // kg/m^3
	private final double area = 4*3.14*4; // 4*Pi *r^2
	
	private double WindSpeed;
	private double height;
	
	

	public Windmodule() {
		this.setWind(getRandomWind());
	}


	public Vector2d getRandomWind() {
		
		
		Random generator = new Random(); // random value
		double windlevel = generator.nextInt(10); // the wind level will be from 0 to 9

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
	/**
	 * this is a method that make the random windForce be stongger or smaller if the wind force is too small or big for now.
	 * @return
	 */
	public Vector2d fixWindForce() {
		
		generator = new Random();
		double x = 0;
		double y = 0;
		while (x < 20 && y < 20) {
			x = generator.nextInt(60);
			y = generator.nextInt(60);
		}
		
		while (x >90 && y >90 ) {
			x = generator.nextInt(60);
			y = generator.nextInt(60);
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
	
	
	/**
	 * this method is a model for the wind will be influenced by the height of the probe when landing on the surface.
	 * @return
	 */
	public Vector2d influencedBydistanceFromSurface(){
		
		double x=Math.random()*100; 
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
	
	public double calWindSpeed() {
		
		
		WindSpeed=(119.9/100)*height+0.1;
		
		//when the WindSpeed reach to 120 becomes the maximum
		if(WindSpeed>120) {
			WindSpeed=120;
		}
		
		return WindSpeed;
	}
	

	public Vector2d calWindForceByWindSpeed() {
		
		if(WindSpeed==0) {
			calWindSpeed();
		}
		
		double WindForce=0.5*airPressure*WindSpeed*WindSpeed*area; // calculate windForce value
		
	
		if (Math.random() < 0.5) {
			WindForce=WindForce;
		}
		else {
			WindForce=-WindForce;
		}
		Vector2d wind = new Vector2d(WindForce,0);
		return wind;
		
	}
	
	
	public double Area() {
		return area;
	}
	
	public double airPressure() {
		return airPressure;
	}
	
	public double getHeight() {
		return height;
	}
	
	public void setHeight(double height) {
		this.height=height;
	}
	
	public Vector2d getWind() {
		return wind;
	}

	public void setWind(Vector2d wind) {
		this.wind = wind;
	}
}