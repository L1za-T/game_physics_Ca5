package org.example;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        //region Initial Conditions
        double target = 1;
        double startTime = 0;
        double timeSteps = 10;

        //Gravity Vector
        double[] grav = new double[3];
        grav[0] = 0;
        grav[1] = 0;
        grav[2] = -9.81;

        //Normal Vector
        double[] nrml = new double[3];
        nrml[0] = -5;
        nrml[1] = 4;
        nrml[2] = 5;

        //Static and Kinetic Friction
        double statFric = 0.8;
        double kineFric = 0.3;
        //endregion

        //Block and Calculator initialisation
        Block block = new Block(0.3, new double[] {1,2,3}, new double [] {0,0,0});
        NetForceCalc calc = new NetForceCalc(block.getMass(), nrml, grav, statFric, kineFric);

        //print outs
        System.out.println("Block Details: ");
        System.out.println(block);
        System.out.println("Starting Conditions: ");
        System.out.println("Normal Vector: "+Arrays.toString(nrml));
        System.out.println("Force of Gravity: "+Arrays.toString(grav));
        System.out.println("Coefficient of static friction: " + statFric);
        System.out.println("Coefficient of kinetic friction: " + kineFric);

        // Force Calculations
        System.out.println("Force Calculations: ");
        System.out.println("Magnitude of Normal Vector: " + calc.getMagNrml());
        System.out.println("Direction of Normal Vector: " + Arrays.toString(calc.getDirectNrml()));
        System.out.println("Fg: " + Arrays.toString(calc.getFg()));
        System.out.println("Fgn: " + Arrays.toString(calc.getFgn()));
        System.out.println("Fgp: " + Arrays.toString(calc.getFgp()));
        System.out.println("Magnitude of Fgp: " + calc.getMagFgp());
        System.out.println("Fn: " + Arrays.toString(calc.getFn()));
        System.out.println("Magnitude of Fn: " + calc.getMagFn());
        System.out.println("Net force on block " + Arrays.toString(calc.getFnet()));

        double[] acceleration = new double[3];
        acceleration[0] = (1/block.getMass() * calc.getFnet()[0]);
        acceleration[1] = (1/block.getMass() * calc.getFnet()[1]);
        acceleration[2] = (1/block.getMass() * calc.getFnet()[2]);

        System.out.println("Acceleration: " + Arrays.toString(acceleration) + "m/s^2");

        double h = (target - startTime) / timeSteps;

        // mutliply accel by 1/kineticFric

//        //Test if static friction will keep the block static.
//
//        double Ff = (statFric * magFn);
//
//        System.out.println("Ff: " + Ff + " Fgp: " + magFgp);
//        if( Ff >= magFgp){
//            System.out.println("Friction is static!");
//            System.out.println("Ff: " + Ff);
//            System.out.println("Fgp: " + Arrays.toString(Fgp));
//            System.out.println(Ff + " > " + magFgp);
//        }
//        else{
//            System.out.println("Friction is kinetic!");
//        }

    }
    static void Euler(double[] position, double[] velocity, double[] acceleration, double step){


    }


}



/*
* Things needed:
* Initial conditions:
* mass, gravity, static and kinetic friction
*
* Normal vector
*
* fgn[0], fgp[0], fn[0]
*
* a check if the force is static or kinetic
*
* if kinetic, find direction of acceleration and rate of acceleration
* (i *think* euler's only applies to the acceleration and position)
* */

/*Everything up until Fnet only needs to be done once. That's all done to figure out acceleration.... *I think*. Is this ultimately a constant accel problem?*/