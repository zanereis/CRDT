package xyz.zanereis.crdt;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface SimulationRepository extends Repository<Simulation,Long> {
    Simulation save(Simulation simulation);
    List<Simulation> findAll();
    Optional<Simulation> findById(Long id);
}
