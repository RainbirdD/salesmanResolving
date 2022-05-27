package com.salesman.Servises.algorhytmAnnealing.SkMaury;

public class City {

    private double x;
    private double y;

//    public City() {
//        this.x = (double) (Math.random() * 100);
//        this.y = (double) (Math.random() * 100);
//    }

    public City(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return this.x + " - " + this.y;
    }

    public double distanceTo(City otherCity){
        double xDistance = Math.abs(getX() - otherCity.getX());
        double yDistance = Math.abs(getY() - otherCity.getY());

        return Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
    }
}
