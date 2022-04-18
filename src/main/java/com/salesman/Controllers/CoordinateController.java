package com.salesman.Controllers;

import com.salesman.Dto.GenomeLoop;
import com.salesman.Dto.ResultDTO;
import com.salesman.Entity.Coordinates;
import com.salesman.Repository.CoordinatesRepository;
import com.salesman.Servises.algorhytm.SalesmanGenome;
import com.salesman.Servises.algorhytm.SelectionType;
import com.salesman.Servises.algorhytm.UberSalesmensch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class CoordinateController {
    @Autowired
    private SalesmanGenome salesmanGenome;
    private SelectionType selectionType;
    private UberSalesmensch uberSalesmensch;

    @Autowired
    CoordinatesRepository coordinatesRepository;

    @GetMapping("/all")
    List<Coordinates> all() {
        return coordinatesRepository.findAll();
    }

    @GetMapping("/home1")
    public String showGr(Model model) {
        return "home";
    }

    @GetMapping("/home")
    public String showGraph(Model model) {

        System.out.println("Exec");
        //Towns
        int numberOfCities = 5;
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

        UberSalesmensch geneticAlgorithm = new UberSalesmensch(numberOfCities, SelectionType.ROULETTE, travelPrices, 0, 0);
        UberSalesmensch geneticAlgorithmwithTour = new UberSalesmensch(numberOfCities, SelectionType.TOURNAMENT, travelPrices, 0, 0);

        List<Integer> fitnessList = new ArrayList();
        List<Integer> fitnessListWithTour = new ArrayList();

        //Alg iterations
        for (int i = 0; i < 10; i++) {
            SalesmanGenome result = geneticAlgorithm.optimize();
            System.out.println(result.getFitness());
            fitnessList.add(result.getFitness());
        }
        for (int i = 0; i < 10; i++) {
            SalesmanGenome result = geneticAlgorithm.optimize();
            System.out.println(result.getFitness());
            fitnessListWithTour.add(result.getFitness());
        }

        SalesmanGenome result = geneticAlgorithm.optimize();

        ResultDTO resultAlls = geneticAlgorithm.optimizeAll();

        var resultAll2 = resultAlls.getGeneration();

        var resultAll = resultAll2.stream()
                        .map(SalesmanGenome::getFitness).collect(Collectors.toList());


        System.out.println(resultAll);
//        System.out.println("Starting city = " + result.getStartingCity());
//        System.out.println("Fitness = " + result.getFitness());
//        System.out.println("Genome  = " + result.getGenome());


        //Looks like
//        Path: 0 3 2 1 4 0
//        Length: 199
//        Starting city = 0
//        Fitness = 199
//        Genome  = [3, 2, 1, 4]
//TODO findout how to present

        GenomeLoop genomeLoop = new GenomeLoop();
        genomeLoop.setGeneration(fitnessList);
        genomeLoop.setPos(228);

        GenomeLoop genomeLoop2 = new GenomeLoop();
        genomeLoop.setGeneration(fitnessListWithTour);
        genomeLoop.setPos(322);

        GenomeLoop genomeLoop3 = new GenomeLoop();
        genomeLoop.setGeneration(fitnessList);
        genomeLoop.setPos(342);

        GenomeLoop genomeLoop4 = new GenomeLoop();
        genomeLoop.setGeneration(fitnessListWithTour);
        genomeLoop.setPos(160);

        GenomeLoop genomeLoop5 = new GenomeLoop();
        genomeLoop.setGeneration(fitnessListWithTour);
        genomeLoop.setPos(322);

        GenomeLoop genomeLoop6 = new GenomeLoop();
        genomeLoop.setGeneration(fitnessListWithTour);
        genomeLoop.setPos(331);

        GenomeLoop genomeLoop7 = new GenomeLoop();
        genomeLoop.setGeneration(fitnessListWithTour);
        genomeLoop.setPos(157);

        GenomeLoop genomeLoop8 = new GenomeLoop();
        genomeLoop.setGeneration(fitnessListWithTour);
        genomeLoop.setPos(164);

        GenomeLoop genomeLoop9 = new GenomeLoop();
        genomeLoop.setGeneration(fitnessListWithTour);
        genomeLoop.setPos(322);

        GenomeLoop genomeLoop10 = new GenomeLoop();
        genomeLoop.setGeneration(fitnessListWithTour);
        genomeLoop.setPos(142);


        List<GenomeLoop> genomeLoops= new ArrayList<>();

        genomeLoops.add(genomeLoop);
        genomeLoops.add(genomeLoop2);
        genomeLoops.add(genomeLoop3);
        genomeLoops.add(genomeLoop4);
        genomeLoops.add(genomeLoop5);
        genomeLoops.add(genomeLoop6);
        genomeLoops.add(genomeLoop7);
        genomeLoops.add(genomeLoop8);
        genomeLoops.add(genomeLoop9);
        genomeLoops.add(genomeLoop10);


        model.addAttribute("resultAll", resultAll);
        model.addAttribute("fitnessList", fitnessList);
        model.addAttribute("genomeLoops", genomeLoops);
        model.addAttribute("genomeLoop", genomeLoop);
        return "home";
    }

}
