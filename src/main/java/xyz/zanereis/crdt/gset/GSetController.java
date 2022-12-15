package xyz.zanereis.crdt.gset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.zanereis.crdt.expression.Expression;
import xyz.zanereis.crdt.expression.ExpressionService;

import javax.validation.Valid;

@RestController
public class GSetController {
    private final ExpressionService expressionService;
    @Autowired
    public GSetController(ExpressionService expressionService) {
        this.expressionService = expressionService;
    }
    @PostMapping("/simulations/{id}/expressions/gsetadd")
    Expression createGSetAdd(@PathVariable Long id, @RequestBody @Valid GSetAdd gSetAdd) {
        return expressionService.save(id, gSetAdd);
    }
    @PostMapping("/simulations/{id}/expressions/gsetnew")
    Expression createGSetNew(@PathVariable Long id, @RequestBody @Valid GSetNew gSetNew) {
        return expressionService.save(id, gSetNew);
    }
    @PostMapping("/simulations/{id}/expressions/gsetmerge")
    Expression createGSetNew(@PathVariable Long id, @RequestBody @Valid GSetMerge gSetMerge) {
        return expressionService.save(id, gSetMerge);
    }
}
