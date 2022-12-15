package xyz.zanereis.crdt.twopset;

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
public class TwoPSetMerge implements Evalable<TwoPSetEnvironment>, Expressionable {
    public static final Operator OPERATOR = Operator.TWOPSETMERGE;

    @NotBlank
    String name;
    @NotBlank
    String mergeWith;
    public void eval(TwoPSetEnvironment env) {
        TwoPSet twoPSet = env.resolve(name);
        TwoPSet argumentTwoPSet = env.resolve(mergeWith);
        TwoPSet newTwoPSet = mergeAll(List.of(twoPSet, argumentTwoPSet));
        twoPSet.setAdded(newTwoPSet.getAdded());
        twoPSet.setTombstone(newTwoPSet.getTombstone());
    }
    public static TwoPSet mergeAll(List<TwoPSet> twoPSets) {
        Set<String> added = new HashSet<String>();
        for (TwoPSet twoPSet : twoPSets) {
            added.addAll(twoPSet.getAdded());
        }

        Set<String> tombstone = new HashSet<String>();
        for (TwoPSet twoPSet : twoPSets) {
            tombstone.addAll(twoPSet.getTombstone());
        }
        TwoPSet newTwoPSet = new TwoPSet();
        newTwoPSet.setAdded(added);
        newTwoPSet.setTombstone(tombstone);
        return newTwoPSet;
    }


    public static TwoPSetMerge fromExpression(Expression expression) {
        if (expression.getOperator() != OPERATOR) {
            throw new RuntimeException("Invalid Operator Type when converting from Expression");
        }
        return new TwoPSetMerge(expression.getOperands().get(0),expression.getOperands().get(1));
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
