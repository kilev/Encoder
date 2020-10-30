package com.kil;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Multiplicity {
    private MultiplicityName name;
    private List<Slice> slices;
}
