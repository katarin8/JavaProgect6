package app.service;

import app.component.RoadComponent;
import app.domain.DTO.LineDTO;
import app.domain.DTO.RoadBlockDTO;
import app.domain.DTO.TrafficLightDTO;
import app.domain.DTO.TrafficLightState;
import app.mapper.MainMapper;
import app.repository.LineRepository;
import app.repository.RoadBlockRepository;
import app.repository.TrafficLightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoadGenerationService {
    private final RoadComponent roadComponent;

    private final LineRepository lineRepository;
    private final RoadBlockRepository roadBlockRepository;
    private final TrafficLightRepository trafficLightRepository;

    private final MainMapper mapper;

    @Autowired
    public RoadGenerationService(LineRepository lineRepository,
                                 RoadComponent roadComponent,
                                 RoadBlockRepository roadBlockRepository,
                                 TrafficLightRepository trafficLightRepository, MainMapper mapper) {
        this.lineRepository = lineRepository;
        this.roadComponent = roadComponent;
        this.roadBlockRepository = roadBlockRepository;
        this.trafficLightRepository = trafficLightRepository;
        this.mapper = mapper;
    }

    public void initRoad() {

        generateRoad();
        initTrafficLights();
    }

    public boolean isRoadInitiated() {
        return roadBlockRepository.getAll().size() == roadComponent.getLineLength() * roadComponent.getLinesPerSide() * 4;
    }

    private void generateRoad() {
        //инициирует дороги и связывает однонаправленные

        List<LineDTO> lines = new ArrayList<>();

        for (int index = 0; index < 4; index++) {
            for (int j = 0; j < roadComponent.getLinesPerSide(); j++) {
                //lineRepository.save(new Line(roadComponent.getLineLength()));
                initLine(roadComponent.getLineLength());

                if (j > 0) {
                    var firstLine = lineRepository.get((long) (index * roadComponent.getLinesPerSide() + j)).get();
                    if (!lines.stream().anyMatch(dto -> dto.getId() == firstLine.getId()))
                        lines.add(mapper.lineToLineDTO(firstLine));

                    var aaa = mapper.lineToLineDTO(firstLine);

                    var secLine = lineRepository.get((long) (index * roadComponent.getLinesPerSide() + j + 1)).get(); // FIXED COUNTER
                    if (!lines.stream().anyMatch(dto -> dto.getId() == secLine.getId()))
                        lines.add(mapper.lineToLineDTO(secLine));

                    linkCoDirectionalLines(lines.get(index * roadComponent.getLinesPerSide() + j - 1),
                            lines.get(index * roadComponent.getLinesPerSide() + j),
                            roadComponent.getLineLength());
                }
            }
        }

        //  List<Line> linesList = lineRepository.getAll();
        final int LINES_PER_SIDE = roadComponent.getLinesPerSide();
        final int LINE_LENGTH = roadComponent.getLineLength();
        final int LINE_COUNT = roadComponent.getLinesPerSide() * 4;

        //Связывает дороги на перекрестке
        for (int index = 0; index < 4; index++) {
            RoadBlockDTO leftTurn = getRoadBlockShiftByIndex(
                    lines.get(LINES_PER_SIDE * index).getStartBlock(), //not fixed
                    LINE_LENGTH / 2);

            RoadBlockDTO rightTurn = getRoadBlockShiftByIndex(
                    lines.get(LINES_PER_SIDE * (index + 1) - 1).getStartBlock(), //not fixed
                    LINE_LENGTH / 2 - LINES_PER_SIDE);

            leftTurn.getAutomobileLinksList()[0] = getRoadBlockShiftByIndex(lines
                            .get(LINE_COUNT / 2 + (index < 2 ? index * LINES_PER_SIDE : -(index / 2 + index % 2) * LINES_PER_SIDE)).getStartBlock(), //not fixed
                    (LINE_LENGTH / 2 - 1));

            rightTurn.getAutomobileLinksList()[2] = getRoadBlockShiftByIndex(lines
                            .get(LINE_COUNT - (index < 2 ? index : index - 2 * (index % 2) + 1) * LINES_PER_SIDE - 1).getStartBlock(), //not fixed
                    LINE_LENGTH / 2 + (LINES_PER_SIDE - 1));

            leftTurn.setIsCrossRoad(true);
            rightTurn.setIsCrossRoad(true);

            roadBlockRepository.update(mapper.blockDtoToBlockNoReccurency(leftTurn));
            roadBlockRepository.update(mapper.blockDtoToBlockNoReccurency(rightTurn));
        }
    }

    public void initLine(int lineLength) {

        RoadBlockDTO startBlock = new RoadBlockDTO();
        startBlock.setTrafficLightState(TrafficLightState.GREEN);
        RoadBlockDTO curr = startBlock;

        //roadBlockRepository.save(startBlock);

        for (int i = 0; i < lineLength - 1; i++) {
            RoadBlockDTO next = new RoadBlockDTO();
            next.setTrafficLightState(TrafficLightState.GREEN);
            curr.getAutomobileLinksList()[1] = next;
            curr = next;
        }

        lineRepository.save(mapper.lineDtoToLine(new LineDTO(startBlock, lineLength)));
    }

    private void linkRoadBlocksByIndex(RoadBlockDTO from, RoadBlockDTO to, int index) {
        if (index < 0 || index > 2)
            return;

        from.getAutomobileLinksList()[index] = to;
        roadBlockRepository.update(mapper.blockDtoToBlock(from));
    }

    private RoadBlockDTO getRoadBlockShiftByIndex(RoadBlockDTO stratBlock, int index) {
        RoadBlockDTO block = stratBlock;
        for (int i = 0; i < index; i++)
            block = block.getAutomobileLinksList()[1];

        return block;
    }

    private void initTrafficLights() {
        final int LINE_LENGTH = roadComponent.getLineLength();
        final int LINES_PER_SIDE = roadComponent.getLinesPerSide();
        final int TRAFFIC_LIGHT_DIST = roadComponent.getTrafficLightDist();

        for (int index = 0; index < 4; index++) {
            List<RoadBlockDTO> roadBlockDTOS = new ArrayList<>();

            for (int j = 0; j < roadComponent.getLinesPerSide(); j++) {
                RoadBlockDTO block = getRoadBlockShiftByIndex(
                        mapper.lineToLineDTO(lineRepository.get((long) (index * roadComponent.getLinesPerSide() + j + 1)).get()).getStartBlock(), //fixed
                        (LINE_LENGTH / 2 - LINES_PER_SIDE - 2 - TRAFFIC_LIGHT_DIST));

                roadBlockDTOS.add(block);

                for (int blockInd = 0; blockInd < TRAFFIC_LIGHT_DIST; blockInd++) {
                    block = block.getAutomobileLinksList()[1];
                    roadBlockDTOS.add(block);
                }
            }

            roadBlockDTOS.forEach(roadBlock -> {
                roadBlock.setTrafficLightState(TrafficLightState.RED);
                roadBlockRepository.update(mapper.blockDtoToBlockNoReccurency(roadBlock));
            });

            var res = mapper.trafficLightDtoTotrafficLight(new TrafficLightDTO(roadBlockDTOS, TrafficLightState.RED));
            trafficLightRepository.save(res);
            var c = 0;
        }
    }

    private void linkCoDirectionalLines(LineDTO left, LineDTO right, int targetLineCount) {
        if (left.getLineLength() != right.getLineLength())
            return;

        RoadBlockDTO leftBlock = left.getStartBlock();
        RoadBlockDTO rightBlock = right.getStartBlock();

        for (int i = 0; i < left.getLineLength() - 1; i++) {
            RoadBlockDTO leftNextBlock = leftBlock.getAutomobileLinksList()[1];
            RoadBlockDTO rightNextBlock = rightBlock.getAutomobileLinksList()[1];

            if (!(i <= left.getLineLength() / 2 - (targetLineCount - 1) && i >= left.getLineLength() / 2 - 3)) {
                linkRoadBlocksByIndex(leftBlock, rightNextBlock, 2);
                linkRoadBlocksByIndex(rightBlock, leftNextBlock, 0);
            }

            leftBlock = leftNextBlock;
            rightBlock = rightNextBlock;
        }
    }

}
