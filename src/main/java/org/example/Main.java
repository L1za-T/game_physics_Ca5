package org.example;
import java.util.Arrays;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {

        //region Initial Conditions
        double target = 5;
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

        double[] acceleration = new double[3];
        acceleration[0] = ((1/block.getMass()) * calc.getFnet()[0]);
        acceleration[1] = ((1/block.getMass()) * calc.getFnet()[1]);
        acceleration[2] = ((1/block.getMass()) * calc.getFnet()[2]);

        //region prints for debug
//        //print outs
//        System.out.println("Block Details: ");
//        System.out.println(block);
//        System.out.println("Starting Conditions: ");
//        System.out.println("Normal Vector: "+Arrays.toString(nrml));
//        System.out.println("Force of Gravity: "+Arrays.toString(grav));
//        System.out.println("Coefficient of static friction: " + statFric);
//        System.out.println("Coefficient of kinetic friction: " + kineFric);
//
//        // Force Calculations
//        System.out.println("Force Calculations: ");
//        System.out.println("Magnitude of Normal Vector: " + calc.getMagNrml());
//        System.out.println("Direction of Normal Vector: " + Arrays.toString(calc.getDirectNrml()));
//        System.out.println("Fg: " + Arrays.toString(calc.getFg()));
//        System.out.println("Fgn: " + Arrays.toString(calc.getFgn()));
//        System.out.println("Fgp: " + Arrays.toString(calc.getFgp()));
//        System.out.println("Magnitude of Fgp: " + calc.getMagFgp());
//        System.out.println("Fn: " + Arrays.toString(calc.getFn()));
//        System.out.println("Magnitude of Fn: " + calc.getMagFn());
//        System.out.println("Net force on block " + Arrays.toString(calc.getFnet()));
//        System.out.println("Acceleration: " + Arrays.toString(acceleration) + "m/s^2");
        //endregion

        LinkedList<Double> t = new LinkedList<>();
        LinkedList<double[]> posTracker = new LinkedList<>();
        LinkedList<double[]> velTracker = new LinkedList<>();
        LinkedList<double[]> accTracker = new LinkedList<>();

        posTracker.add(block.getPos());
        velTracker.add(block.getVel());
        accTracker.add(acceleration);


        double time;
        int step = 1;
        double h = (target - startTime) / timeSteps;

        if(calc.isStatic(calc.getFn(), calc.getFgp())){
            System.out.println("Static friction is enough to keep object static.");
            System.out.println("Fnet = " + Arrays.toString(calc.getFnet()));

        }
        else{
            for(double i = startTime; i < target; i= i+h) {

                //tn
                time = tn(i, h);
                t.add(time);
                System.out.println(time);
                System.out.println(h);
                //pn
                posTracker.add(nextCalc(posTracker.get(step - 1), velTracker.get(step - 1), h));
                System.out.println(Arrays.toString(posTracker.get(step)));
                //vn
                velTracker.add(nextCalc(velTracker.get(step - 1), accTracker.get(step - 1), h));
                System.out.println(Arrays.toString(velTracker.get(step)));
                //an
                accTracker.add(calc.squareVecs(nextCalc(accTracker.get(step - 1), accTracker.get(step - 1), h),2));
                System.out.println(Arrays.toString(accTracker.get(step)));

                step++;
            }

        }

//        for(int i = 0; i < target; i++){
//            System.out.println(i);
//            System.out.println("Position at time: "+t.get(i) + " is: "+Arrays.toString(posTracker.get(i)));
//            System.out.println("Velocity at time: "+t.get(i)+" is: "+Arrays.toString(velTracker.get(i)));
//            System.out.println("Acceleration at time: "+t.get(i)+" is: "+Arrays.toString(accTracker.get(i)));
//        }

    }

    static double tn(double t, double h){
        return t + h;
    }

    static double[] nextCalc(double[] v1, double[] v2, double h){
        v1[0] = (v2[0]*h);
        v1[1] = (v2[1]*h);
        v1[2] = (v2[2]*h);

        return  v1;
    }
    static double round(double bigNum){
        String s;
        double temp = bigNum;
        s = String.format("%.5g%n", temp);
        bigNum = Double.parseDouble(s);
        return bigNum;
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