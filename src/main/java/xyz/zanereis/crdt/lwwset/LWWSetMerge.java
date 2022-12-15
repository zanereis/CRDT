package xyz.zanereis.crdt.lwwset;

import lombok.Value;
import xyz.zanereis.crdt.Simulation;
import xyz.zanereis.crdt.expression.Evalable;
import xyz.zanereis.crdt.expression.Expression;
import xyz.zanereis.crdt.expression.Expressionable;
import xyz.zanereis.crdt.expression.Operator;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.*;

@Value
public class LWWSetMerge implements Evalable<LWWSetEnvironment>, Expressionable {
    public static final Operator OPERATOR = Operator.LWWSETMERGE;

    @NotBlank
    String name;
    @NotBlank
    String mergeWith;
    public void eval(LWWSetEnvironment env) {
        LWWSet LWWSet = env.resolve(name);
        LWWSet argumentLWWSet = env.resolve(mergeWith);
        LWWSet newLWWSet = mergeAll(List.of(LWWSet, argumentLWWSet));
        LWWSet.setAdded(newLWWSet.getAdded());
        LWWSet.setTombstone(newLWWSet.getTombstone());
    }
    public static LWWSet mergeAll(List<LWWSet> lwwSets) {
        List<HashMap<String,LocalDateTime>> addeds = lwwSets.stream().map(LWWSet::getAdded).toList();
        List<HashMap<String,LocalDateTime>> tombstones = lwwSets.stream().map(LWWSet::getTombstone).toList();
        HashMap<String, LocalDateTime> added = merge(addeds);
        HashMap<String, LocalDateTime> tombstone = merge(tombstones);
        LWWSet newLWWSet = new LWWSet();
        newLWWSet.setAdded(added);
        newLWWSet.setTombstone(tombstone);
        return newLWWSet;
    }
    private static HashMap<String, LocalDateTime> merge(List<HashMap<String,LocalDateTime>> maps) {
        HashMap<String, LocalDateTime> merged = new HashMap<>();
        for (HashMap<String, LocalDateTime> map : maps) {
            for (String key : map.keySet()) {
                if (merged.containsKey(key) && merged.get(key).isAfter(map.get(key))) {
                    continue;
                }
                merged.put(key,map.get(key));
            }
        }
        return merged;
    }


    public static LWWSetMerge fromExpression(Expression expression) {
        if (expression.getOperator() != OPERATOR) {
            throw new RuntimeException("Invalid Operator Type when converting from Expression");
        }
        return new LWWSetMerge(expression.getOperands().get(0),expression.getOperands().get(1));
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
