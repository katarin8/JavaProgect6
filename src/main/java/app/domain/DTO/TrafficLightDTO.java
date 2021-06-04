package app.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrafficLightDTO {

    private Long id;
    private List<RoadBlockDTO> controlledBlocks;
    private TrafficLightState currentState;
    private long lastSwitchTime;
    private long cycleTimeRed;
    private long cycleTimeYellow;
    private long cycleTimeGreen;

    public TrafficLightDTO(List<RoadBlockDTO> controlledBlocks, TrafficLightState currentState) {
        this.controlledBlocks = controlledBlocks;
        this.currentState = currentState;
    }
}
