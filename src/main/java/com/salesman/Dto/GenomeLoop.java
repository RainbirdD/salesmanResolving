package com.salesman.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenomeLoop {
    Integer pos;
    List<Integer> generation;
    List<List> coordinates;

}
