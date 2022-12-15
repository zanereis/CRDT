package xyz.zanereis.crdt.lwwset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class LWWSetNew implements Evalable<LWWSetEnvironment>, Expressionable {
    public static final Operator OPERATOR = Operator.LWWSETNEW;

    @NotBlank
    private String name;
    public void eval(LWWSetEnvironment env) {
        env.getCrdts().put(name,new LWWSet());
    }


    public static LWWSetNew fromExpression(Expression expression) {
        if (expression.getOperator() != OPERATOR) {
            throw new RuntimeException("Invalid Operator Type when converting from Expression");
        }
        return new LWWSetNew(expression.getOperands().get(0));
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
