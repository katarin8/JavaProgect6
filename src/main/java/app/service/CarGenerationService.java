package app.service;

import app.component.RoadComponent;
import app.domain.DTO.AutomobileDTO;
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
    private final RoadComponent roadComponent;
    private final RoadBlockRepository roadBlockRepository;

    private final MainMapper mapper;

    @Autowired
    public CarGenerationService(MainMapper mapper, LineRepository lineRepository,
                                RoadBlockRepository roadBlockRepository,
                                AutomobileRepository automobileRepository,
                                RoadComponent roadComponent) {
        this.mapper = mapper;
        this.lineRepository = lineRepository;
        this.roadBlockRepository = roadBlockRepository;
        this.automobileRepository = automobileRepository;
        this.roadComponent = roadComponent;
    }

    public void generateCars(int count) {
        Random random = new Random();


        for (int i = 0; i < count && i < roadComponent.getLinesPerSide() * 4; i++) {
            LineDTO ln;

            do {
                int lineNum = random.nextInt(roadComponent.getLinesPerSide() * 4);
                ln = mapper.lineToLineDTO(lineRepository.get(lineNum + 1L).get()); //fixed
            }
            while (ln == null || (ln != null && ln.getStartBlock().getAutomobile() != null));

            RoadBlockDTO startBlock = ln.getStartBlock();
            int autoSpeed = random.nextInt(roadComponent.getMaxAutoSpeed() - roadComponent.getMinAutoSpeed());

            AutomobileDTO automobileDTO = new AutomobileDTO(autoSpeed + roadComponent.getMinAutoSpeed(),
                    DriveModel.values()[random.nextInt(DriveModel.values().length)],
                    startBlock);
            startBlock.setAutomobile(automobileDTO);
            automobileDTO.setRoadBlock(startBlock);

            var auto = mapper.autoDtoToAuto(automobileDTO);
            automobileRepository.save(auto);
            automobileDTO.setId(auto.getId());
            startBlock.setAutomobile(automobileDTO);

            auto = mapper.autoDtoToAuto(automobileDTO);
            automobileRepository.update(auto);
            roadBlockRepository.update(auto.getRoadBlock());
        }

        var d = automobileRepository.getAll();
        var aaaaaaa = 0;
    }
}
