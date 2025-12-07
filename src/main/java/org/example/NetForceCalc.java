package org.example;

public class NetForceCalc {
    //region Fields
    double mass;
    double[] nrml;
    double[] grav;
    double statFric;
    double kineFric;
    double magNrml;
    double magFgp;
    double magFn;
    double magFf;
    double[] directFf;
    double[] directNrml;
    double[] Fg;
    double[] Fgn;
    double[] Fgp;
    double[] Fn;
    double[] Ff;
    double[] Fnet;

    //endregion

    //region Getters and Setters
    public double[] getFnet() {
        return Fnet;
    }
    public void setFnet(double[] fnet) {
        Fnet = fnet;
    }
    public double getMagFf() {
        return magFf;
    }
    public void setMagFf(double magFf) {
        this.magFf = magFf;
    }
    public double[] getDirectFf() {
        return directFf;
    }
    public void setDirectFf(double[] directFf) {
        this.directFf = directFf;
    }
    public double[] getFf() {
        return Ff;
    }
    public void setFf(double[] ff) {
        this.Ff = ff;
    }
    public double[] getDirectNrml() {
        return directNrml;
    }
    public void setDirectNrml(double[] directNrml) {
        this.directNrml = directNrml;
    }
    public double[] getFg() {
        return Fg;
    }
    public void setFg(double[] fg) {
        Fg = fg;
    }
    public double[] getFgn() {
        return Fgn;
    }
    public void setFgn(double[] fgn) {
        Fgn = fgn;
    }
    public double[] getFgp() {
        return Fgp;
    }
    public void setFgp(double[] fgp) {
        Fgp = fgp;
    }
    public double[] getFn() {
        return Fn;
    }
    public void setFn(double[] fn) {
        Fn = fn;
    }
    public double getMass() {
        return mass;
    }
    public void setMass(double mass) {
        this.mass = mass;
    }
    public double[] getNrml() {
        return nrml;
    }
    public void setNrml(double[] nrml) {
        this.nrml = nrml;
    }
    public double[] getGrav() {
        return grav;
    }
    public void setGrav(double[] grav) {
        this.grav = grav;
    }
    public double getStatFric() {
        return statFric;
    }
    public void setStatFric(double statFric) {
        this.statFric = statFric;
    }
    public double getKineFric() {
        return kineFric;
    }
    public void setKineFric(double kineFric) {
        this.kineFric = kineFric;
    }
    public double getMagNrml() {
        return magNrml;
    }
    public void setMagNrml(double magNrml) {
        this.magNrml = magNrml;
    }
    public double getMagFgp() {
        return magFgp;
    }
    public void setMagFgp(double magFgp) {
        this.magFgp = magFgp;
    }
    public double getMagFn() {
        return magFn;
    }
    public void setMagFn(double magFn) {
        this.magFn = magFn;
    }
    //endregion
    public NetForceCalc(double mass, double[] nrml,  double[] grav, double statFric, double kineFric) {
        this.mass = mass;
        this.nrml = nrml;
        this.grav = grav;
        this.statFric = statFric;

        //Normal vector direction
        directNrml = calcDirection(getNrml());
        //Fg
        Fg = calcFg(getMass(), getGrav());
        //Fgn
        Fgn = calcFgn(Fg, directNrml);
        //Fgp
        Fgp = subtractVec(Fg, Fgn);
        //Fn
        Fn = negVector(Fgn);
        Ff = negVector(Fgp);

        //Test for static friction
        if(isStatic(Fn, Fgp)){
            Fnet[0] = 0.0;
            Fnet[1] = 0.0;
            Fnet[2] = 0.0;
        }
        else {
            directFf = negVector(calcDirection(Fgp));
            magFf = (kineFric * magCalc(Fn));
            Ff[0] = (magFf * directFf[0]);
            Ff[1] = (magFf * directFf[1]);
            Ff[2] = (magFf * directFf[2]);

            Fnet = addVecs(Fgp, Ff);
        }
    }
    public double[] calcFg(double mass, double[] grav){
        double[] result = new double[3];
        result[0] = (mass * grav[0]);
        result[1] = (mass * grav[1]);
        result[2] = (mass * grav[2]);
        return result;
    }
    public double[] calcFgn(double[] Fg, double[] directNrml){
        double[] result = new double[3];
        double dot = dotProduct(Fg, directNrml);
        result[0] = (dot*directNrml[0]); //should be a negative for N[0] = -5
        result[1] = (dot*directNrml[1]);
        result[2] = (dot*directNrml[2]);
        return result;
    }
    public double[] subtractVec(double[] v1, double[] v2){
        double[] result = new double[3];
        result[0] = (v1[0]-v2[0]);
        result[1] = (v1[1]-v2[1]);
        result[2] = (v1[2]-v2[2]);
        return result;
    }
    public double[] calcDirection(double[] v){
        double[] result = new double[3];
        double vMag = magCalc(v);
        result[0] = (1/vMag)* v[0];
        result[1] = (1/vMag)* v[1];
        result[2] = (1/vMag)* v[2];
        return result;
    }
    public double[] negVector(double[] v){
        double[] result = new double[3];
        result[0] = (v[0]*-1);
        result[1] = (v[1]*-1);
        result[2] = (v[2]*-1);
        return result;
    }
    public double magCalc(double[] vector) {
        double result;
        result = Math.sqrt((Math.pow(vector[0], 2) + Math.pow(vector[1], 2) + Math.pow(vector[2], 2)));
        return result;
    }
    public double dotProduct(double[] v1, double[] v2) {
        double result;
        result = (v1[0] * v2[0]) + (v1[1] * v2[1]) + (v1[2] * v2[2]);
        return result;
    }
    public double[] addVecs(double[] v1, double[] v2){
        double[] result = new double[3];
        result[0] = v1[0] + v2[0];
        result[1] = v1[1] + v2[1];
        result[2] = v1[2] + v2[2];
        return result;
    }
    public boolean isStatic(double[] Fn, double[] Fgp){
        if((getStatFric() * magCalc(Fn)) >= magCalc(Fgp)) {
            return true;
        }
        return false;
    }
}


