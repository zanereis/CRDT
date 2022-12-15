package xyz.zanereis.crdt.lwwset;

import lombok.Value;
import xyz.zanereis.crdt.Simulation;
import xyz.zanereis.crdt.expression.Evalable;
import xyz.zanereis.crdt.expression.Expression;
import xyz.zanereis.crdt.expression.Expressionable;
import xyz.zanereis.crdt.expression.Operator;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Value
public class LWWSetAdd implements Evalable<LWWSetEnvironment>, Expressionable {
    public static final Operator OPERATOR = Operator.LWWSETADD;
    @NotBlank
    String name;
    @NotBlank
    String added;
    LocalDateTime time;
    public void eval(LWWSetEnvironment env) {
        env.getCrdts().get(name).getAdded().put(added,time);
    }


    public static LWWSetAdd fromExpression(Expression expression) {
        if (expression.getOperator() != OPERATOR) {
            throw new RuntimeException("Invalid Operator Type when converting from Expression");
        }
        return new LWWSetAdd(
                expression.getOperands().get(0),
                expression.getOperands().get(1),
                LocalDateTime.parse(expression.getOperands().get(2))
        );
    }
    public Expression toExpression(Simulation simulation) {
        return new Expression(0L, simulation, OPERATOR, List.of(name, added, time.toString()));
    }
    public List<String> getCrdtExistsList() {
        return List.of(name);
    }
    public List<String> getCrdtDontExistList() {
        return new ArrayList<String>();
    }
}
