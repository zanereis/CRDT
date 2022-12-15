package xyz.zanereis.crdt.lwwset;

import lombok.Data;
import xyz.zanereis.crdt.CrdtType;
import xyz.zanereis.crdt.Simulation;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class LWWSet {
    public static final CrdtType TYPE = CrdtType.LWWSET;
    HashMap<String, LocalDateTime> added = new HashMap<>();
    HashMap<String, LocalDateTime> tombstone = new HashMap<>();
    public Set<String> getElements() {
        Set<String> elements = new HashSet<String>();
        for (String key : added.keySet()) {
            if (!tombstone.containsKey(key) || tombstone.get(key).isBefore(added.get(key))) {
                elements.add(key);
            }
        }
        return elements;
    }
    public static void checkType(Simulation simulation) {
        if (simulation.getType() != TYPE)
            throw new RuntimeException("Unexpected Operator Type: " + simulation.getType());
    }
}
