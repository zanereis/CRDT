package xyz.zanereis.crdt.expression;

import lombok.Getter;
import lombok.Value;
import xyz.zanereis.crdt.CrdtType;
import xyz.zanereis.crdt.Simulation;

import java.util.List;
import java.util.stream.Stream;

@Getter
public class ExpressionList {
    public ExpressionList(List<Expression> list) {
        type = Operator.toType(list.get(0).getOperator());
        this.list = list;
    }
    private final CrdtType type;
    private final List<Expression> list;
    private static void ensureSameType(List<Expression> list) {
        Stream<CrdtType> distictTypes = list.stream()
                .map(Expression::getOperator)
                .map(Operator::toType).distinct();
        boolean allSameType = distictTypes.count() == 1;
        if (!allSameType) {
            throw new RuntimeException("Expressions are from different types of simulations: " + distictTypes);
        }
    }
}
