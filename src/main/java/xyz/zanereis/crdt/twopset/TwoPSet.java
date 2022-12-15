package xyz.zanereis.crdt.twopset;

import lombok.Data;
import xyz.zanereis.crdt.CrdtType;
import xyz.zanereis.crdt.Simulation;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class TwoPSet {
    public static final CrdtType TYPE = CrdtType.TWOPSET;
    Set<String> added = new HashSet<String>();
    Set<String> tombstone = new HashSet<String>();
    public Set<String> getElements() {
        return added.stream().filter((e) -> !tombstone.contains(e)).collect(Collectors.toSet());
    }
    public static void checkType(Simulation simulation) {
        if (simulation.getType() != TYPE)
            throw new RuntimeException("Unexpected Operator Type: " + simulation.getType());
    }
}
