package xyz.zanereis.crdt.expression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ExpressionController {
    private final ExpressionService expressionService;

    @Autowired
    public ExpressionController(ExpressionService expressionService) {
        this.expressionService = expressionService;
    }
    List<Expression> getExpressions(Long id) {
        return expressionService.findBySimulationId(id);
    }
    @GetMapping("/simulations/{id}/history")
    public List<OrderedEntry> getOrderedExpressions(@PathVariable Long id) {
        return OrderedEntry.fromList(getExpressions(id));
    }

    @GetMapping("/simulations/{id}/environment")
    public Environment getEnvironment(@PathVariable Long id) {
        List<Expression> expressions = expressionService.findBySimulationId(id);
        ExpressionList expressionList = new ExpressionList(expressions);
        return expressionService.eval(expressionList);
    }
    @GetMapping("/simulations/{id}/history/{n}/environment")
    public Environment viewHistoricEnvironment(@PathVariable Long id, @PathVariable Integer n) {
        List<Expression> expressions = getExpressions(id);
        List<Expression> filteredExpressions = expressions.subList(0,n);
        ExpressionList expressionList = new ExpressionList(filteredExpressions);
        return expressionService.eval(expressionList);
    }
    @GetMapping("/simulations/{id}/history/environment")
    List<OrderedEntry> viewHistoricEnvironments(@PathVariable Long id) {
        List<Expression> expressions = getExpressions(id);
        List<Environment> environments = new ArrayList<>();
        for (int i = 1; i <= expressions.size(); i++) {
            List<Expression> filteredExpressions = new ArrayList<Expression>(expressions).subList(0,i);
            ExpressionList expressionList = new ExpressionList(filteredExpressions);
            environments.add(expressionService.eval(expressionList));
        }
        return OrderedEntry.fromList(environments);
    }
}
