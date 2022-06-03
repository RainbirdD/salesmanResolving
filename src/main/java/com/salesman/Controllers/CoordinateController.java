package com.salesman.Controllers;

import com.salesman.Dto.AnnealingDTO;
import com.salesman.Dto.AntDTO;
import com.salesman.Dto.GenomeLoop;
import com.salesman.Dto.ResultDTO;
import com.salesman.Servises.aco.ACORunner;
import com.salesman.Servises.algorhytm.SalesmanGenome;
import com.salesman.Servises.algorhytm.SelectionType;
import com.salesman.Servises.algorhytm.UberSalesmensch;
import com.salesman.Servises.algorhytmAnnealing.SkMaury.City;
import com.salesman.Servises.algorhytmAnnealing.SkMaury.Repository;
import com.salesman.Servises.algorhytmAnnealing.SkMaury.SimulatedAnnealing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class CoordinateController {
    @Autowired
    private SalesmanGenome salesmanGenome;
    private SelectionType selectionType;
    private UberSalesmensch uberSalesmensch;


    @Autowired
    private ACORunner acoRunner;



    @GetMapping
    public String poihali(Model model) throws IOException {


        List<Long> time = new ArrayList<>();


        List<AntDTO> data = new ArrayList<>();



        for (int i = 0; i < 10; i++) {
            StopWatch watch = new StopWatch();
            watch.start();

            var antDTO = acoRunner.runAlgorithm();

            data.add(antDTO);

            System.out.println(antDTO.getPos());
            watch.stop();
            time.add(watch.getTotalTimeMillis());
        }

        System.out.println(data.size());


        model.addAttribute("tournament", data);
        model.addAttribute("rouletteTime", time);
        return "home";
    }
}
