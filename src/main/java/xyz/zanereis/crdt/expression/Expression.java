package xyz.zanereis.crdt.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.zanereis.crdt.Simulation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Expression {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @ManyToOne
    private Simulation simulation;
    @NotNull
    private Operator operator;
    @NotNull
    @ElementCollection
    private List<String> operands;
    @Override
    public String toString() {
        return operator.toString() + ": " + operands.toString();
    }
}
