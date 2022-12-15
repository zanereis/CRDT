package xyz.zanereis.crdt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.zanereis.crdt.gset.*;
import xyz.zanereis.crdt.expression.ExpressionList;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SimulationController {
    private final SimulationRepository simulationRepository;
    private final GSetService gSetService;

    @Autowired
    public SimulationController(SimulationRepository simulationRepository, GSetService gSetService) {
        this.simulationRepository = simulationRepository;
        this.gSetService = gSetService;
    }

    @PostMapping("/simulations")
    public Simulation newSimulation(@RequestBody @Valid Simulation simulation) {
        return simulationRepository.save(simulation);
    }

    @GetMapping("/simulations")
    public List<Simulation> getSimulations() {
        return simulationRepository.findAll();
    }

    @GetMapping("/test1")
    public GSetEnvironment getSimulation() {
        GSetEnvironment env = new GSetEnvironment();
        new GSetNew("yams").eval(env);
        new GSetAdd("yams","beans").eval(env);
        new GSetAdd("yams","potatoes").eval(env);
        new GSetNew("beans").eval(env);
        new GSetNew("baz").eval(env);
        new GSetAdd("baz","beans").eval(env);
        new GSetAdd("baz","meat").eval(env);
        new GSetMerge("yams","baz").eval(env);
        return env;
    }
    @GetMapping("/test2")
    public GSetEnvironment getSimulation2() {
        Simulation s = new Simulation(0L,CrdtType.GSET);
        ExpressionList list = new ExpressionList(List.of(
                new GSetNew("yams").toExpression(s),
                new GSetAdd("yams","beans").toExpression(s),
                new GSetAdd("yams","potatoes").toExpression(s),
                new GSetNew("beans").toExpression(s),
                new GSetNew("baz").toExpression(s),
                new GSetAdd("baz","beans").toExpression(s),
                new GSetAdd("baz","meat").toExpression(s),
                new GSetMerge("yams","baz").toExpression(s)
        ));
        return gSetService.eval(list);
    }
    @GetMapping("/test3")
    public GSet getSimulation3() {
        Simulation s = new Simulation(0L,CrdtType.GSET);
        ExpressionList list = new ExpressionList(List.of(
                new GSetNew("yams").toExpression(s),
                new GSetAdd("yams","beans").toExpression(s),
                new GSetAdd("yams","potatoes").toExpression(s),
                new GSetNew("beans").toExpression(s),
                new GSetNew("baz").toExpression(s),
                new GSetAdd("baz","toxic waste").toExpression(s),
                new GSetAdd("baz","beans").toExpression(s),
                new GSetAdd("baz","meat").toExpression(s)
//                new LWWSetMerge("yams","baz").toExpression(s)
        ));
        List<GSet> gSets = gSetService.eval(list).getCrdts().values().stream().toList();
        return GSetMerge.mergeAll(gSets);
//        return gSetService.eval(list);
    }
}
