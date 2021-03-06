package domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class AutoRepair {
    private Map<RepairType, Department> departmentMap = new HashMap<>();

}
