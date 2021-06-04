package app.service;

import app.component.CrossRoads;
import app.domain.DTO.TrafficLightDTO;
import app.domain.DTO.TrafficLightState;
import app.mapper.MainMapper;
import app.repository.RoadBlockRepository;
import app.repository.TrafficLightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrafficLightService {
    private final CrossRoads crossRoads;
    private final TrafficLightRepository trafficLightRepository;
    private final RoadBlockRepository roadBlockRepository;
    private final MainMapper mapper;

    @Autowired
    public TrafficLightService(MainMapper mapper, TrafficLightRepository trafficLightRepository,
                               CrossRoads crossRoads,
                               RoadBlockRepository roadBlockRepository) {
        this.mapper = mapper;
        this.trafficLightRepository = trafficLightRepository;
        this.crossRoads = crossRoads;
        this.roadBlockRepository = roadBlockRepository;
    }


    public List<TrafficLightDTO> getTrafficLightList() {
        return mapper.trafficLightTotrafficLightDTO(trafficLightRepository.getAll());
    }


    public void startAll() {
        for (TrafficLightDTO trafficLightDTO : mapper.trafficLightTotrafficLightDTO(trafficLightRepository.getAll())) {
            trafficLightDTO.setLastSwitchTime(crossRoads.getCurrentTimeInSeconds());

            trafficLightRepository.update(mapper.trafficLightDtoTotrafficLight(trafficLightDTO));
        }
    }


    public void changeCycleTimeByColor(TrafficLightDTO trafficLightDTO, TrafficLightState color, long time) {
        switch (color) {
            case RED: {
                trafficLightDTO.setCycleTimeRed(time);
                break;
            }
            case YELLOW: {
                trafficLightDTO.setCycleTimeYellow(time);
                break;
            }
            case GREEN: {
                trafficLightDTO.setCycleTimeGreen(time);
                break;
            }
        }

        trafficLightRepository.update(mapper.trafficLightDtoTotrafficLight(trafficLightDTO));
    }


    public void changeCycleTimeByColor(TrafficLightState color, long time) {
        getTrafficLightList().forEach(trafficLight -> changeCycleTimeByColor(trafficLight, color, time));
    }


    public void changeStateByTime() {
        getTrafficLightList().forEach(trafficLight -> {
            long time = crossRoads.getCurrentTimeInSeconds();

            switch (trafficLight.getCurrentState()) {
                case RED: {
                    if (trafficLight.getCycleTimeRed() + trafficLight.getLastSwitchTime() - time <= 0)
                        changeStateIfNeeded(trafficLight);
                    break;
                }
                case YELLOW: {
                    if (trafficLight.getCycleTimeYellow() + trafficLight.getLastSwitchTime() - time <= 0)
                        changeStateIfNeeded(trafficLight);
                    break;
                }
                case GREEN: {
                    if (trafficLight.getCycleTimeGreen() + trafficLight.getLastSwitchTime() - time <= 0)
                        changeStateIfNeeded(trafficLight);
                    break;
                }
            }
        });
    }


    private void changeStateIfNeeded(TrafficLightDTO trafficLightDTO) {
        trafficLightDTO.setCurrentState(
                TrafficLightState.values()[(trafficLightDTO.getCurrentState().ordinal() + 1) % TrafficLightState.values().length]
        );

        trafficLightDTO.setLastSwitchTime(crossRoads.getCurrentTimeInSeconds());

        trafficLightDTO.getControlledBlocks()
                .forEach(roadBlock -> {
                    roadBlock.setTrafficLightState(trafficLightDTO.getCurrentState());
                    roadBlockRepository.update(mapper.blockDtoToBlockNoReccurency(roadBlock));
                });

        trafficLightRepository.update(mapper.trafficLightDtoTotrafficLight(trafficLightDTO));
    }
}
