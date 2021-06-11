package app.mapper.impl;

import app.domain.DTO.CarsDTO;
import app.domain.DTO.RoadDTO;
import app.domain.DTO.RoadBlockDTO;
import app.domain.DTO.TrafficLightDTO;
import app.domain.model.Cars;
import app.domain.model.Road;
import app.domain.model.RoadBlock;
import app.domain.model.TrafficLight;

import java.util.ArrayList;
import java.util.List;


public class MainMapperImpl {


    public Cars autoDtoToAuto(CarsDTO dto) {
        Cars cars = new Cars();
        cars.setDriveLine(dto.getDriveLine());
        cars.setHasTurned(dto.getHasTurned());
        cars.setSpeed(dto.getSpeed());
        cars.setId(dto.getId());

        if (cars.getRoadBlock() != null) {
            cars.setRoadBlock(blockDtoToBlock(dto.getRoadBlock()));
        }

        return cars;
    }


    public CarsDTO autoToAutoDTO(Cars ent) {
        CarsDTO dto = new CarsDTO();
        dto.setId(ent.getId());
        dto.setDriveLine(ent.getDriveLine());
        dto.setSpeed(ent.getSpeed());
        dto.setHasTurned(ent.getHasTurned());

        if (ent.getRoadBlock() != null)
            dto.setRoadBlock(blockToBlockDTO(ent.getRoadBlock()));

        return dto;
    }


    public List<Cars> autoDtoToAuto(List<CarsDTO> dto) {
        List<Cars> res = new ArrayList<>();
        for (var item : dto)
            res.add(autoDtoToAuto(item));

        return res;
    }


    public List<CarsDTO> autoToAutoDTO(List<Cars> ent) {
        List<CarsDTO> res = new ArrayList<>();
        for (var item : ent)
            res.add(autoToAutoDTO(item));

        return res;
    }


    public RoadBlock blockDtoToBlock(RoadBlockDTO dto) {
        RoadBlock roadBlock = new RoadBlock();

        roadBlock.setId(dto.getId());
        roadBlock.setIsCrossroad(dto.getIsCrossRoad());
        roadBlock.setTrafficLightState(dto.getTrafficLightState());

        if (dto.getAutomobileLinksList()[1] != null) {
            roadBlock.setRightBlock(blockDtoToBlock(dto.getAutomobileLinksList()[2]));
            roadBlock.setCenterBlock(blockDtoToBlock(dto.getAutomobileLinksList()[1]));
            roadBlock.setLeftBlock(blockDtoToBlock(dto.getAutomobileLinksList()[0]));
        }

        if (dto.getAutomobile() != null) {
            Cars auto = new Cars();
            auto.setSpeed(dto.getAutomobile().getSpeed());
            auto.setRoadBlock(roadBlock);
            auto.setHasTurned(dto.getAutomobile().getHasTurned());
            auto.setDriveLine(dto.getAutomobile().getDriveLine());
            auto.setId(dto.getAutomobile().getId());
            roadBlock.setCars(auto);
        }

        return roadBlock;
    }


    public RoadBlockDTO blockToBlockDTO(RoadBlock ent) {
        RoadBlockDTO dto = new RoadBlockDTO();
        dto.setId(ent.getId());
        dto.setIsCrossRoad(ent.getIsCrossroad());
        dto.setTrafficLightState(ent.getTrafficLightState());

        if (ent.getCenterBlock() != null) {
            dto.getAutomobileLinksList()[0] = blockToBlockDTO(ent.getLeftBlock());
            dto.getAutomobileLinksList()[1] = blockToBlockDTO(ent.getCenterBlock());
            dto.getAutomobileLinksList()[2] = blockToBlockDTO(ent.getRightBlock());
        }

        if (ent.getCars() != null) {
            CarsDTO carsDTO = new CarsDTO();
            carsDTO.setId(ent.getCars().getId());
            carsDTO.setSpeed(ent.getCars().getSpeed());
            carsDTO.setHasTurned(ent.getCars().getHasTurned());
            carsDTO.setDriveLine(ent.getCars().getDriveLine());
            carsDTO.setRoadBlock(dto);
        }

        return dto;
    }


    public Road lineDtoToLine(RoadDTO dto) {
        Road road = new Road();
        road.setLineLength(dto.getLineLength());
        road.setId(dto.getId());
        //road.setStartBlock(blockDtoToBlock(dto.getStartBlock()));

        return road;
    }


    public RoadDTO lineToLineDTO(Road ent) {
        RoadDTO roadDto = new RoadDTO();
        roadDto.setLineLength(ent.getLineLength());
        roadDto.setId(ent.getId());
        //roadDto.setStartBlock(readAllDescendants(ent.getStartBlock()));

        return roadDto;
    }

    private RoadBlockDTO readAllDescendants(RoadBlock block) {
        if (block.getLeftBlock() != null)
            readAllDescendants(block.getLeftBlock());

        if (block.getCenterBlock() != null)
            readAllDescendants(block.getCenterBlock());

        if (block.getRightBlock() != null)
            readAllDescendants(block.getRightBlock());

        return blockToBlockDTO(block);
    }


    public TrafficLight trafficLightDtoTotrafficLight(TrafficLightDTO dto) {
        TrafficLight trafficLight = new TrafficLight();

        trafficLight.setId(dto.getId());
        trafficLight.setCurrentState(dto.getCurrentState());
        trafficLight.setCycleTimeGreen(dto.getCycleTimeGreen());
        trafficLight.setCycleTimeRed(dto.getCycleTimeRed());
        trafficLight.setCycleTimeYellow(dto.getCycleTimeYellow());
        trafficLight.setLastSwitchTime(dto.getLastSwitchTime());

        trafficLight.setControlledBlocks(blockDtoToBlock(dto.getControlledBlocks()));
        return trafficLight;
    }


    public TrafficLightDTO trafficLightTotrafficLightDTO(TrafficLight ent) {
        var trafficLightDTO = new TrafficLightDTO();

        trafficLightDTO.setId(ent.getId());
        trafficLightDTO.setCurrentState(ent.getCurrentState());
        trafficLightDTO.setCycleTimeGreen(ent.getCycleTimeGreen());
        trafficLightDTO.setCycleTimeRed(ent.getCycleTimeRed());
        trafficLightDTO.setCycleTimeYellow(ent.getCycleTimeYellow());
        trafficLightDTO.setLastSwitchTime(ent.getLastSwitchTime());

        trafficLightDTO.setControlledBlocks(blockToBlockDTO(ent.getControlledBlocks()));
        return trafficLightDTO;
    }


    public List<RoadBlock> blockDtoToBlock(List<RoadBlockDTO> dto) {
        List<RoadBlock> res = new ArrayList<>();
        for (var dt : dto)
            res.add(blockDtoToBlock(dt));

        return res;
    }


    public List<RoadBlockDTO> blockToBlockDTO(List<RoadBlock> ent) {
        List<RoadBlockDTO> res = new ArrayList<>();
        for (var elem : ent)
            res.add(blockToBlockDTO(elem));

        return res;
    }


    public List<Road> lineDtoToLine(List<RoadDTO> dto) {
        List<Road> res = new ArrayList<>();

        for (var item : dto)
            res.add(lineDtoToLine(item));

        return res;
    }


    public List<RoadDTO> lineToLineDTO(List<Road> ent) {
        List<RoadDTO> res = new ArrayList<>();

        for (var item : ent)
            res.add(lineToLineDTO((item)));

        return res;
    }


    public List<TrafficLight> trafficLightDtoTotrafficLight(List<TrafficLightDTO> dto) {
        List<TrafficLight> res = new ArrayList<>();

        for (var item : dto)
            res.add(trafficLightDtoTotrafficLight(item));

        return res;
    }


    public List<TrafficLightDTO> trafficLightTotrafficLightDTO(List<TrafficLight> ent) {
        List<TrafficLightDTO> res = new ArrayList<>();

        for (var item : ent)
            res.add(trafficLightTotrafficLightDTO(item));

        return res;
    }
}
