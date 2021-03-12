package domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Light {
    private Boolean col; //Таймер будет идти по цвету
    private Boolean yel; //промежуточный жёлтый цвет
}
