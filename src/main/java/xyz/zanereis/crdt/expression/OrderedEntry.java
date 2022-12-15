package xyz.zanereis.crdt.expression;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class OrderedEntry {
    Integer index;
    Object entry;
    public static List<OrderedEntry> fromList(List objects) {
        List<OrderedEntry> entries = new ArrayList<OrderedEntry>();
        for (int i = 1; i <= objects.size(); i++ ) {
            entries.add(new OrderedEntry(i,objects.get(i-1)));
        }
        return entries;
    }
}
