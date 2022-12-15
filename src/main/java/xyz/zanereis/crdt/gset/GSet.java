package xyz.zanereis.crdt.gset;

import lombok.Data;
import xyz.zanereis.crdt.CrdtType;
import xyz.zanereis.crdt.Simulation;

import java.util.HashSet;
import java.util.Set;

@Data
public class GSet {
    public static final CrdtType TYPE = CrdtType.GSET;
    Set<String> elements = new HashSet<String>();
    public static void checkType(Simulation simulation) {
        if (simulation.getType() != TYPE)
            throw new RuntimeException("Unexpected Operator Type: " + simulation.getType());
    }
}
