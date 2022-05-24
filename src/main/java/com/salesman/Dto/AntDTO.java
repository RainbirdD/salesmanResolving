package com.salesman.Dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class AntDTO {
    private double value;
    List<Integer> allRolls;
}
