package xyz.zanereis.crdt.twopset;

import lombok.Data;
import xyz.zanereis.crdt.expression.Environment;

import java.util.HashMap;
import java.util.Map;

@Data
public class TwoPSetEnvironment implements Environment<TwoPSet> {
    HashMap<String, TwoPSet> crdts = new HashMap<String, TwoPSet>();

    @Override
    public TwoPSet resolve(String name) {
        return crdts.get(name);
    }
    @Override
    public HashMap<String, TwoPSet> getCrdts() {
        return crdts;
    }
    @Override
    public TwoPSet getConvergentState() {
        return TwoPSetMerge.mergeAll(crdts.values().stream().toList());
    }
}
