package xyz.zanereis.crdt.expression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.zanereis.crdt.CrdtType;
import xyz.zanereis.crdt.gset.GSetService;
import xyz.zanereis.crdt.Simulation;
import xyz.zanereis.crdt.SimulationRepository;
import xyz.zanereis.crdt.lwwset.LWWSetService;
import xyz.zanereis.crdt.twopset.TwoPSetService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ExpressionService {
    private final ExpressionRepository expressionRepository;
    private final SimulationRepository simulationRepository;
    private final GSetService gSetService;
    private final TwoPSetService twoPSetService;
    private final LWWSetService lwwSetService;
    @Autowired
    public ExpressionService(ExpressionRepository expressionRepository,
                             SimulationRepository simulationRepository,
                             GSetService gSetService,
                             TwoPSetService twoPSetService,
                             LWWSetService lwwSetService) {
        this.expressionRepository = expressionRepository;
        this.simulationRepository = simulationRepository;
        this.gSetService = gSetService;
        this.twoPSetService = twoPSetService;
        this.lwwSetService = lwwSetService;
    }
    public Expression save(Long simulationId, Expressionable expressionable) {
        Simulation simulation = simulationRepository.findById(simulationId)
                .orElseThrow(() -> new NoSuchElementException("Cannot find simulation by id: " + simulationId));
        Expression expression = expressionable.toExpression(simulation);
        // LINT
        ensureValidType(simulation, expression);
        ensureExist(simulation, expressionable.getCrdtExistsList());
        ensureDontExist(simulation, expressionable.getCrdtDontExistList());
        return expressionRepository.save(expression);
    }
    public List<Expression> findBySimulationId(Long id) {
        Simulation simulation = simulationRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        return expressionRepository.findBySimulation(simulation);
    }

    public Environment eval(ExpressionList expressionList) {
        return switch (expressionList.getType()) {
            case GSET -> gSetService.eval(expressionList);
            case TWOPSET -> twoPSetService.eval(expressionList);
            case LWWSET -> lwwSetService.eval(expressionList);
        };
    }
    private static void ensureValidType(Simulation simulation, Expression expression) {
        CrdtType expressionType = Operator.toType(expression.getOperator());
        CrdtType simulationType = simulation.getType();
        if (expressionType != simulationType) {
            throw new RuntimeException("Expression type [" + expressionType
                    + "] differs from simulation type [" + simulationType + "]");
        }
    }
    private void ensureExist(Simulation simulation, List<String> mustExist) {
        for (String operand : mustExist) {
            List<Expression> matchingCrdt = expressionRepository.findBySimulationAndOperands(simulation, operand);
            if (matchingCrdt.size() == 0)
                throw new RuntimeException("The following CRDT is not found: " + operand);
        }
    }
    private void ensureDontExist(Simulation simulation, List<String> mustNotExist) {
        for (String operand : mustNotExist) {
            List<Expression> matchingCrdt = expressionRepository.findBySimulationAndOperands(simulation, operand);
            if (matchingCrdt.size() > 0)
                throw new RuntimeException("The following CRDTs already exists: " + matchingCrdt);
        }
    }
}
