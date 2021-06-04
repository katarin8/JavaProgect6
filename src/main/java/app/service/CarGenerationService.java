package app.service;

import app.component.RoadComponent;
import app.domain.DTO.CarsDTO;
import app.domain.DTO.DriveModel;
import app.domain.DTO.LineDTO;
import app.domain.DTO.RoadBlockDTO;
import app.mapper.MainMapper;
import app.repository.CarsRepo;
import app.repository.LineRepo;
import app.repository.RoadBlockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CarGenerationService {
    private final LineRepo lineRepo;
    private final CarsRepo carsRepo;
    private final RoadComponent roadComponent;
    private final RoadBlockRepo roadBlockRepo;

    private final MainMapper mapper;

    @Autowired
    public CarGenerationService(MainMapper mapper, LineRepo lineRepo,
                                RoadBlockRepo roadBlockRepo,
                                CarsRepo carsRepo,
                                RoadComponent roadComponent) {
        this.mapper = mapper;
        this.lineRepo = lineRepo;
        this.roadBlockRepo = roadBlockRepo;
        this.carsRepo = carsRepo;
        this.roadComponent = roadComponent;
    }

    public void generateCars(int count) {
        Random random = new Random();


        for (int i = 0; i < count && i < roadComponent.getLinesPerSide() * 4; i++) {
            LineDTO ln;

            do {
                int lineNum = random.nextInt(roadComponent.getLinesPerSide() * 4);
                ln = mapper.lineToLineDTO(lineRepo.get(lineNum + 1L).get()); //fixed
            }
            while (ln == null || (ln != null && ln.getStartBlock().getAutomobile() != null));

            RoadBlockDTO startBlock = ln.getStartBlock();
            int autoSpeed = random.nextInt(roadComponent.getMaxAutoSpeed() - roadComponent.getMinAutoSpeed());

            CarsDTO carsDTO = new CarsDTO(autoSpeed + roadComponent.getMinAutoSpeed(),
                    DriveModel.values()[random.nextInt(DriveModel.values().length)],
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
