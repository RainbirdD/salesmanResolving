package com.salesman.Controllers;

import com.salesman.Dto.GenomeLoop;
import com.salesman.Dto.ResultDTO;
import com.salesman.Servises.algorhytm.SalesmanGenome;
import com.salesman.Servises.algorhytm.SelectionType;
import com.salesman.Servises.algorhytm.UberSalesmensch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class CoordinateController {
    @Autowired
    private SalesmanGenome salesmanGenome;
    private SelectionType selectionType;
    private UberSalesmensch uberSalesmensch;

//    @Autowired
//    CoordinatesRepository coordinatesRepository;
//
//    @GetMapping("/all")
//    List<Coordinates> all() {
//        return coordinatesRepository.findAll();
//    }


    @GetMapping("/home")
    public String showGraph(Model model) {

        System.out.println("Exec");
        //Towns
        int numberOfCities = 15;
        int[][] travelPrices = new int[numberOfCities][numberOfCities];
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j <= i; j++) {
                Random rand = new Random();
                if (i == j)
                    travelPrices[i][j] = 0;
                else {
                    travelPrices[i][j] = rand.nextInt(100);
                    travelPrices[j][i] = travelPrices[i][j];
                }
            }
        }

        UberSalesmensch geneticAlgorithmWithRoulette = new UberSalesmensch(numberOfCities, SelectionType.ROULETTE, travelPrices, 0, 0);
        UberSalesmensch geneticAlgorithmWithTour = new UberSalesmensch(numberOfCities, SelectionType.TOURNAMENT, travelPrices, 0, 0);

        List<GenomeLoop> roulette = new ArrayList<>();
        List<GenomeLoop> tournament = new ArrayList<>();

        List<Long> rouletteTime = new ArrayList<>();
        List<Long> tourTime = new ArrayList<>();


        //Alg iterations
        for (int i = 0; i < 10; i++) {
            StopWatch watch = new StopWatch();
            watch.start();
            ResultDTO res = geneticAlgorithmWithRoulette.optimizeAll();
            var generationRes = res.getGeneration().stream()
                    .map(SalesmanGenome::getFitness)
                    .collect(Collectors.toList());

            var coordinates = numsToCoordinates(res.getSalesmanGenome().getGenome());

            Map<Integer, Long> frequency =
                    generationRes.stream().collect(Collectors.groupingBy(
                            Function.identity(), Collectors.counting()));


            List<Integer> key = frequency.entrySet()
                    .stream()
                    .sorted(Comparator.comparing(Map.Entry<Integer, Long>::getValue).reversed())
                    .map(Map.Entry<Integer, Long>::getKey)
                    .collect(Collectors.toList());

            List<Long> value = frequency.entrySet()
                    .stream()
                    .sorted(Comparator.comparing(Map.Entry<Integer, Long>::getValue).reversed())
                    .map(Map.Entry<Integer, Long>::getValue)
                    .collect(Collectors.toList());



            roulette.add(new GenomeLoop(res.getSalesmanGenome().getFitness(), generationRes, key, value, coordinates));
            watch.stop();
            rouletteTime.add(watch.getTotalTimeMillis());
        }


        for (int i = 0; i < 10; i++) {
            StopWatch watch = new StopWatch();
            watch.start();
            ResultDTO res = geneticAlgorithmWithTour.optimizeAll();
            var generationRes = res.getGeneration().stream()
                    .map(SalesmanGenome::getFitness)
                    .collect(Collectors.toList());

            var coordinates = numsToCoordinates(res.getSalesmanGenome().getGenome());

            Map<Integer, Long> frequency =
                    generationRes.stream().collect(Collectors.groupingBy(
                            Function.identity(), Collectors.counting()));


            List<Integer> key = frequency.entrySet()
                    .stream()
                    .sorted(Comparator.comparing(Map.Entry<Integer, Long>::getValue).reversed())
                    .map(Map.Entry<Integer, Long>::getKey)
                    .collect(Collectors.toList());

            List<Long> value = frequency.entrySet()
                    .stream()
                    .sorted(Comparator.comparing(Map.Entry<Integer, Long>::getValue).reversed())
                    .map(Map.Entry<Integer, Long>::getValue)
                    .collect(Collectors.toList());

            tournament.add(new GenomeLoop(res.getSalesmanGenome().getFitness(), generationRes, key, value, coordinates));
            watch.stop();
            tourTime.add(watch.getTotalTimeMillis());
        }

        model.addAttribute("roulette", roulette);
        model.addAttribute("tournament", tournament);
        model.addAttribute("rouletteTime", rouletteTime);
        model.addAttribute("tourTime", tourTime);
        return "home";
    }

    private List<List> numsToCoordinates(List<Integer> genome) {
        List<List> coordinates = new ArrayList<>();
        coordinates.add(List.of(4.90, 53.38));

        genome.forEach(v -> {
            switch (v) {
                case 0:
                    coordinates.add(List.of(4.90, 53.38));
                    break;
                case 1:
                    coordinates.add(List.of(-118.24, 34.05));
                    break;
                case 2:
                    coordinates.add(List.of(-75.69, 45.38));
                    break;
                case 3:
                    coordinates.add(List.of(-122.33, 47.36));
                    break;
                case 4:
                    coordinates.add(List.of(-87.62, 41.88));
                    break;
                case 5:
                    coordinates.add(List.of(-92.32, 30.39));
                    break;
                case 6:
                    coordinates.add(List.of(-99.13, 19.43));
                    break;
                case 7:
                    coordinates.add(List.of(-80.19, 25.76));
                    break;
                case 8:
                    coordinates.add(List.of(-82.36, 23.11));
                    break;
                case 9:
                    coordinates.add(List.of(-75.00, 43.00));
                    break;
                case 10:
                    coordinates.add(List.of(-66.91, 10.50));
                    break;
                case 11:
                    coordinates.add(List.of(-72.33, 18.53));
                    break;
                case 12:
                    coordinates.add(List.of(-71.25, 46.82));
                    break;
                case 13:
                    coordinates.add(List.of(-85.00, 50.00));
                    break;
                case 14:
                    coordinates.add(List.of(-127.64, 53.72));
                    break;
            }
        });
        coordinates.add(List.of(4.90, 53.38));
        return coordinates;
    }
}
