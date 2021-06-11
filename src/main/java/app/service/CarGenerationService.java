package app.service;

import app.component.RoadComponent;
import app.domain.DTO.CarsDTO;
import app.domain.DTO.DriveLine;
import app.domain.DTO.RoadDTO;
import app.domain.DTO.RoadBlockDTO;
import app.mapper.MainMapper;
import app.repository.CarsRepo;
import app.repository.RoadRepo;
import app.repository.RoadBlockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CarGenerationService {
    private final RoadRepo roadRepo;
    private final CarsRepo carsRepo;
    private final RoadComponent roadComponent;
    private final RoadBlockRepo roadBlockRepo;

    private final MainMapper mapper;

    @Autowired
    public CarGenerationService(MainMapper mapper, RoadRepo roadRepo,
                                RoadBlockRepo roadBlockRepo,
                                CarsRepo carsRepo,
                                RoadComponent roadComponent) {
        this.mapper = mapper;
        this.roadRepo = roadRepo;
        this.roadBlockRepo = roadBlockRepo;
        this.carsRepo = carsRepo;
        this.roadComponent = roadComponent;
    }

    public void generateCars(int count) {
        Random random = new Random();


        for (int i = 0; i < count && i < roadComponent.getLinesPerSide() * 4; i++) {
            RoadDTO ln;

            do {
                int lineNum = random.nextInt(roadComponent.getLinesPerSide() * 4);
                ln = mapper.lineToLineDTO(roadRepo.get(lineNum + 1L).get()); //fixed
            }
            while (ln == null || (ln != null && ln.getStartBlock().getAutomobile() != null));

            RoadBlockDTO startBlock = ln.getStartBlock();
            int autoSpeed = random.nextInt(roadComponent.getMaxAutoSpeed() - roadComponent.getMinAutoSpeed());

            CarsDTO carsDTO = new CarsDTO(autoSpeed + roadComponent.getMinAutoSpeed(),
                    DriveLine.values()[random.nextInt(DriveLine.values().length)],
                    startBlock);
            startBlock.setAutomobile(carsDTO);
            carsDTO.setRoadBlock(startBlock);

            var auto = mapper.autoDtoToAuto(carsDTO);
            carsRepo.save(auto);
            carsDTO.setId(auto.getId());
            startBlock.setAutomobile(carsDTO);

            auto = mapper.autoDtoToAuto(carsDTO);
            carsRepo.update(auto);
            roadBlockRepo.update(auto.getRoadBlock());
        }

    }
}
