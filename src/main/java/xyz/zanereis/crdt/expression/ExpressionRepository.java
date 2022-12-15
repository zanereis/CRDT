package xyz.zanereis.crdt.expression;

import org.springframework.data.repository.Repository;
import xyz.zanereis.crdt.Simulation;

import java.util.List;

public interface ExpressionRepository extends Repository<Expression,Long> {
    Expression save(Expression expression);
//    List<Expression> findAll(Expression);
    List<Expression> findBySimulation(Simulation id);
    List<Expression> findBySimulationAndOperands(Simulation simulation, String operand);
}
