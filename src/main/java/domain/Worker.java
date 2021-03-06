package domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class Worker {
    private String name;
    private Set<RepairType> abilities = new HashSet<>();

}
