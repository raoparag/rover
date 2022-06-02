package com.mars.rover.repository;

import com.mars.rover.entity.Rover;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RoverRepo {
    private Map<String, Rover> roverMap;

    @PostConstruct
    public void init() {
        roverMap = new ConcurrentHashMap<>();
    }

    public void add(Rover rover) {
        roverMap.put(rover.getId(), rover);
    }

    public Optional<Rover> get(String roverId) {
        return Optional.ofNullable(roverMap.get(roverId));
    }

    public List<Rover> getAll(){
        return new ArrayList<>(roverMap.values());
    }
}
