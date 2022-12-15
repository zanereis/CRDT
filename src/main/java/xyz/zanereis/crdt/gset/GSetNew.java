package xyz.zanereis.crdt.gset;

import lombok.*;
import xyz.zanereis.crdt.Simulation;
import xyz.zanereis.crdt.expression.Evalable;
import xyz.zanereis.crdt.expression.Expression;
import xyz.zanereis.crdt.expression.Expressionable;
import xyz.zanereis.crdt.expression.Operator;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GSetNew implements Evalable<GSetEnvironment>, Expressionable {
    public static final Operator OPERATOR = Operator.GSETNEW;

    @NotBlank
    private String name;
    public void eval(GSetEnvironment env) {
        env.getCrdts().put(name,new GSet());
    }
    public static GSetNew fromExpression(Expression expression) {
        if (expression.getOperator() != Operator.GSETNEW) {
            throw new RuntimeException("Invalid Operator Type when converting from Expression");
        }
        return new GSetNew(expression.getOperands().get(0));
    }
    public Expression toExpression(Simulation simulation) {
        return new Expression(0L, simulation, OPERATOR, List.of(name));
    }
    public List<String> getCrdtExistsList() {
        return new ArrayList<String>();
    }
    public List<String> getCrdtDontExistList() {
        return List.of(name);
    }
}
