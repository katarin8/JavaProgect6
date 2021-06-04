package app.service;

import app.domain.DTO.CarsDTO;
import app.domain.DTO.RoadBlockDTO;
import app.domain.DTO.TrafficLightState;
import app.mapper.MainMapper;
import app.repository.CarsRepo;
import app.repository.RoadBlockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarMovingService {

    private final RoadBlockRepo roadBlockRepo;
    private final CarsRepo carsRepo;

    private final MainMapper mapper;

    @Autowired
    public CarMovingService(MainMapper mapper, RoadBlockRepo roadBlockRepo, CarsRepo carsRepo) {
        this.mapper = mapper;
        this.roadBlockRepo = roadBlockRepo;
        this.carsRepo = carsRepo;
    }

    public void moveAllCars() {
        List<Long> carsToRemove = new ArrayList<>();

        getAllAutomobiles().forEach(auto -> {
            //for (int i = 0; i < auto.getSpeed(); i++) {
            RoadBlockDTO currRoadBlockDTO = auto.getRoadBlock();

            if (currRoadBlockDTO != null)
                if (currRoadBlockDTO.getAutomobileLinksList()[0] == null &&
                        currRoadBlockDTO.getAutomobileLinksList()[1] == null &&
                        currRoadBlockDTO.getAutomobileLinksList()[2] == null) {

                    moveCar(currRoadBlockDTO, null, auto);
                    carsToRemove.add(auto.getId());

                } else if (currRoadBlockDTO.getAutomobileLinksList()[auto.getDriveModel().ordinal()] != null &&
                        currRoadBlockDTO.getAutomobileLinksList()[auto.getDriveModel().ordinal()].getAutomobile() == null && !auto.getHasTurned()) {

                    RoadBlockDTO nextRoadBlockDTO = currRoadBlockDTO.getAutomobileLinksList()[auto.getDriveModel().ordinal()];

                    if (currRoadBlockDTO.getIsCrossRoad() && !auto.getHasTurned())
                        auto.setHasTurned(true);

                    moveCar(currRoadBlockDTO, nextRoadBlockDTO, auto);

                } else if (currRoadBlockDTO.getAutomobileLinksList()[1] != null &&
                        (currRoadBlockDTO.getAutomobileLinksList()[auto.getDriveModel().ordinal()] == null || auto.getHasTurned())) {

                    moveCar(currRoadBlockDTO, currRoadBlockDTO.getAutomobileLinksList()[1], auto);
                }
            //}
        });

        carsToRemove.forEach(carsRepo::delete);
    }

    public List<CarsDTO> getAllAutomobiles() {
        return mapper.autoToAutoDTO(carsRepo.getAll());
    }

    private void moveCar(RoadBlockDTO currBlock, RoadBlockDTO nextBlock, CarsDTO auto) {

        if (nextBlock == null) {
            currBlock.setAutomobile(null);
            auto.setRoadBlock(null);
            return;
        }

        //aaaaa
        if (nextBlock.getAutomobile() == null &&
                nextBlock.getTrafficLightState().ordinal() == TrafficLightState.GREEN.ordinal()) {

            nextBlock.setAutomobile(auto);
            currBlock.setAutomobile(null);
            auto.setRoadBlock(nextBlock);
        } else
            return;

        var currBlockEnt = mapper.blockDtoToBlockNoReccurency(currBlock);
        var nextBlockEnt = mapper.blockDtoToBlockNoReccurency(nextBlock);
        var autoEnt = mapper.autoDtoToAuto(auto);
        roadBlockRepo.updateSavingNextBlocks(currBlockEnt);
        roadBlockRepo.updateSavingNextBlocks(nextBlockEnt);
        carsRepo.update(autoEnt);
    }
}
