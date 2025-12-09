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
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        PrintStream console = System.out;

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

        //region prints for debug
//        //print outs
//        System.out.println("Block Details: ");
//        System.out.println(block);
//        System.out.println("Starting Conditions: ");
//        System.out.println("Normal Vector: "+Arrays.toString(nrml));
//        System.out.println("Force of Gravity: "+Arrays.toString(grav));
//        System.out.println("Coefficient of static friction: " + statFric);
//        System.out.println("Coefficient of kinetic friction: " + kineFric);

        LinkedList<Double> t = new LinkedList<>();
        LinkedList<double[]> posTracker = new LinkedList<>();
        LinkedList<double[]> velTracker = new LinkedList<>();

        posTracker.add(block.getPos());
        velTracker.add(block.getVel());

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
                //pn
                posTracker.add(nextCalc(posTracker.get(step - 1), velTracker.get(step - 1), h));

                //vn
                velTracker.add(nextCalc(velTracker.get(step - 1), acceleration, h));

                step++;
            }

        }

        System.out.println("Start time: " + startTime);
        System.out.println("Target time: " + target);
        System.out.println("Number of steps: " + timeSteps);

        System.out.println("Step size: " + h);
        System.out.println("Acceleration is: "+Arrays.toString(acceleration));
        System.out.println("Position at time: 0.0 is: "+Arrays.toString(posTracker.getFirst()));
        System.out.println("Velocity at time: 0.0 is: "+Arrays.toString(velTracker.getFirst()));

        for(int i = 0; i < step-1; i++){
            System.out.println(i+1);
            System.out.println("Position at time: "+ round(t.get(i))+ " is: "+Arrays.toString(posTracker.get(i)));
            System.out.println("Velocity at time: "+round(t.get(i))+" is: "+Arrays.toString(velTracker.get(i)));
        }

        System.setOut(out);
        System.out.println("Test");
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
    public String convertToCsv(String[] data){
        return Stream.of(data).map(this::escapeSpecialCharacters).collect(Collectors.joining(","));
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