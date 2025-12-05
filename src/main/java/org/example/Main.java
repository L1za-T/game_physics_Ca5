package org.example;
import java.util.Arrays;
import java.util.Vector;


public class Main {
    public static void main(String[] args) {

        //Block parameters
        Block block = new Block(0.3, new double[] {1,2,3}, new double [] {0,0,0});

        //Normal Vector
        double[] nrml = new double[3];
        nrml[0] = -5;
        nrml[1] = 4;
        nrml[2] = 5;

        //Gravity Vector
        double[] grav = new double[3];
        grav[0] = 0;
        grav[1] = 0;
        grav[2] = -9.81;

        double statFric = 0.8;
        double kineFric = 0.3;

        //Normal vector magnitude
        double magNrml = magCalc(nrml);

        //Normal vector direction
        double[] directNrml = new double[3];
        directNrml[0] = (1/magNrml)*nrml[0];
        directNrml[1] = (1/magNrml)*nrml[1];
        directNrml[2] = (1/magNrml)*nrml[2];

        //Fg
        double[] Fg = new double[3];
        Fg[0] = grav[0];
        Fg[1] = grav[1];
        Fg[2] = (block.getMass() * grav[2]);

        //Fgn
        double[] Fgn = new double[3];
        Fgn[0] = dotProduct(Fg, directNrml)*directNrml[0]; //should be a negative for N[0] = -5
        Fgn[1] = dotProduct(Fg, directNrml)*directNrml[1];
        Fgn[2] = dotProduct(Fg, directNrml)*directNrml[2];

        //Fgp
        double[] Fgp = new double[3];
        Fgp[0] = (Fg[0]-Fgn[0]);
        Fgp[1] = (Fg[1]-Fgn[1]);
        Fgp[2] = (Fg[2]-Fgn[2]);

        //magnitude of Fgp
        double magFgp = magCalc(Fgp);

        //Fn
        double[] Fn = new double[3];
        Fn[0] = (Fgn[0]*-1);
        Fn[1] = (Fgn[1]*-1);
        Fn[2] = (Fgn[2]*-1);

        //magnitude of Fn
        double magFn = magCalc(Fn);

        //print outs
        System.out.println(block);
        System.out.println("Normal Vector: "+Arrays.toString(nrml));
        System.out.println("Force of Gravity: "+Arrays.toString(grav));
        System.out.println("Coefficient of static friction: " + statFric);
        System.out.println("Coefficient of kinetic friction: " + kineFric);
        System.out.println("Magnitude of Normal Vector: " + magNrml);
        System.out.println("Direction of Normal Vector: " + Arrays.toString(directNrml));
        System.out.println("Fg: " + Arrays.toString(Fg));
        System.out.println("Fgn: " + Arrays.toString(Fgn));
        System.out.println("Fgp: " + Arrays.toString(Fgp));
        System.out.println("Magnitude of Fgp: " + magFgp);
        System.out.println("Fn: " + Arrays.toString(Fn));
        System.out.println("Magnitude of Fn: " + magFn);
    }

    public static double magCalc(double[] vector) {
        double result;
        result = Math.sqrt((Math.pow(vector[0], 2) + Math.pow(vector[1], 2) + Math.pow(vector[2], 2)));

        return result;
    }

    public static double dotProduct(double[] v1, double[] v2) {
        double result;

        result = (v1[0] * v2[0]) + (v1[1] * v2[1]) + (v1[2] * v2[2]);

        return result;
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