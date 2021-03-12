package domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Car {
    private String name;
    private int num;
    private int primarySpeed;
    private Road rd;
}
