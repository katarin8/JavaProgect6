package domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class Crossroads {
    private List<Road> roads = new ArrayList<>();
    private List<Car> cars = new ArrayList<>();
}
