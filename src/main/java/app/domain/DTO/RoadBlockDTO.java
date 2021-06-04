package app.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoadBlockDTO {

    private Long id;
    private RoadBlockDTO[] automobileLinksList = new RoadBlockDTO[3];
    private CarsDTO automobile;
    private TrafficLightState trafficLightState;
    private Boolean isCrossRoad = false;

    @Override
    public String toString() {
        return "RoadBlockDTO{" +
                "id=" + id +
                ", automobileLinksList=[" + "leftId=" + (automobileLinksList[0] == null ? "null" : automobileLinksList[0].getId()) +
                "centerId=" + (automobileLinksList[1] == null ? "null" : automobileLinksList[1].getId()) + "rightId=" + (automobileLinksList[2] == null ? "null" : automobileLinksList[2].getId()) +
                "], automobileId=" + (automobile == null ? "null" : automobile.getId()) +
                ", trafficLightState=" + trafficLightState +
                ", isCrossRoad=" + isCrossRoad +
                '}';
    }
}
