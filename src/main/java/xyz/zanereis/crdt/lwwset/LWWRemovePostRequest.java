package xyz.zanereis.crdt.lwwset;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Value
public class LWWRemovePostRequest {
    @NotBlank
    String name;
    @NotBlank
    String remove;
    public LWWSetRemove toLwwSetRemove() {
        return new LWWSetRemove(name, remove, LocalDateTime.now());
    }
}
