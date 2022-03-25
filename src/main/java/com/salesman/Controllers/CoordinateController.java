package com.salesman.Controllers;

import com.salesman.Entity.Coordinates;
import com.salesman.Repository.CoordinatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Controller
public class CoordinateController {
    @Autowired
    CoordinatesRepository coordinatesRepository;

    @GetMapping("/all")
    List<Coordinates> all(){
        return coordinatesRepository.findAll();
    }

    @GetMapping("/home")
    public String showGraph(Model model){
        var scope = List.of(83, 56, 97);
        var coodA = List.of(2, 3, 6);
        var coodB = List.of(1, 5, 7);
        var time = List.of(1, 2, 3);
        model.addAttribute("scope", scope);
        model.addAttribute("time", 2);
        return "home";

    }

}
