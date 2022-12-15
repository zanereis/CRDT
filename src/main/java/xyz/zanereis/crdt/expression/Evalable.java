package xyz.zanereis.crdt.expression;

public interface Evalable<T> {
   void eval(T environment);
}
