package com.salesman.Servises.algorhytm;

import com.salesman.Dto.ResultDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Data
@RequiredArgsConstructor
public class UberSalesmensch {
    private int generationSize;
    private int genomeSize;
    private int numberOfCities;
    private int reproductionSize;
    private int maxIterations;
    private float mutationRate;
    private int tournamentSize;
    private SelectionType selectionType;
    private int[][] travelPrices;
    private int startingCity;
    private int targetFitness;


    public UberSalesmensch(int numberOfCities, SelectionType selectionType, int[][] travelPrices, int startingCity,
                           int targetFitness, int generationSize, int reproductionSize, int maxIterations, float mutationRate, int tournamentSize) {
        this.numberOfCities = numberOfCities;
        this.genomeSize = numberOfCities - 1;
        this.selectionType = selectionType;
        this.travelPrices = travelPrices;
        this.startingCity = startingCity;
        this.targetFitness = targetFitness;
        this.generationSize = generationSize;
        this.reproductionSize = reproductionSize;
        this.maxIterations = maxIterations;
        this.mutationRate = mutationRate;
        this.tournamentSize = tournamentSize;


//        generationSize = 5000;
//        reproductionSize = 250;
//        maxIterations = 100;
//        mutationRate = 0.1f;
//        tournamentSize = 80;
    }

    public List<SalesmanGenome> initialPopulation() {
        List<SalesmanGenome> population = new ArrayList<>();
        for (int i = 0; i < generationSize; i++) {
            population.add(new SalesmanGenome(numberOfCities, travelPrices, startingCity));
        }
        return population;
    }

    public List<SalesmanGenome> selection(List<SalesmanGenome> population) throws Exception {
        List<SalesmanGenome> selected = new ArrayList<>();
        SalesmanGenome winner;
        for (int i = 0; i < reproductionSize; i++) {
            if (selectionType == SelectionType.ROULETTE) {
                selected.add(rouletteSelection(population));
            } else if (selectionType == SelectionType.TOURNAMENT) {
                selected.add(tournamentSelection(population));
            }
        }
        return selected;
    }

    public SalesmanGenome rouletteSelection(List<SalesmanGenome> population) throws Exception {
        var n = population.stream().sorted().collect(Collectors.toList());

        var grr = n.subList(0, (int) ((int) n.size()*0.02));

        return grr.stream().findAny()
                .orElseThrow(() -> new Exception("Out of bounds"));

    }

    public static <E> List<E> pickNRandomElements(List<E> list, int n) {
        Random r = new Random();
        int length = list.size();

        if (length < n) return null;

        for (int i = length - 1; i >= length - n; --i) {
            Collections.swap(list, i, r.nextInt(i + 1));
        }
        return list.subList(length - n, length);
    }

    public SalesmanGenome tournamentSelection(List<SalesmanGenome> population) {
        List<SalesmanGenome> selected = pickNRandomElements(population, tournamentSize);
        return Collections.min(selected);
    }

    public SalesmanGenome mutate(SalesmanGenome salesman) {
        Random random = new Random();
        float mutate = random.nextFloat();
        if (mutate < mutationRate) {
            List<Integer> genome = salesman.getGenome();
            Collections.swap(genome, random.nextInt(genomeSize), random.nextInt(genomeSize));
            return new SalesmanGenome(genome, numberOfCities, travelPrices, startingCity);
        }
        return salesman;
    }

    public List<SalesmanGenome> createGeneration(List<SalesmanGenome> population) {
        List<SalesmanGenome> generation = new ArrayList<>();
        int currentGenerationSize = 0;
        while (currentGenerationSize < generationSize) {
            List<SalesmanGenome> parents = pickNRandomElements(population, 2);
            List<SalesmanGenome> children = crossover(parents);
            children.set(0, mutate(children.get(0)));
            children.set(1, mutate(children.get(1)));
            generation.addAll(children);
            currentGenerationSize += 2;
        }
        return generation;
    }

    public List<SalesmanGenome> crossover(List<SalesmanGenome> parents) {
        Random random = new Random();
        int breakpoint = random.nextInt(genomeSize);
        List<SalesmanGenome> children = new ArrayList<>();

        List<Integer> parent1Genome = new ArrayList<>(parents.get(0).getGenome());
        List<Integer> parent2Genome = new ArrayList<>(parents.get(1).getGenome());

        for (int i = 0; i < breakpoint; i++) {
            int newVal;
            newVal = parent2Genome.get(i);
            Collections.swap(parent1Genome, parent1Genome.indexOf(newVal), i);
        }
        children.add(new SalesmanGenome(parent1Genome, numberOfCities, travelPrices, startingCity));
        parent1Genome = parents.get(0).getGenome(); // reseting the edited parent

        for (int i = breakpoint; i < genomeSize; i++) {
            int newVal = parent1Genome.get(i);
            Collections.swap(parent2Genome, parent2Genome.indexOf(newVal), i);
        }
        children.add(new SalesmanGenome(parent2Genome, numberOfCities, travelPrices, startingCity));

        return children;
    }

    public ResultDTO optimizeAll() throws Exception {
        List<SalesmanGenome> generation = new ArrayList<>();
        List<SalesmanGenome> population = initialPopulation();
        SalesmanGenome globalBestGenome = population.get(0);
        int key = 0;
        for (int i = 0; i < maxIterations; i++) {
            List<SalesmanGenome> selected = selection(population);
            population = createGeneration(selected);
            globalBestGenome = Collections.min(population);
            generation.add(globalBestGenome);
            key++;
            if (i >= 2 && generation.get(i).getFitness() != generation.get(i - 1).getFitness()) {
                key = 0;
            }

            if (key == 20) {
                break;
            }
        }
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setSalesmanGenome(Collections.min(generation));
        resultDTO.setGeneration(generation);
        return resultDTO;
    }


    public void printGeneration(List<SalesmanGenome> generation) {
        for (SalesmanGenome genome : generation) {
            System.out.println(genome);
        }
    }
}
