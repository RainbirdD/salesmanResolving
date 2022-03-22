package com.salesman.Controllers;

import com.salesman.Entity.Coordinates;
import com.salesman.Repository.CoordinatesRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CoordinateController {
    @Autowired
    CoordinatesRepository coordinatesRepository;

    @GetMapping("/all")
    List<Coordinates> all(){
        return coordinatesRepository.findAll();
    }
}
