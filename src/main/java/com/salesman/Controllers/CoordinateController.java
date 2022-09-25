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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class CoordinateController {
    @Autowired
    private SalesmanGenome salesmanGenome;
    private SelectionType selectionType;
    private UberSalesmensch uberSalesmensch;


    @GetMapping()
    public String showDefaultPage() {
        return "default";
    }


    @GetMapping("/home")
    public String showGraph(Model model, @RequestParam(name = "generationSize") Integer generationSize,
                            @RequestParam(name = "reproductionSize") Integer reproductionSize,
                            @RequestParam(name = "maxIterations") Integer maxIterations,
                            @RequestParam(name = "mutationRate") Float mutationRate,
                            @RequestParam(name = "tournamentSize") Integer tournamentSize) throws Exception {


        System.out.println("Exec");


        int numberOfCities = 15;
        int gistagrammValue = 8;

        int[][] travelPrices = new int[][]{
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

//        int[][] travelPrices = new int[][]{
//                {0, 8938, 5631, 7824, 6610, 7762, 9072, 7433, 7715, 5861, 7850, 7482, 4983, 5873, 7311},
//                {8938, 0, 3800, 1507, 2801, 2479, 2002, 3758, 4004, 3610, 5818, 4811, 4155, 3494, 2280},
//                {5628, 3800, 0, 3523, 1035, 2147, 3442, 2220, 2626, 268, 3970, 2952, 781, 788, 3592},
//                {7824, 1507, 3523, 0, 2788, 3164, 3278, 4378, 4746, 3641, 6590, 5491, 3571, 2768, 735},
//                {6610, 2801, 1035, 2788, 0, 1267, 2483, 1913, 2334, 1145, 4028, 2920, 1623, 853, 3045},
//                {7762, 2479, 2147, 3164, 1267, 0, 1330, 1286, 1586, 1827, 3438, 2369, 2848, 2142, 3674},
//                {9072, 2002, 3442, 3278, 2483, 1320, 0, 2227, 2311, 3298, 3972, 3097, 4113, 3296, 3947},
//                {7433, 3758, 2220, 4378, 1913, 1286, 2227, 0, 425, 1965, 2198, 1097, 2996, 2636, 4819},
//                {7715, 4004, 2626, 4746, 2334, 1586, 2311, 425, 0, 2346, 1852, 809, 3397, 3061, 5199},
//                {5861, 3610, 268, 3641, 1145, 1827, 3298, 1965, 2346, 0, 3707, 2684, 1034, 980, 3759},
//                {7850, 5818, 3970, 6590, 4028, 3438, 3972, 2198, 1852, 3707, 0, 1114, 4636, 4604, 7012},
//                {7482, 4811, 2952, 5491, 2920, 2369, 3097, 1097, 809, 2684, 1114, 0, 3669, 3470, 5899},
//                {4983, 4155, 781, 3571, 1623, 2848, 4113, 2996, 3397, 1034, 4636, 3669, 0, 730, 3470},
//                {5873, 3494, 788, 2768, 853, 2142, 3296, 2636, 3061, 980, 4604, 3470, 730, 0, 2805},
//                {7311, 2280, 3592, 735, 3045, 3674, 3947, 4819, 5199, 3759, 7012, 5899, 3470, 2805, 0}
//        };


        UberSalesmensch geneticAlgorithmWithRoulette = new UberSalesmensch(numberOfCities, SelectionType.ROULETTE, travelPrices, 0, 0, generationSize, reproductionSize, maxIterations, mutationRate, tournamentSize);
        UberSalesmensch geneticAlgorithmWithTour = new UberSalesmensch(numberOfCities, SelectionType.TOURNAMENT, travelPrices, 0, 0, generationSize, reproductionSize, maxIterations, mutationRate, tournamentSize);

        List<GenomeLoop> roulette = new ArrayList<>();
        List<GenomeLoop> tournament = new ArrayList<>();

        List<Long> rouletteTime = new ArrayList<>();
        List<Long> tourTime = new ArrayList<>();


        //Alg iterations
        for (int i = 0; i < 30; i++) {
            StopWatch watch = new StopWatch();
            watch.start();
            ResultDTO res = geneticAlgorithmWithRoulette.optimizeAll();
            var generationRes = res.getGeneration().stream()
                    .map(SalesmanGenome::getFitness)
                    .collect(Collectors.toList());

            var coordinates = numsToCoordinates(res.getSalesmanGenome().getGenome());

            roulette.add(new GenomeLoop(res.getSalesmanGenome().getFitness(), generationRes, coordinates));
            watch.stop();
            rouletteTime.add(watch.getTotalTimeMillis());
        }


        for (int i = 0; i < 30; i++) {
            StopWatch watch = new StopWatch();
            watch.start();
            ResultDTO res = geneticAlgorithmWithTour.optimizeAll();
            var generationRes = res.getGeneration().stream()
                    .map(SalesmanGenome::getFitness)
                    .collect(Collectors.toList());

            var coordinates = numsToCoordinates(res.getSalesmanGenome().getGenome());

            tournament.add(new GenomeLoop(res.getSalesmanGenome().getFitness(), generationRes, coordinates));
            watch.stop();
            tourTime.add(watch.getTotalTimeMillis());
        }

        List<Integer> tourGistogramm = tournament.stream()
                .map(GenomeLoop::getPos)
                .collect(Collectors.toList());

        List<Integer> rouletteGistogramm = roulette.stream()
                .map(GenomeLoop::getPos)
                .collect(Collectors.toList());



        List<Long> values = new ArrayList<>();
        List<Integer> distancesFrom = new ArrayList<>();
        List<Integer> distancesTo = new ArrayList<>();

        for (int i = 0; i < gistagrammValue; i++) {
            long min = Collections.min(tourGistogramm);
            int max = Collections.max(tourGistogramm);

            long delta = (max - min) / gistagrammValue;

            int finalI = i;

            if (i < gistagrammValue - 1) {
                long count = tourGistogramm.stream()
                        .filter(v -> v >= min + delta * finalI && v < min + delta * (finalI + 1))
                        .count();
                values.add(count);
            } else {
                long count = tourGistogramm.stream()
                        .filter(v -> v >= min + delta * finalI && v < min + delta * (finalI + 2))
                        .count();
                values.add(count);
            }


            if (i < gistagrammValue - 1) {
                distancesFrom.add((int) (min + delta * finalI));
                distancesTo.add((int) (min + delta * (finalI + 1)));
            } else {
                distancesFrom.add((int) (min + delta * finalI));
                distancesTo.add((int) (min + delta * (finalI + 2)));
            }
        }

        List<Long> rouletteValues = new ArrayList<>();
        List<Integer> rouletteDistancesFrom = new ArrayList<>();
        List<Integer> rouletteDistancesTo = new ArrayList<>();

        for (int i = 0; i < gistagrammValue; i++) {
            long min = Collections.min(rouletteGistogramm);
            int max = Collections.max(rouletteGistogramm);

            long delta = (max - min) / gistagrammValue;

            int finalI = i;

            if (i < gistagrammValue - 1) {
                long count = rouletteGistogramm.stream()
                        .filter(v -> v >= min + delta * finalI && v < min + delta * (finalI + 1))
                        .count();
                rouletteValues.add(count);
            } else {
                long count = rouletteGistogramm.stream()
                        .filter(v -> v >= min + delta * finalI && v < min + delta * (finalI + 2))
                        .count();
                rouletteValues.add(count);
            }


            if (i < gistagrammValue - 1) {
                rouletteDistancesFrom.add((int) (min + delta * finalI));
                rouletteDistancesTo.add((int) (min + delta * (finalI + 1)));
            } else {
                rouletteDistancesFrom.add((int) (min + delta * finalI));
                rouletteDistancesTo.add((int) (min + delta * (finalI + 2)));
            }
        }

        model.addAttribute("roulette", roulette);
        model.addAttribute("tournament", tournament);
        model.addAttribute("rouletteTime", rouletteTime);
        model.addAttribute("tourTime", tourTime);
        model.addAttribute("tournamentdistancesFrom", distancesFrom);
        model.addAttribute("tournamentdistancesTo", distancesTo);
        model.addAttribute("tournamentValue", values);
        model.addAttribute("rouletteDistancesFrom", rouletteDistancesFrom);
        model.addAttribute("rouletteDistancesTo", rouletteDistancesTo);
        model.addAttribute("rouletteValue", rouletteValues);
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
                    coordinates.add(List.of(-77.03, 38.89));
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
