package xyz.zanereis.crdt.lwwset;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Value
public class LWWAddPostRequest {
    @NotBlank
    String name;
    @NotBlank
    String add;
    public LWWSetAdd toLwwSetAdd() {
        return new LWWSetAdd(name, add, LocalDateTime.now());
    }
}
