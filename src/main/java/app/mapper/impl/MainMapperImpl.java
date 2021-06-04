package app.mapper.impl;

import app.domain.DTO.AutomobileDTO;
import app.domain.DTO.LineDTO;
import app.domain.DTO.RoadBlockDTO;
import app.domain.DTO.TrafficLightDTO;
import app.domain.model.Automobile;
import app.domain.model.Line;
import app.domain.model.RoadBlock;
import app.domain.model.TrafficLight;

import java.util.ArrayList;
import java.util.List;


public class MainMapperImpl {


    public Automobile autoDtoToAuto(AutomobileDTO dto) {
        Automobile automobile = new Automobile();
        automobile.setDriveModel(dto.getDriveModel());
        automobile.setHasTurned(dto.getHasTurned());
        automobile.setSpeed(dto.getSpeed());
        automobile.setId(dto.getId());

        if (automobile.getRoadBlock() != null) {
            automobile.setRoadBlock(blockDtoToBlock(dto.getRoadBlock()));
        }

        return automobile;
    }


    public AutomobileDTO autoToAutoDTO(Automobile ent) {
        AutomobileDTO dto = new AutomobileDTO();
        dto.setId(ent.getId());
        dto.setDriveModel(ent.getDriveModel());
        dto.setSpeed(ent.getSpeed());
        dto.setHasTurned(ent.getHasTurned());

        if (ent.getRoadBlock() != null)
            dto.setRoadBlock(blockToBlockDTO(ent.getRoadBlock()));

        return dto;
    }


    public List<Automobile> autoDtoToAuto(List<AutomobileDTO> dto) {
        List<Automobile> res = new ArrayList<>();
        for (var item : dto)
            res.add(autoDtoToAuto(item));

        return res;
    }


    public List<AutomobileDTO> autoToAutoDTO(List<Automobile> ent) {
        List<AutomobileDTO> res = new ArrayList<>();
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
            Automobile auto = new Automobile();
            auto.setSpeed(dto.getAutomobile().getSpeed());
            auto.setRoadBlock(roadBlock);
            auto.setHasTurned(dto.getAutomobile().getHasTurned());
            auto.setDriveModel(dto.getAutomobile().getDriveModel());
            auto.setId(dto.getAutomobile().getId());
            roadBlock.setAutomobile(auto);
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

        if (ent.getAutomobile() != null) {
            AutomobileDTO automobileDTO = new AutomobileDTO();
            automobileDTO.setId(ent.getAutomobile().getId());
            automobileDTO.setSpeed(ent.getAutomobile().getSpeed());
            automobileDTO.setHasTurned(ent.getAutomobile().getHasTurned());
            automobileDTO.setDriveModel(ent.getAutomobile().getDriveModel());
            automobileDTO.setRoadBlock(dto);
        }

        return dto;
    }


    public Line lineDtoToLine(LineDTO dto) {
        Line line = new Line();
        line.setLineLength(dto.getLineLength());
        line.setId(dto.getId());
        //line.setStartBlock(blockDtoToBlock(dto.getStartBlock()));

        return line;
    }


    public LineDTO lineToLineDTO(Line ent) {
        LineDTO lineDto = new LineDTO();
        lineDto.setLineLength(ent.getLineLength());
        lineDto.setId(ent.getId());
        //lineDto.setStartBlock(readAllDescendants(ent.getStartBlock()));

        return lineDto;
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


    public List<Line> lineDtoToLine(List<LineDTO> dto) {
        List<Line> res = new ArrayList<>();

        for (var item : dto)
            res.add(lineDtoToLine(item));

        return res;
    }


    public List<LineDTO> lineToLineDTO(List<Line> ent) {
        List<LineDTO> res = new ArrayList<>();

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
