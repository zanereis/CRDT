package xyz.zanereis.crdt.twopset;

import org.springframework.stereotype.Service;
import xyz.zanereis.crdt.CrdtType;
import xyz.zanereis.crdt.expression.Evalable;
import xyz.zanereis.crdt.expression.Expression;
import xyz.zanereis.crdt.expression.ExpressionList;

@Service
public class TwoPSetService {
    public TwoPSetEnvironment eval(ExpressionList list) {
        TwoPSetEnvironment env = new TwoPSetEnvironment();
        if (list.getType() != CrdtType.TWOPSET)
            throw new RuntimeException("Invalid CrdtType for expression list: "
                    + list.getType() + " is not of type " + CrdtType.TWOPSET);
        for (Expression expression : list.getList()) {
            toEvalable(expression).eval(env);
        }
        return env;
    }
    private Evalable<TwoPSetEnvironment> toEvalable(Expression expression) {
        return switch (expression.getOperator()) {
            case TWOPSETADD -> TwoPSetAdd.fromExpression(expression);
            case TWOPSETMERGE -> TwoPSetMerge.fromExpression(expression);
            case TWOPSETNEW -> TwoPSetNew.fromExpression(expression);
            case TWOPSETREMOVE -> TwoPSetRemove.fromExpression(expression);
            default -> throw new RuntimeException("Unexpected Operator Type: " + expression.getOperator());
        };
    }
}
