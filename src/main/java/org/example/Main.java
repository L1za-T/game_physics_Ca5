package org.example;
import java.util.Vector;


public class Main {
    public static void main(String[] args) {

        //Block parameters
        Block block = new Block(0.3, new double[] {1,2,3}, new double [] {0,0,0});

        //Normal Vector
        Vector<Double> nrml = new Vector<>();
        nrml.add(-5.0);
        nrml.add(4.0);
        nrml.add(5.0);

        //Gravity Vector
        Vector<Double> grav = new Vector<>();
        grav.add(0.0);
        grav.add(0.0);
        grav.add(-9.81);

        double statFric = 0.8;
        double kineFric = 0.3;

        System.out.println(block);
        System.out.println(nrml);
        System.out.println(grav);
        System.out.println(statFric);
        System.out.println(kineFric);

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