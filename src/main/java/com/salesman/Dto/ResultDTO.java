package com.salesman.Dto;

import com.salesman.Servises.algorhytm.SalesmanGenome;
import lombok.Data;

import java.util.List;

@Data
public class ResultDTO {
    SalesmanGenome salesmanGenome;
    List<SalesmanGenome> generation;
}
