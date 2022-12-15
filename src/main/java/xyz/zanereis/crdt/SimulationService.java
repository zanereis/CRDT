package xyz.zanereis.crdt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SimulationService {
    private final SimulationRepository simulationRepository;
    @Autowired
    public SimulationService(SimulationRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
    }

    public Simulation save(Simulation simulation) {
        return simulationRepository.save(simulation);
    }
    public List<Simulation> findAll() {
        return simulationRepository.findAll();
    }
    public Optional<Simulation> findById(Long id) {
        return simulationRepository.findById(id);
    }
}
