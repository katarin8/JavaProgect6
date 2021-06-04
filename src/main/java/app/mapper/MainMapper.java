package app.mapper;

import app.domain.DTO.AutomobileDTO;
import app.domain.DTO.LineDTO;
import app.domain.DTO.RoadBlockDTO;
import app.domain.DTO.TrafficLightDTO;
import app.domain.model.Automobile;
import app.domain.model.Line;
import app.domain.model.RoadBlock;
import app.domain.model.TrafficLight;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

@Mapper(componentModel = "spring")
public abstract class MainMapper {
    public Automobile autoDtoToAuto(AutomobileDTO dto) {
        Automobile automobile = new Automobile();
        automobile.setDriveModel(dto.getDriveModel());
        automobile.setHasTurned(dto.getHasTurned());
        automobile.setSpeed(dto.getSpeed());
        automobile.setId(dto.getId());

        if (dto.getRoadBlock() != null) {
            automobile.setRoadBlock(blockDtoToBlockNoReccurency(dto.getRoadBlock()));
        }

        return automobile;
    }

    public AutomobileDTO autoToAutoDTO(Automobile ent) {
        AutomobileDTO dto = new AutomobileDTO();
        dto.setId(ent.getId());
        dto.setDriveModel(ent.getDriveModel());
        dto.setSpeed(ent.getSpeed());
        dto.setHasTurned(ent.getHasTurned());

        if (ent.getRoadBlock() != null) {
            dto.setRoadBlock(blockToBlockDTOnoReccurencyLazy(ent.getRoadBlock()));
            dto.getRoadBlock().setAutomobile(dto);
        }

        return dto;
    }

    private AutomobileDTO autoToAutoDTOLazy(Automobile ent) {
        AutomobileDTO dto = new AutomobileDTO();
        dto.setId(ent.getId());
        dto.setDriveModel(ent.getDriveModel());
        dto.setSpeed(ent.getSpeed());
        dto.setHasTurned(ent.getHasTurned());

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
        if (dto == null) {
            return null;
        }

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

    public RoadBlock blockDtoToBlockNoReccurency(RoadBlockDTO dto) {
        if (dto == null) {
            return null;
        }

        RoadBlock roadBlock = new RoadBlock();

        roadBlock.setId(dto.getId());
        roadBlock.setIsCrossroad(dto.getIsCrossRoad());
        roadBlock.setTrafficLightState(dto.getTrafficLightState());

        if (dto.getAutomobileLinksList()[1] != null) {
            roadBlock.setRightBlock(getRoadBlockFromDTOFields(dto.getAutomobileLinksList()[2]));
            roadBlock.setCenterBlock(getRoadBlockFromDTOFields(dto.getAutomobileLinksList()[1]));
            roadBlock.setLeftBlock(getRoadBlockFromDTOFields(dto.getAutomobileLinksList()[0]));
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

    public List<RoadBlock> blockDtoToBlockNoReccurency(List<RoadBlockDTO> dto) {
        var result = new ArrayList<RoadBlock>();
        for (var blockDTO : dto) {
            result.add(blockDtoToBlock(blockDTO));
        }
        return result;
    }

    public List<RoadBlockDTO> blockToBlockDTOnoReccurency(List<RoadBlock> ent) {
        var res = new ArrayList<RoadBlockDTO>();

        for (var block : ent) {
            res.add(blockToBlockDTOnoReccurency(block));
        }

        return res;
    }

    private RoadBlock getRoadBlockFromDTOFields(RoadBlockDTO dto) {
        if (dto == null)
            return null;

        var res = new RoadBlock();
        res.setId(dto.getId());
        res.setTrafficLightState(dto.getTrafficLightState());
        res.setIsCrossroad(dto.getIsCrossRoad());
        return res;
    }


    public RoadBlockDTO blockToBlockDTO(RoadBlock ent) {
        if (ent == null) {
            return null;
        }

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
            AutomobileDTO automobileDTO = autoToAutoDTOLazy(ent.getAutomobile());
            automobileDTO.setRoadBlock(dto);
        }

        return dto;
    }

    public RoadBlockDTO blockToBlockDTOnoReccurencyLazy(RoadBlock ent) {
        if (ent == null) {
            return null;
        }

        RoadBlockDTO dto = new RoadBlockDTO();
        dto.setId(ent.getId());
        dto.setIsCrossRoad(ent.getIsCrossroad());
        dto.setTrafficLightState(ent.getTrafficLightState());

        if (ent.getCenterBlock() != null) {
            dto.getAutomobileLinksList()[0] = getRoadBlockDTOFromFields(ent.getLeftBlock());
            dto.getAutomobileLinksList()[1] = getRoadBlockDTOFromFields(ent.getCenterBlock());
            dto.getAutomobileLinksList()[2] = getRoadBlockDTOFromFields(ent.getRightBlock());
        }

        return dto;
    }

    public RoadBlockDTO blockToBlockDTOnoReccurency(RoadBlock ent) {
        if (ent == null) {
            return null;
        }

        RoadBlockDTO dto = new RoadBlockDTO();
        dto.setId(ent.getId());
        dto.setIsCrossRoad(ent.getIsCrossroad());
        dto.setTrafficLightState(ent.getTrafficLightState());

        if (ent.getCenterBlock() != null) {
            dto.getAutomobileLinksList()[0] = getRoadBlockDTOFromFields(ent.getLeftBlock());
            dto.getAutomobileLinksList()[1] = getRoadBlockDTOFromFields(ent.getCenterBlock());
            dto.getAutomobileLinksList()[2] = getRoadBlockDTOFromFields(ent.getRightBlock());
        }

        if (ent.getAutomobile() != null) {
            dto.setAutomobile(autoToAutoDTOLazy(ent.getAutomobile()));
            dto.getAutomobile().setRoadBlock(dto);
        }

        return dto;
    }


    private RoadBlockDTO getRoadBlockDTOFromFields(RoadBlock block) {
        if (block == null)
            return null;

        var res = new RoadBlockDTO();
        res.setId(block.getId());
        res.setIsCrossRoad(block.getIsCrossroad());
        res.setTrafficLightState(block.getTrafficLightState());
        return res;
    }

    public Line lineDtoToLine(LineDTO dto) { // only for generation
        Line line = new Line();
        line.setLineLength(dto.getLineLength());
        line.setId(dto.getId());

        List<RoadBlock> lines = new ArrayList<>();
        var block = dto.getStartBlock();

        Stack<RoadBlockDTO> stack = new Stack<>();

        while (block != null) {
            stack.add(block);
            block = block.getAutomobileLinksList()[1];
        }

        var lastBlock = blockDtoToBlockNoReccurency(stack.pop());
        lines.add(lastBlock);

        while (stack.size() > 0) {
            var newBlock = blockDtoToBlockNoReccurency(stack.pop());
            newBlock.setCenterBlock(lastBlock);
            lines.add(newBlock);
            lastBlock = newBlock;
        }

        line.setBlockList(lines);
        return line;
    }

    public LineDTO lineToLineDTO(Line ent) { //only for initialisations
        LineDTO lineDto = new LineDTO();
        lineDto.setLineLength(ent.getLineLength());
        lineDto.setId(ent.getId());

        ent.getBlockList().sort(Comparator.comparingLong(RoadBlock::getId));

        var lastBlock = blockToBlockDTOnoReccurency(ent.getBlockList().get(0));
        for (int i = 1; i < ent.getBlockList().size(); i++) {
            var newBlock = blockToBlockDTOnoReccurency(ent.getBlockList().get(i));
            newBlock.getAutomobileLinksList()[1] = lastBlock;
            lastBlock = newBlock;
        }

        lineDto.setStartBlock(lastBlock);
        return lineDto;
    }
//
//    private RoadBlockDTO readAllDescendants(RoadBlock block){
//        if (block.getLeftBlock() != null)
//            readAllDescendants(block.getLeftBlock());
//
//        if (block.getCenterBlock() != null)
//            readAllDescendants(block.getCenterBlock());
//
//        if (block.getRightBlock() != null)
//            readAllDescendants(block.getRightBlock());
//
//        return blockToBlockDTO(block);
//    }


    public TrafficLight trafficLightDtoTotrafficLight(TrafficLightDTO dto) {
        TrafficLight trafficLight = new TrafficLight();

        trafficLight.setId(dto.getId());
        trafficLight.setCurrentState(dto.getCurrentState());
        trafficLight.setCycleTimeGreen(dto.getCycleTimeGreen());
        trafficLight.setCycleTimeRed(dto.getCycleTimeRed());
        trafficLight.setCycleTimeYellow(dto.getCycleTimeYellow());
        trafficLight.setLastSwitchTime(dto.getLastSwitchTime());

        trafficLight.setControlledBlocks(blockDtoToBlockNoReccurency(dto.getControlledBlocks()));
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

        trafficLightDTO.setControlledBlocks(blockToBlockDTOnoReccurency(ent.getControlledBlocks()));
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