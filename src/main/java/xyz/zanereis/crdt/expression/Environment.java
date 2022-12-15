package xyz.zanereis.crdt.expression;

import java.util.HashMap;
import java.util.Map;

public interface Environment<T> {
    T resolve(String name);
    HashMap<String,T> getCrdts();
    T getConvergentState();
}
