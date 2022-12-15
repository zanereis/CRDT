package xyz.zanereis.crdt.gset;

import lombok.Value;
import xyz.zanereis.crdt.Simulation;
import xyz.zanereis.crdt.expression.Evalable;
import xyz.zanereis.crdt.expression.Expression;
import xyz.zanereis.crdt.expression.Expressionable;
import xyz.zanereis.crdt.expression.Operator;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Value
public class GSetMerge implements Evalable<GSetEnvironment>, Expressionable {
    public static final Operator OPERATOR = Operator.GSETMERGE;

    @NotBlank
    String name;
    @NotBlank
    String mergeWith;
    public void eval(GSetEnvironment env) {
        GSet gSet = env.resolve(name);
        GSet argumentGSet = env.resolve(mergeWith);
//        gSet.getElements().addAll(argumentGSet.getElements());
        GSet newGSet = mergeAll(List.of(gSet,argumentGSet));
        gSet.setElements(newGSet.getElements());
    }
    public static GSet mergeAll(List<GSet> gSets) {
        Set<String> elements = new HashSet<String>();
        for (GSet gSet : gSets) {
            elements.addAll(gSet.getElements());
        }
        GSet newGSet = new GSet();
        newGSet.setElements(elements);
        return newGSet;
    }
    public static GSetMerge fromExpression(Expression expression) {
        if (expression.getOperator() != OPERATOR) {
            throw new RuntimeException("Invalid Operator Type when converting from Expression");
        }
        return new GSetMerge(expression.getOperands().get(0),expression.getOperands().get(1));
    }
    public Expression toExpression(Simulation simulation) {
        return new Expression(0L, simulation, OPERATOR, List.of(name, mergeWith));
    }
    public List<String> getCrdtExistsList() {
        return List.of(name, mergeWith);
    }
    public List<String> getCrdtDontExistList() {
        return new ArrayList<String>();
    }
}
