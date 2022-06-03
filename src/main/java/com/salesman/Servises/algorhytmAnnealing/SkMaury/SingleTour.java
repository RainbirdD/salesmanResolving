package com.salesman.Servises.algorhytmAnnealing.SkMaury;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SingleTour {

    private List<City> tour = new ArrayList<>();
    private double distance = 0;

    public SingleTour() {
        for(int i = 0; i < Repository.getNumbderofCities(); ++i){
            tour.add(null);
        }
    }

    public SingleTour(List<City> tour) {
        List<City> currentTour = new ArrayList<>();

        for(int i = 0; i < tour.size(); ++i){
            currentTour.add(null);
        }
        for(int i = 0; i < tour.size(); ++i){
            currentTour.set(i, tour.get(i));
        }
        this.tour = currentTour;
    }

    public List<City> getTour() {
        return this.tour;
    }

    public void setCity(int cityIndex, City city) {
        this.tour.set(cityIndex, city);
        this.distance = 0;
    }

    public void generateIndividual(){
        for(int cityIndex = 0; cityIndex < Repository.getNumbderofCities(); ++cityIndex){
            setCity(cityIndex, Repository.getCity(cityIndex));
        }
        Collections.shuffle(tour);
    }

    public City getCity(int tourPosition){
        return tour.get(tourPosition);
    }

    public int getTourSize(){
        return this.tour.size();
    }

//    @Override
//    public String toString() {
//        String s = "";
//        for(int i = 0; i < getTourSize(); ++i){
//            s += getCity(i) + " -> ";
//        }
//        return s;
//    }

    @Override
    public String toString() {
        String s = "";
        for(int i = 0; i < getTourSize(); ++i){
            s += getCity(i);
        }
        collectCoordinates();
        return s;
    }

    public List<List> collectCoordinates(){
        List<List> values = new ArrayList<>();

        for(int i = 0; i < getTourSize(); ++i){
            values.add(List.of(getCity(i).getX(), getCity(i).getY()));
        }
        return values;
    }


    public double getDistance(){
        if (distance == 0){
            int tourDistance = 0;
            for(int cityIndex = 0; cityIndex < getTourSize(); ++cityIndex){
                City fromCity = getCity(cityIndex);
                City destinationCity;
                if(cityIndex + 1 < getTourSize()){
                    destinationCity = getCity(cityIndex + 1);
                }
                else
                    destinationCity = getCity(0);
                tourDistance += fromCity.distanceTo(destinationCity);
            }
            this.distance = tourDistance;
        }
        return this.distance;
    }
}
