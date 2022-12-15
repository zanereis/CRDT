package xyz.zanereis.crdt.gset;

import lombok.Value;
import xyz.zanereis.crdt.Simulation;
import xyz.zanereis.crdt.expression.Evalable;
import xyz.zanereis.crdt.expression.Expression;
import xyz.zanereis.crdt.expression.Expressionable;
import xyz.zanereis.crdt.expression.Operator;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Value
public class GSetAdd implements Evalable<GSetEnvironment>, Expressionable {
    public static final Operator OPERATOR = Operator.GSETADD;
    @NotBlank
    String name;
    @NotBlank
    String add;
    public void eval(GSetEnvironment env) {
        env.getCrdts().get(name).getElements().add(add);
    }
    public static GSetAdd fromExpression(Expression expression) {
        if (expression.getOperator() != OPERATOR) {
            throw new RuntimeException("Invalid Operator Type when converting from Expression");
        }
        return new GSetAdd(expression.getOperands().get(0),expression.getOperands().get(1));
    }
    public Expression toExpression(Simulation simulation) {
        return new Expression(0L, simulation, OPERATOR, List.of(name, add));
    }
    public List<String> getCrdtExistsList() {
        return List.of(name);
    }
    public List<String> getCrdtDontExistList() {
        return new ArrayList<String>();
    }
}
