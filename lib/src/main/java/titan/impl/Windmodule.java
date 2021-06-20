package titan.impl;

import java.util.Random;

public class Windmodule {

	private Vector2d wind;
	private Random generator;
	private final double airPressure=1.23995; //kg/m^3
	private final double area = 4*3.14*4; //4*Pi *r^2
	
	private double WindSpeed;
	private double height;//height of the probe when landing on the Titan
	
	

	public Windmodule() {
		this.setWind(getRandomWind());
	}


	public Vector2d getRandomWind() {
		// this is the random windForce module, the length of coordinate (x,y) is the windforce represented by vertor2d variable.
		
		Random generator = new Random(); // random stuff
		double windlevel = generator.nextInt(10); // the windlevel will be from 0 to 9

		double x = 0;
		double y = 0;

		if (windlevel < 3) {
			x = generator.nextInt(50); //random generator
			y = generator.nextInt(50);
		} else if (windlevel >= 3) {
			x = generator.nextInt(80);
			y = generator.nextInt(80);
		}
		
		//two if statement will influenced the direction of the windForce by slope of x and y.

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
		//this is a method that make the random windForce be stonger if the windforce is small for now.
		
		generator = new Random();
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

	public Vector2d influencedBydistanceFromSurface(){
		// this method is a model for the wind will be influenced by the height of the probe when landing on the surface.
		double x=Math.random()*100; 
		double y=Math.random()*100;
		for(int i=100;i>0;i--){ // it`s a simple stuff to represent, the more close to surface, the bigger y is.
			if(y==i)
			y=generator.nextInt(100-i); //Chen please check: generator has been made a field
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
		// we could use Linear formular to get the WindSpeed by tangent
		// let us set two point (0,0.1) and (100,120)
		// x is height(km) and y is windSpeed(m/s)
		
		
		WindSpeed=(119.9/100)*height+0.1;
		
		//when the WindSpeed reach to 120 becomes the maximum
		if(WindSpeed>120) {
			WindSpeed=120;
		}
		
		return WindSpeed;
	}
	

	
	public Vector2d calWindForceByWindSpeed() {
		/*
		 * this is another method different from the random stuff, the windForce depending on the wind Speed, so this is a new model.
		 *  Fw=1/2*P*v^2*A
		 *  
		 *  Fw=WindForce(N2)
		 *  A=surface area(m2)
		 *  P=density of air (kg/m^3)
		 *  v=WindSpeed(m/s)
		 */
		
		if(WindSpeed==0) {
			calWindSpeed();
		}
		// calculate windForce value
		double WindForce=0.5*airPressure*WindSpeed*WindSpeed*area;
		
		//direction random
		if (Math.random() < 0.5) {
			WindForce=WindForce;
		}
		else {
			WindForce=-WindForce;
		}
		Vector2d wind = new Vector2d(WindForce,0);
		return wind;
		
	}
	
	// getter and setter
	
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