package xyz.zanereis.crdt.twopset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.zanereis.crdt.expression.Expression;
import xyz.zanereis.crdt.expression.ExpressionService;

import javax.validation.Valid;

@RestController
public class TwoPSetController {
    private final ExpressionService expressionService;
    @Autowired
    public TwoPSetController(ExpressionService expressionService) {
        this.expressionService = expressionService;
    }
    @PostMapping("/simulations/{id}/expressions/twopsetadd")
    Expression createTwoPSetAdd(@PathVariable Long id, @RequestBody @Valid TwoPSetAdd twoPSetAdd) {
        return expressionService.save(id, twoPSetAdd);
    }
    @PostMapping("/simulations/{id}/expressions/twopsetnew")
    Expression createTwoPSetNew(@PathVariable Long id, @RequestBody @Valid TwoPSetNew twoPSetNew) {
        return expressionService.save(id, twoPSetNew);
    }
    @PostMapping("/simulations/{id}/expressions/twopsetmerge")
    Expression createTwoPSetMerge(@PathVariable Long id, @RequestBody @Valid TwoPSetMerge twoPSetMerge) {
        return expressionService.save(id, twoPSetMerge);
    }
    @PostMapping("/simulations/{id}/expressions/twopsetremove")
    Expression createTwoPSetAdd(@PathVariable Long id, @RequestBody @Valid TwoPSetRemove twoPSetRemove) {
        return expressionService.save(id, twoPSetRemove);
    }
}
