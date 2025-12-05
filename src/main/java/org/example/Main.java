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
        double magNrml = Math.sqrt((Math.pow(nrml[0],2))+(Math.pow(nrml[1],2))+(Math.pow(nrml[2],2)));

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
        Fgn[0] = ((Fg[0] * directNrml[0])*directNrml[0]);
        Fgn[1] = ((Fg[1] * directNrml[1])*directNrml[1]);
        Fgn[2] = ((Fg[2] * directNrml[2])*directNrml[2]);

        //print outs
        System.out.println(block);
        System.out.println(Arrays.toString(nrml));
        System.out.println(Arrays.toString(grav));
        System.out.println(statFric);
        System.out.println(kineFric);
        System.out.println(magNrml);
        System.out.println(Arrays.toString(directNrml));
        System.out.println(Arrays.toString(Fg));
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