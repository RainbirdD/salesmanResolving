//package com.salesman.Servises.algorhytm;
//
//import java.util.Random;
//
//public class Main {
//    public static void printTravelPrices(int[][] travelPrices, int numberOfCities){
//        for(int i = 0; i<numberOfCities; i++){
//            for(int j=0; j<numberOfCities; j++){
//                System.out.print(travelPrices[i][j]);
//                if(travelPrices[i][j]/10 == 0)
//                    System.out.print("  ");
//                else
//                    System.out.print(' ');
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }
//
//    public static void main(String[] args) {
//        int numberOfCities = 5;
//        int[][] travelPrices = new int[numberOfCities][numberOfCities];
//        for(int i = 0; i<numberOfCities; i++){
//            for(int j=0; j<=i; j++){
//                Random rand = new Random();
//                if(i==j)
//                    travelPrices[i][j] = 0;
//                else {
//                    travelPrices[i][j] = rand.nextInt(100);
//                    travelPrices[j][i] = travelPrices[i][j];
//                }
//            }
//        }
//
//        printTravelPrices(travelPrices,numberOfCities);
//
//        UberSalesmensch geneticAlgorithm = new UberSalesmensch(numberOfCities, SelectionType.ROULETTE, travelPrices, 0, 0);
//        SalesmanGenome result = geneticAlgorithm.optimize();
//        System.out.println(result);
//        System.out.println("Starting city = " + result.getStartingCity());
//        System.out.println("Fitness = " + result.getFitness());
//        System.out.println("Genome  = " + result.getGenome());
//        //Looks like
////        Path: 0 3 2 1 4 0
////        Length: 199
////        Starting city = 0
////        Fitness = 199
////        Genome  = [3, 2, 1, 4]
////TODO findout how to present
//    }
//}
//
