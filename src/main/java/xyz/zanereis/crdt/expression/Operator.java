package xyz.zanereis.crdt.expression;

import lombok.Getter;
import xyz.zanereis.crdt.CrdtType;

@Getter
public enum Operator {
    GSETADD,
    GSETMERGE,
    GSETNEW,
    TWOPSETADD,
    TWOPSETREMOVE,
    TWOPSETMERGE,
    TWOPSETNEW,
    LWWSETADD,
    LWWSETREMOVE,
    LWWSETMERGE,
    LWWSETNEW;
    public static CrdtType toType(Operator operator) {
        return switch (operator) {
            case GSETADD, GSETMERGE, GSETNEW -> CrdtType.GSET;
            case TWOPSETADD, TWOPSETREMOVE, TWOPSETNEW, TWOPSETMERGE -> CrdtType.TWOPSET;
            case LWWSETMERGE, LWWSETREMOVE, LWWSETADD, LWWSETNEW -> CrdtType.LWWSET;
        };
    }
}
