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


            roulette.add(new GenomeLoop(res.getSalesmanGenome().getFitness(), generationRes, key, value));
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

            tournament.add(new GenomeLoop(res.getSalesmanGenome().getFitness(), generationRes, key, value));
            watch.stop();
            tourTime.add(watch.getTotalTimeMillis());
        }

        //Looks like
//        Path: 0 3 2 1 4 0
//        Length: 199
//        Starting city = 0
//        Fitness = 199
//        Genome  = [3, 2, 1, 4]
//TODO findout how to present

        model.addAttribute("roulette", roulette);
        model.addAttribute("tournament", tournament);
        model.addAttribute("rouletteTime", rouletteTime);
        model.addAttribute("tourTime", tourTime);
        return "home";
    }

}
