package app.domain.model;

import app.domain.DTO.TrafficLightState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity(name = "trafficLight")
@NoArgsConstructor
@AllArgsConstructor
public class TrafficLight {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trafficLightState", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TrafficLightState currentState;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<RoadBlock> controlledBlocks;

    @Column(name = "lastSwitchTime")
    private Long lastSwitchTime;

    @Column(name = "cycleTimeRed")
    private Long cycleTimeRed;

    @Column(name = "cycleTimeYellow")
    private Long cycleTimeYellow;

    @Column(name = "cycleTimeGreen")
    private Long cycleTimeGreen;
}
