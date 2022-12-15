package xyz.zanereis.crdt.lwwset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.zanereis.crdt.expression.Expression;
import xyz.zanereis.crdt.expression.ExpressionService;

import javax.validation.Valid;

@RestController
public class LWWSetController {
    private final ExpressionService expressionService;

    @Autowired
    public LWWSetController(ExpressionService expressionService) {
        this.expressionService = expressionService;
    }
    @PostMapping("/simulations/{id}/expressions/lwwsetadd")
    Expression createLwwSetRemove(@PathVariable Long id, @RequestBody @Valid LWWAddPostRequest lwwAddPostRequest) {
        return expressionService.save(id, lwwAddPostRequest.toLwwSetAdd());
    }
    @PostMapping("/simulations/{id}/expressions/lwwsetnew")
    Expression createLwwSetRemove(@PathVariable Long id, @RequestBody @Valid LWWSetNew LWWSetNew) {
        return expressionService.save(id, LWWSetNew);
    }
    @PostMapping("/simulations/{id}/expressions/lwwsetmerge")
    Expression createLwwSetRemove(@PathVariable Long id, @RequestBody @Valid LWWSetMerge LWWSetMerge) {
        return expressionService.save(id, LWWSetMerge);
    }
    @PostMapping("/simulations/{id}/expressions/lwwsetremove")
    Expression createLwwSetRemove(@PathVariable Long id, @RequestBody @Valid LWWRemovePostRequest lwwRemovePostRequest) {
        return expressionService.save(id, lwwRemovePostRequest.toLwwSetRemove());
    }
}
