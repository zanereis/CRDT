package xyz.zanereis.crdt.expression;

import xyz.zanereis.crdt.Simulation;

import java.util.List;

public interface Expressionable {
    Expression toExpression(Simulation simulation);
    List<String> getCrdtExistsList();
    List<String> getCrdtDontExistList();
}
