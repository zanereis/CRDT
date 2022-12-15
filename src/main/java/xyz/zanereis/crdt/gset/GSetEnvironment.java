package xyz.zanereis.crdt.gset;

import lombok.Data;
import xyz.zanereis.crdt.expression.Environment;

import java.util.HashMap;
import java.util.Map;

@Data
public class GSetEnvironment implements Environment<GSet> {
    HashMap<String, GSet> crdts = new HashMap<String, GSet>();

    @Override
    public GSet resolve(String name) {
        return crdts.get(name);
    }
    @Override
    public HashMap<String,GSet> getCrdts() {
        return crdts;
    }
    @Override
    public GSet getConvergentState() {
        return GSetMerge.mergeAll(crdts.values().stream().toList());
    }
}
