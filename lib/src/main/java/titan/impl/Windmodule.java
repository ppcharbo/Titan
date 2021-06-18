package titan.impl;

import java.util.Random;

public class Windmodule {
    
    private vector3d wind;

    public Windmodule(){
        this.wind=getRandomWind();
    }

    public setWindforce(vector3d wind){
        this.wind = wind;
    }

    public vector3d getRandomWind(){
        Random generator=new Random();
        double windlevel=generator.nextInt(10);
        double x=0;
        double y=0;

        if(windlevel<3){
            x=generator.nextInt(50);
            y=generator.nextInt(50);
        }
        else if(windlevel>=3){
            x=generator.nextInt(80);
            y=generator.nextInt(80);
        }

        if(Math.random()<0.5){
            x=x-5;
        }
        if(Math.random()<0.5){
            y=y-5;
        }

        vector3d wind=new vector3d(x,y);
        return wind;
    }

    public void getStronger(){
        Random generator=new Random();
        x=0;
        y=0;
        while(x<50||y<50){
            x=generator.nextInt(90);
            y=generator.nextInt(90);
        }

        if(Math.random()<0.5){
            x=x-5;
        }
        if(Math.random()<0.5){
            y=y-5;
        }

        vector3d wind=new vector3d(x,y);
        return wind;
    }
}