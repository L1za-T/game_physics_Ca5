package org.example;

import java.util.Arrays;
import java.util.Vector;
public class Block {

    double mass;
    double[] pos;
    double[] vel;
    public Block(double mass, double[] pos, double[] vel) {
        this.mass = mass;
        this.pos = pos;
        this.vel = vel;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double[] getPos() {
        return pos;
    }

    public void setPos(double[] pos) {
        this.pos = pos;
    }

    public double[] getVel() {
        return vel;
    }

    public void setVel(double[] vel) {
        this.vel = vel;
    }

    @Override
    public String toString() {
        return "Block{" +
                "mass=" + mass +
                ", pos=" + Arrays.toString(pos) +
                ", vel=" + Arrays.toString(vel) +
                '}';
    }
}
