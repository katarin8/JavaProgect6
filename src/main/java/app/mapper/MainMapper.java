package app.mapper;

import app.domain.DTO.CarsDTO;
import app.domain.DTO.RoadDTO;
import app.domain.DTO.RoadBlockDTO;
import app.domain.DTO.TrafficLightDTO;
import app.domain.model.Cars;
import app.domain.model.Road;
import app.domain.model.RoadBlock;
import app.domain.model.TrafficLight;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

@Mapper(componentModel = "spring")
public abstract class MainMapper {
    public Cars autoDtoToAuto(CarsDTO dto) {
        Cars cars = new Cars();
        cars.setDriveLine(dto.getDriveLine());
        cars.setHasTurned(dto.getHasTurned());
        cars.setSpeed(dto.getSpeed());
        cars.setId(dto.getId());

        if (dto.getRoadBlock() != null) {
            cars.setRoadBlock(blockDtoToBlockNoReccurency(dto.getRoadBlock()));
        }

        return cars;
    }

    public CarsDTO autoToAutoDTO(Cars ent) {
        CarsDTO dto = new CarsDTO();
        dto.setId(ent.getId());
        dto.setDriveLine(ent.getDriveLine());
        dto.setSpeed(ent.getSpeed());
        dto.setHasTurned(ent.getHasTurned());

        if (ent.getRoadBlock() != null) {
            dto.setRoadBlock(blockToBlockDTOnoReccurencyLazy(ent.getRoadBlock()));
            dto.getRoadBlock().setAutomobile(dto);
        }

        return dto;
    }

    private CarsDTO autoToAutoDTOLazy(Cars ent) {
        CarsDTO dto = new CarsDTO();
        dto.setId(ent.getId());
        dto.setDriveLine(ent.getDriveLine());
        dto.setSpeed(ent.getSpeed());
        dto.setHasTurned(ent.getHasTurned());

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

        if (ent.getCars() != null) {
            CarsDTO carsDTO = autoToAutoDTOLazy(ent.getCars());
            carsDTO.setRoadBlock(dto);
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

        if (ent.getCars() != null) {
            dto.setAutomobile(autoToAutoDTOLazy(ent.getCars()));
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

    public Road lineDtoToLine(RoadDTO dto) { // only for generation
        Road road = new Road();
        road.setLineLength(dto.getLineLength());
        road.setId(dto.getId());

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

        road.setBlockList(lines);
        return road;
    }

    public RoadDTO lineToLineDTO(Road ent) { //only for initialisations
        RoadDTO roadDto = new RoadDTO();
        roadDto.setLineLength(ent.getLineLength());
        roadDto.setId(ent.getId());

        ent.getBlockList().sort(Comparator.comparingLong(RoadBlock::getId));

        var lastBlock = blockToBlockDTOnoReccurency(ent.getBlockList().get(0));
        for (int i = 1; i < ent.getBlockList().size(); i++) {
            var newBlock = blockToBlockDTOnoReccurency(ent.getBlockList().get(i));
            newBlock.getAutomobileLinksList()[1] = lastBlock;
            lastBlock = newBlock;
        }

        roadDto.setStartBlock(lastBlock);
        return roadDto;
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