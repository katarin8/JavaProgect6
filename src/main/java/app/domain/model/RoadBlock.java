package app.domain.model;


import app.domain.DTO.TrafficLightState;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "roadblocks")
@NoArgsConstructor
public class RoadBlock {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Nullable
    private RoadBlock leftBlock;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Nullable
    private RoadBlock centerBlock;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Nullable
    private RoadBlock rightBlock;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Nullable
    private Cars cars;

    @Column(name = "trafficLightState", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TrafficLightState trafficLightState;

    private Boolean isCrossroad;

    @Override
    public String toString() {
        return "RoadBlock{" +
                "id=" + id +
                ", leftBlockId=" + (leftBlock == null ? "none" : leftBlock.getId()) +
                ", centerBlockId=" + (centerBlock == null ? "none" : centerBlock.getId()) +
                ", rightBlockId=" + (rightBlock == null ? "none" : rightBlock.getId()) +
                ", automobileId=" + (cars == null ? "none" : cars.getId()) +
                ", trafficLightState=" + trafficLightState +
                ", isCrossroad=" + isCrossroad +
                '}';
    }
}
