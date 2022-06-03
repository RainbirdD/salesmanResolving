package com.salesman.Dto;

import lombok.Data;

import java.util.List;

@Data
public class AnnealingDTO {
    Integer pos;
    List<Double> generation;
    List<List> coordinates;
}
