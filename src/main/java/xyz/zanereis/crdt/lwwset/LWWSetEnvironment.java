package xyz.zanereis.crdt.lwwset;

import lombok.Data;
import xyz.zanereis.crdt.expression.Environment;

import java.util.HashMap;
import java.util.Map;

@Data
public class LWWSetEnvironment implements Environment<LWWSet> {
    HashMap<String, LWWSet> crdts = new HashMap<String, LWWSet>();

    @Override
    public LWWSet resolve(String name) {
        return crdts.get(name);
    }
    @Override
    public HashMap<String, LWWSet> getCrdts() {
        return crdts;
    }
    @Override
    public LWWSet getConvergentState() {
        return LWWSetMerge.mergeAll(crdts.values().stream().toList());
    }
}
