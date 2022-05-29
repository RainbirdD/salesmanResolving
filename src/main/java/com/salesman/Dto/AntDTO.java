package com.salesman.Dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class AntDTO {
    double pos;
    List<Double> generation;
}
