package xyz.zanereis.crdt;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Simulation {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CrdtType type;
}
