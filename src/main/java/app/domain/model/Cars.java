package app.domain.model;

import app.domain.DTO.DriveLine;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Data
@Entity(name = "cars")
@NoArgsConstructor
public class Cars {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "speed")
    private Integer speed;

    @Column(name = "driveLine", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private DriveLine driveLine;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Nullable
    private RoadBlock roadBlock;

    private Boolean hasTurned;
}
