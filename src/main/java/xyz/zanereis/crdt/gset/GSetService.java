package xyz.zanereis.crdt.gset;

import org.springframework.stereotype.Service;
import xyz.zanereis.crdt.CrdtType;
import xyz.zanereis.crdt.expression.Evalable;
import xyz.zanereis.crdt.expression.Expression;
import xyz.zanereis.crdt.expression.ExpressionList;

@Service
public class GSetService {
    public GSetEnvironment eval(ExpressionList list) {
        GSetEnvironment env = new GSetEnvironment();
        if (list.getType() != CrdtType.GSET)
            throw new RuntimeException("Invalid CrdtType for expression list");
        for (Expression expression : list.getList()) {
            toEvalable(expression).eval(env);
        }
        return env;
    }
    private Evalable<GSetEnvironment> toEvalable(Expression expression) {
        return switch (expression.getOperator()) {
            case GSETADD -> GSetAdd.fromExpression(expression);
            case GSETMERGE -> GSetMerge.fromExpression(expression);
            case GSETNEW -> GSetNew.fromExpression(expression);
            default -> throw new RuntimeException("Unexpected Operator Type: " + expression.getOperator());
        };
    }
}
