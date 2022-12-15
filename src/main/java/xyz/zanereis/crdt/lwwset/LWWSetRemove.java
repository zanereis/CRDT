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
public class LWWSetRemove implements Evalable<LWWSetEnvironment>, Expressionable {
    public static final Operator OPERATOR = Operator.LWWSETREMOVE;
    @NotBlank
    String name;
    @NotBlank
    String remove;
    LocalDateTime time;
    public void eval(LWWSetEnvironment env) {
        env.getCrdts().get(name).getTombstone().put(remove,time);
    }


    public static LWWSetRemove fromExpression(Expression expression) {
        if (expression.getOperator() != OPERATOR) {
            throw new RuntimeException("Invalid Operator Type when converting from Expression");
        }
        return new LWWSetRemove(
                expression.getOperands().get(0),
                expression.getOperands().get(1),
                LocalDateTime.parse(expression.getOperands().get(2))
        );
    }
    public Expression toExpression(Simulation simulation) {
        return new Expression(0L, simulation, OPERATOR, List.of(name, remove, time.toString()));
    }
    public List<String> getCrdtExistsList() {
        return List.of(name);
    }
    public List<String> getCrdtDontExistList() {
        return new ArrayList<String>();
    }
}
