package domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class RepairRequest {
    private List<RepairType> jobs = new ArrayList<>();
    private Car car;
}
