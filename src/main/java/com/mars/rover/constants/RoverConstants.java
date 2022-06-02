package com.mars.rover.constants;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class RoverConstants {
    public static final Set<String> validCommands = Stream.of("N", "S", "E", "W", "F", "B", "R", "L")
            .collect(Collectors.toCollection(HashSet::new));
}
