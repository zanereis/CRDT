package xyz.zanereis.crdt.lwwset;

import org.springframework.stereotype.Service;
import xyz.zanereis.crdt.CrdtType;
import xyz.zanereis.crdt.expression.Evalable;
import xyz.zanereis.crdt.expression.Expression;
import xyz.zanereis.crdt.expression.ExpressionList;

@Service
public class LWWSetService {
    public LWWSetEnvironment eval(ExpressionList list) {
        LWWSetEnvironment env = new LWWSetEnvironment();
        if (list.getType() != LWWSet.TYPE)
            throw new RuntimeException("Invalid CrdtType for expression list: "
                    + list.getType() + " is not of type " + LWWSet.TYPE);
        for (Expression expression : list.getList()) {
            toEvalable(expression).eval(env);
        }
        return env;
    }
    private Evalable<LWWSetEnvironment> toEvalable(Expression expression) {
        return switch (expression.getOperator()) {
            case LWWSETADD -> LWWSetAdd.fromExpression(expression);
            case LWWSETMERGE -> LWWSetMerge.fromExpression(expression);
            case LWWSETNEW -> LWWSetNew.fromExpression(expression);
            case LWWSETREMOVE -> LWWSetRemove.fromExpression(expression);
            default -> throw new RuntimeException("Unexpected Operator Type: " + expression.getOperator());
        };
    }
}
