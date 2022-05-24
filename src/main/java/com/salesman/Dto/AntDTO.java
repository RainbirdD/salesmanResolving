package com.salesman.Dto;

import com.salesman.Servises.algorhytm.SalesmanGenome;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class AntDTO {
    private double value;
    List<Double> allRolls;
}
