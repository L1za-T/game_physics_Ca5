package org.example;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //region Print to File
        PrintStream f = new PrintStream(new FileOutputStream("outputs/Results.txt"));
        PrintStream console = System.out;
        System.setOut(f);

        //endregion

        //region Initial Conditions
        double target = 10;
        double startTime = 0;
        double timeSteps = 20;

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

        LinkedList<Double> t = new LinkedList<>();
        LinkedList<double[]> posTracker = new LinkedList<>();
        LinkedList<double[]> velTracker = new LinkedList<>();

        posTracker.add(roundVec((block.getPos())));
        velTracker.add(roundVec(block.getVel()));

        double time;
        int step = 1;
        double h = (target - startTime) / timeSteps;

        if(calc.isStatic(calc.getFn(), calc.getFgp())){
            System.out.println("Static friction is enough to keep object static.");
            System.out.println("Fnet = " + Arrays.toString(calc.getFnet()) + " N");

        }
        else{
            for(double i = startTime; i < target; i= i+h) {

                //tn
                time = tn(i, h);
                t.add(time);
                //pn
                posTracker.add(roundVec(nextCalc(posTracker.get(step - 1), velTracker.get(step - 1), h)));

                //vn
                velTracker.add(roundVec(nextCalc(velTracker.get(step - 1), acceleration, h)));

                step++;
            }

        }
        System.out.println("Situation: " + "\n");
        System.out.println("Block mass: " + block.getMass() + "Kg.");
        System.out.println("Normal Vector : " + Arrays.toString(roundVec(nrml)));
        System.out.println("Coefficient of static friction: " + statFric);
        System.out.println("Coefficient of kinetic friction: " + kineFric);
        System.out.println("Gravity: " + Arrays.toString(roundVec((grav))) + "\n");

        System.out.println("Timings: " + "\n");
        System.out.println("Start time: " + startTime + " seconds.");
        System.out.println("Target time: " + target);
        System.out.println("Number of steps: " + timeSteps);
        System.out.println("Step size: " + h + "\n");

        System.out.println("Initial condition: "+ "\n");
        System.out.println("Position at time: 0.0 is: "+Arrays.toString(roundVec(posTracker.getFirst())) + " m.");
        System.out.println("Velocity at time: 0.0 is: "+Arrays.toString(roundVec(velTracker.getFirst())) + " m/s.");
        System.out.println("Acceleration is: "+Arrays.toString(roundVec(acceleration)) + "ms^2." + "\n");

        System.out.println("Summation of forces: " + "\n");
        System.out.println("Force due to gravity: " + Arrays.toString(roundVec(calc.getFg())) + " N.");
        System.out.println("Force due to in direction of Normal: " + Arrays.toString(roundVec(calc.getFgn())) + " N.");
        System.out.println("Force due to in direction of Normal: " + Arrays.toString(roundVec(calc.getFgn())) + " N.");
        System.out.println("Force of gravity in direction of Plane: " +  Arrays.toString(roundVec(calc.getFgp())) + " N.");
        System.out.println("Static Friction: " + (calc.getStatFric() * calc.magCalc(calc.getFn())) + " N.");
        System.out.println("Kinetic Friction: " + Arrays.toString(roundVec(calc.getFf())) + " N.");
        System.out.println("Net Force: " + Arrays.toString(roundVec(calc.getFnet())) + " N." + "\n");


        for(int i = 0; i < step-1; i++){
            System.out.println(i+1);
            System.out.println("Position at time: "+ round(t.get(i))+ " is: "+Arrays.toString(posTracker.get(i)) + " m.");
            System.out.println("Velocity at time: "+round(t.get(i))+" is: "+Arrays.toString(velTracker.get(i)) + " m/s.");
        }

        System.setOut(console);
    }

    static double tn(double t, double h){
        return t + h;
    }

    static double[] nextCalc(double[] v1, double[] v2, double h){
        double[] result = new double[3];

        result[0] = v1[0]+(v2[0]*h);
        result[1] = v1[1]+(v2[1]*h);
        result[2] = v1[2]+(v2[2]*h);

        return  result;
    }
    static double round(double bigNum){
        String s;
        double temp = bigNum;
        s = String.format("%.5g%n", temp);
        bigNum = Double.parseDouble(s);
        return bigNum;
    }
    static double[] roundVec(double[] bigVec){
        String s;
        for(int i = 0; i < bigVec.length; i++){
            double temp = bigVec[i];
            s = String.format("%.5g%n", temp);
            bigVec[i] = Double.parseDouble(s);
        }
        return bigVec;
    }

    public String escapeSpecialCharacters(String data){
        if (data== null){
            throw new IllegalArgumentException("Input data cannot be null");
        }
        String escapedData = data.replaceAll("\\R", " ");
        if (escapedData.contains(",")|| escapedData.contains("\"")|| escapedData.contains("'")){

            escapedData = escapedData.replace("\"","\"\"");
            escapedData = "\"" + escapedData + "\"";
        }

        return escapedData;
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