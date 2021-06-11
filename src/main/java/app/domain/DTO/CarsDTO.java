package app.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarsDTO {

    private Long id;
    private Integer speed;
    private DriveLine driveLine;
    private RoadBlockDTO roadBlock;
    private Boolean hasTurned = false;

    public CarsDTO(Integer speed, DriveLine driveLine, RoadBlockDTO roadBlock) {
        this.speed = speed;
        this.driveLine = driveLine;
        this.roadBlock = roadBlock;
    }

}
