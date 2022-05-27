package com.salesman.Servises.algorhytmAnnealing.SkMaury;

public class Main {
    public static void main(String args[]){
        // [4.90, 53.38], // Amsterdam
        // [-118.24, 34.05], // Los Angeles
        // [-75.69, 45.38], //Ottawa
        // [-122.33, 47.36], //Washington
        // [-87.62, 41.88], //Chicago
        // [-92.32, 30.39], //Louisiana
        // [-99.13, 19.43], //Mexico
        // [-80.19, 25.76], //Miami
        // [-82.36, 23.11] // Cuba
        // [-75.00, 43.00] // New York
        // [-66.91, 10.50] // Caracas
        // [-72.33, 18.53] // Haiti
        // [-71.25, 46.82] // Quebec
        // [-85.00, 50.00] // Ontario
        // [-127.64, 53.72] // British colombia
            City city1 = new City(4.90, 53.38);
            City city2 = new City(-118.24, 34.05);
            City city3 = new City(-75.69, 45.38);
            City city4 = new City(-122.33, 47.36);
            City city5 = new City(-87.62, 41.88);
            City city6 = new City(-92.32, 30.39);
            City city7 = new City(-99.13, 19.43);
            City city8 = new City(-80.19, 25.76);
            City city9 = new City(-82.36, 23.11);
            City city10 = new City(-75.00, 43.00);
            City city11 = new City(-66.91, 10.50);
            City city12 = new City(-72.33, 18.53);
            City city13 = new City(-71.25, 46.82);
            City city14 = new City(-85.00, 50.00);
            City city15 = new City(-127.64, 53.72);

            Repository.addCity(city1);
            Repository.addCity(city2);
            Repository.addCity(city3);
            Repository.addCity(city4);
            Repository.addCity(city5);
            Repository.addCity(city6);
            Repository.addCity(city7);
            Repository.addCity(city8);
            Repository.addCity(city9);
            Repository.addCity(city10);
            Repository.addCity(city11);
            Repository.addCity(city12);
            Repository.addCity(city13);
            Repository.addCity(city14);
            Repository.addCity(city15);


        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing();
//        simulatedAnnealing.simulation();
        var kekw = simulatedAnnealing.govnoXXX();
            System.out.println(kekw.getPos());
            System.out.println(kekw.getGeneration());


        System.out.println("Final approximate solution distance is = " + simulatedAnnealing.getBest().getDistance());
        System.out.println("Tour : " + simulatedAnnealing.getBest());
    }
}
