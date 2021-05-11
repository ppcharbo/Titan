package titan.impl;

import titan.ProbeSimulatorInterface;
import titan.Vector3dInterface;
import titan.StateInterface;
import titan.ODEFunctionInterface;

/*
this class is a function about costing fuel when the probe moving and especially when Probe approch the surface of the moon
using the energy conservation theorem 

Vairable:
M: the rocket mass
m: mass of expell fuel
Ve:relative velocity
V0:initial velocity of rocket
Vr:final Velocity of the rocket

initial momentum=Final momentum
mass*velocity is momentum
(M+m)*V0=M*Vr+(V0-Ve)m

M(Vr-V0)/▲t=Ve*▲m/▲t

Thrust=F=Ma=Ve*(m#) m#:expelled of fuel mass flow rate.

M#=-m# (mass of space ship change and change of the use fuel)

ps:the max relative velocity is 20km/s
    the max thrustfoce is 30M N
    m#max= Fmax/Ve= 1.5k kg/s

*/

public class FuelCostProbe implements ProbeSimulatorInterface{
    
    //get acceleration
    Vector3dInterface accelerationForce = ship.accelerationForce();

    //get ship speed for initial and final
    Vector3dInterface speed = ship.getSpeed();

    //we need relative velocity between ship and fuel (i don`t know how to express this)

    //then we could use M(Vr-V0)/▲t=Ve*▲m/▲t this fomular to calculate ▲m/▲t, means m#

    

}
