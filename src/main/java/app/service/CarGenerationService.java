package app.service;

import app.component.CrossRoads;
import app.domain.DTO.CarsDTO;
import app.domain.DTO.DriveModel;
import app.domain.DTO.LineDTO;
import app.domain.DTO.RoadBlockDTO;
import app.mapper.MainMapper;
import app.repository.AutomobileRepository;
import app.repository.LineRepository;
import app.repository.RoadBlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CarGenerationService {
    private final LineRepository lineRepository;
    private final AutomobileRepository automobileRepository;
    private final CrossRoads crossRoads;
    private final RoadBlockRepository roadBlockRepository;

    private final MainMapper mapper;

    @Autowired
    public CarGenerationService(MainMapper mapper, LineRepository lineRepository,
                                RoadBlockRepository roadBlockRepository,
                                AutomobileRepository automobileRepository,
                                CrossRoads crossRoads) {
        this.mapper = mapper;
        this.lineRepository = lineRepository;
        this.roadBlockRepository = roadBlockRepository;
        this.automobileRepository = automobileRepository;
        this.crossRoads = crossRoads;
    }

    public void generateCars(int count) {
        Random random = new Random();


        for (int i = 0; i < count && i < crossRoads.getLinesPerSide() * 4; i++) {
            LineDTO ln;

            do {
                int lineNum = random.nextInt(crossRoads.getLinesPerSide() * 4);
                ln = mapper.lineToLineDTO(lineRepository.get(lineNum + 1L).get()); //fixed
            }
            while (ln == null || (ln != null && ln.getStartBlock().getAutomobile() != null));

            RoadBlockDTO startBlock = ln.getStartBlock();
            int autoSpeed = random.nextInt(crossRoads.getMaxAutoSpeed() - crossRoads.getMinAutoSpeed());

            CarsDTO carsDTO = new CarsDTO(autoSpeed + crossRoads.getMinAutoSpeed(),
                    DriveModel.values()[random.nextInt(DriveModel.values().length)],
                    startBlock);
            startBlock.setAutomobile(carsDTO);
            carsDTO.setRoadBlock(startBlock);

            var auto = mapper.autoDtoToAuto(carsDTO);
            automobileRepository.save(auto);
            carsDTO.setId(auto.getId());
            startBlock.setAutomobile(carsDTO);

            auto = mapper.autoDtoToAuto(carsDTO);
            automobileRepository.update(auto);
            roadBlockRepository.update(auto.getRoadBlock());
        }

        var d = automobileRepository.getAll();
        var aaaaaaa = 0;
    }
}
