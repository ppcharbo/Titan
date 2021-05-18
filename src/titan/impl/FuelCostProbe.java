package titan.impl;

import titan.ProbeSimulatorInterface;
import titan.Vector3dInterface;
import titan.StateInterface;
import titan.ODEFunctionInterface;

/*
this class is a function about costing fuel when the probe moving and especially when Probe approch the surface of the moon
using the energy conservation theorem 

Vairable:
Pt=Total Pressure
Tt=Total Temperatrue
P0=Free stream Pressure
γ=Specific Heat Ration
R=Gas Constant
A=Area
#m=Mass FLow Rate

Thrust: F=#mVe

Mass Flow rate: #m=(Area*Pt/Math.sqrt(total_temperatrue))-(Math.sqrt(Specific heat rate/Gas Constant))*Math.pow((Specific heat rate+1)/2,-1*((Specific heat ratio + 1)/(2*(Specific heat ratio-1))
                

ps:the max relative velocity is 20km/s
    the max thrustfoce is 30M N
    m#max= Fmax/Ve= 1.5k kg/s


*/


public class FuelCostProbe implements ProbeSimulatorInterface{
    
    private final double area;
    private final double total_pressure;
    private final double specific_heat_ratio;
    private final double Exit_mach;
    private final double mach; //for now don`t know this variable
    private final double gas_constant;
    private final double total_temperatrue;


    public static double calMassflowRate(){
        double fraction1= (area * total_presssure)/ Math.sqrt(total_temperatrue);

    }

}
