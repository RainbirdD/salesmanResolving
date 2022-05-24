package com.salesman.Servises.antAlgorhytm;

import com.salesman.Dto.AntDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
AntAlgorithm
 * default
 * private double c = 1.0;             //количество трасс
 * private double alpha = 1;           //важность феромонов
 * private double beta = 5;            // приоритет расстояния
 * private double evaporation = 0.5;
 * private double Q = 500;             // оставленный на следе муравья
 * private double antFactor = 0.8;     //количество муравьев на узел
 * private double randomFactor = 0.01; // введение случайности
 * private int maxIterations = 1000;
 */
@Data
public class AntAlgorithm {
    public String s = "";
    private double c ;             //количество трасс
    private double alpha ;           //важность феромонов
    private double beta ;            //приоритет расстояния
    private double evaporation;
    private double Q;             //оставленный на следе муравья
    private double antFactor ;     //количество муравьев на узел
    private double randomFactor ; //введение случайности

    private int maxIterations ;

    private int numberOfCities ;
    private int numberOfAnts;
    private double[][] graph;
    private double[][] trails;
    private List<Ant> ants = new ArrayList<>();
    private Random random = new Random();
    private double[] probabilities;

    private int currentIndex;

    private int[] bestTourOrder;
    private double bestTourLength;

    public AntAlgorithm(double TR, double alf, double bet, double eva,
                                 double q, double aF, double rF, int maxiter, int noOfCity) {
        c = TR;
        alpha = alf;
        beta = bet;
        evaporation = eva;
        Q = q;
        antFactor = aF;
        randomFactor = rF;
        maxIterations = maxiter;

//        graph = generateRandomMatrix(noOfCity);
        graph = new double[][]{
                {0, 8938, 5628, 6189, 6604, 7773, 9045, 7442, 7637, 5860, 7850, 7482, 5260, 5775, 7311},
                {8938, 0, 3800, 1507, 2801, 2479, 2002, 3758, 4004, 3610, 5818, 4811, 4155, 3494, 2280},
                {5628, 3800, 0, 732, 1035, 2147, 3442, 2220, 2626, 331, 3970, 2952, 379, 351, 3315},
                {6189, 1507, 732, 0, 956, 1627, 2948, 1488, 1893, 447, 3315, 2256, 999, 563, 3945},
                {6604, 2801, 1035, 956, 0, 1281, 2483, 1913, 2334, 809, 4028, 2920, 1416, 703, 3045},
                {7773, 2479, 2147, 1627, 1282, 0, 1320, 1286, 1586, 1827, 3438, 2369, 2512, 1802, 3674},
                {9045, 2002, 3442, 2948, 2483, 1320, 0, 2227, 2311, 3131, 3972, 3097, 3814, 3091, 3947},
                {7442, 3758, 2220, 1488, 1913, 1286, 2227, 0, 425, 1912, 2198, 1097, 2470, 3703, 4819},
                {7637, 4004, 2626, 1893, 2334, 1586, 2311, 425, 0, 2143, 1852, 809, 2858, 2406, 5199},
                {5860, 3610, 331, 447, 809, 1827, 3131, 1912, 2143, 0, 3430, 2419, 714, 549, 4012},
                {7850, 5818, 3970, 3315, 4028, 3438, 3972, 2198, 1852, 3430, 0, 1114, 4057, 3876, 7012},
                {7482, 4811, 2952, 2256, 2920, 2369, 3097, 1097, 809, 2419, 1114, 0, 3094, 2816, 5899},
                {5260, 4155, 379, 999, 1416, 2512, 3814, 2470, 2858, 714, 4057, 3094, 0, 730, 3798},
                {5775, 3494, 351, 563, 703, 1802, 3091, 3703, 2406, 549, 3876, 2816, 730, 0, 3463},
                {7311, 2280, 3315, 3945, 3045, 3674, 3947, 4819, 5199, 4012, 7012, 5899, 3798, 3463, 0}

        };





        numberOfCities = noOfCity;
        numberOfAnts = (int) (numberOfCities * antFactor);

        trails = new double[numberOfCities][numberOfCities];
        probabilities = new double[numberOfCities];

        for (int i = 0; i < numberOfAnts; i++)
            ants.add(new Ant(numberOfCities));
    }




   //  Сгенерировать начальное решение

//    public double[][] generateRandomMatrix(int n) {
//        double[][] randomyMatrix = new double[n][n];
//
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                if (i == j)
//                    randomyMatrix[i][j] = 0;
//                else
//                    randomyMatrix[i][j] = Math.abs(random.nextInt(100) + 1);
//            }
//        }
//
//        s += ("\t");
//        for (int i = 0; i < n; i++)
//            s += (i + "\t");
//        s += "\n";
//
//        for (int i = 0; i < n; i++) {
//            s += (i + "\t");
//            for (int j = 0; j < n; j++)
//                s += (randomyMatrix[i][j] + "\t");
//            s += "\n";
//        }
//
//        int sum = 0;
//
//        for (int i = 0; i < n - 1; i++)
//            sum += randomyMatrix[i][i + 1];
//        sum += randomyMatrix[n - 1][0];
//        s += ("\nNaive solution 0-1-2-...-n-0 = " + sum + "\n");//Наивное решение 0-1-2-...-n-0 =
//        return randomyMatrix;
//
//    }


    //  Выполнить муравьиную оптимизацию


    public void startAntOptimization() {
        for (int i = 1; i <= 5; i++) {
            s += ("\nAttempt #" + i);
            solve();
            s += "\n";
        }
    }


     //Используйте этот метод для запуска основной логики


    public int[] solve() {
        setupAnts();
        clearTrails();
        for (int i = 0; i < maxIterations; i++) {
            moveAnts();
            updateTrails();
            updateBest();
        }

        System.out.println(s += ("\nЛучший города тура по убиванию: " + Arrays.toString(bestTourOrder)));
        System.out.println(s += ("\nЛучшая продолжительность тура: " + (bestTourLength - numberOfCities)));


        return bestTourOrder.clone();
    }


    public AntDTO solveXXX() {
        AntDTO antDTO = new AntDTO();

        List<Integer> kekw = new ArrayList<>();
        setupAnts();
        clearTrails();
        for (int i = 0; i < maxIterations; i++) {

            moveAnts();
            updateTrails();
            updateBest();
            kekw.add((int) bestTourLength);
        }

        antDTO.setValue(bestTourLength);
        antDTO.setAllRolls(kekw);

        System.out.println(antDTO.getValue());
        System.out.println(antDTO.getAllRolls());

        return antDTO;
    }


     // Подготовьте муравьев к симуляции


    private void setupAnts() {
        for (int i = 0; i < numberOfAnts; i++) {
            for (Ant ant : ants) {
                ant.clear();
                ant.visitToCity(-1, random.nextInt(numberOfCities));
            }
        }
        currentIndex = 0;
    }


     // На каждой итерации перемещайте муравьев

    private void moveAnts() {
        for (int i = currentIndex; i < numberOfCities - 1; i++) {
            for (Ant ant : ants) {
                ant.visitToCity(currentIndex, selectNextCity(ant));
            }
            currentIndex++;
        }
    }


     //Выберите следующий город для каждого муравья

    private int selectNextCity(Ant ant) {
        int t = random.nextInt(numberOfCities - currentIndex);
        if (random.nextDouble() < randomFactor) {
            int cityIndex = -999;
            for (int i = 0; i < numberOfCities; i++) {
                if (i == t && !ant.visited(i)) {
                    cityIndex = i;
                    break;
                }
            }
            if (cityIndex != -999)
                return cityIndex;
        }
        calculateProbabilities(ant);
        double r = random.nextDouble();
        double total = 0;
        for (int i = 0; i < numberOfCities; i++) {
            total += probabilities[i];
            if (total >= r)
                return i;
        }
        throw new RuntimeException("Других городов нет");
    }


     // Рассчитать вероятность выбора следующего городa

    public void calculateProbabilities(Ant ant) {
        int i = ant.trail[currentIndex];//текущий индекс
        double pheromone = 0.0;//феромон
        for (int l = 0; l < numberOfCities; l++)//количество городов

        {
            if (!ant.visited(l))//!муравей посетил
                pheromone += Math.pow(trails[i][l], alpha) * Math.pow(1.0 / graph[i][l], beta);
        }
        for (int j = 0; j < numberOfCities; j++) {
            if (ant.visited(j))
                probabilities[j] = 0.0;
            else {
                double numerator = Math.pow(trails[i][j], alpha) * Math.pow(1.0 / graph[i][j], beta);
                probabilities[j] = numerator / pheromone;
            }
        }
    }


    // Обновите следы, которые использовали муравьи

    private void updateTrails()//обновить тропы
    {
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++)
                trails[i][j] *= evaporation;//испарение
        }
        for (Ant a : ants) {
            double contribution = Q / a.trailLength(graph);//вклад
            for (int i = 0; i < numberOfCities - 1; i++)
                trails[a.trail[i]][a.trail[i + 1]] += contribution;
            trails[a.trail[numberOfCities - 1]][a.trail[0]] += contribution;
        }
    }


    // Обновите лучшее решение

    private void updateBest() {
        List<Double> kekw = new ArrayList<>();

        if (bestTourOrder == null) {
            bestTourOrder = ants.get(0).trail;
            bestTourLength = ants.get(0).trailLength(graph);
        }

        for (Ant a : ants) {
            if (a.trailLength(graph) < bestTourLength) {
                kekw.add(bestTourLength);
                System.out.println(bestTourLength);
                bestTourLength = a.trailLength(graph);
                bestTourOrder = a.trail.clone();
            }
        }
        System.out.println(kekw);
    }





    // Очистить следы после симуляции

    private void clearTrails() {
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++)
                trails[i][j] = c;
        }
    }

}


