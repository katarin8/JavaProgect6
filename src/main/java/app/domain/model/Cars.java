package app.domain.model;

import app.domain.DTO.DriveModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Data
@Entity(name = "cars")
@NoArgsConstructor
public class Cars {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "speed")
    private Integer speed;

    @Column(name = "driveModel", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private DriveModel driveModel;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Nullable
    private RoadBlock roadBlock;

    private Boolean hasTurned;
}
