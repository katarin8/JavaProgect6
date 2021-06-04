package app.service;

import app.component.RoadComponent;
import app.domain.DTO.TrafficLightDTO;
import app.domain.DTO.TrafficLightState;
import app.mapper.MainMapper;
import app.repository.RoadBlockRepo;
import app.repository.TrafficLightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrafficLightService {
    private final RoadComponent roadComponent;
    private final TrafficLightRepo trafficLightRepo;
    private final RoadBlockRepo roadBlockRepo;
    private final MainMapper mapper;

    @Autowired
    public TrafficLightService(MainMapper mapper, TrafficLightRepo trafficLightRepo,
                               RoadComponent roadComponent,
                               RoadBlockRepo roadBlockRepo) {
        this.mapper = mapper;
        this.trafficLightRepo = trafficLightRepo;
        this.roadComponent = roadComponent;
        this.roadBlockRepo = roadBlockRepo;
    }


    public List<TrafficLightDTO> getTrafficLightList() {
        return mapper.trafficLightTotrafficLightDTO(trafficLightRepo.getAll());
    }


    public void startAll() {
        for (TrafficLightDTO trafficLightDTO : mapper.trafficLightTotrafficLightDTO(trafficLightRepo.getAll())) {
            trafficLightDTO.setLastSwitchTime(roadComponent.getCurrentTimeInSeconds());

            trafficLightRepo.update(mapper.trafficLightDtoTotrafficLight(trafficLightDTO));
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

        trafficLightRepo.update(mapper.trafficLightDtoTotrafficLight(trafficLightDTO));
    }


    public void changeCycleTimeByColor(TrafficLightState color, long time) {
        getTrafficLightList().forEach(trafficLight -> changeCycleTimeByColor(trafficLight, color, time));
    }


    public void changeStateByTime() {
        getTrafficLightList().forEach(trafficLight -> {
            long time = roadComponent.getCurrentTimeInSeconds();

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

        trafficLightDTO.setLastSwitchTime(roadComponent.getCurrentTimeInSeconds());

        trafficLightDTO.getControlledBlocks()
                .forEach(roadBlock -> {
                    roadBlock.setTrafficLightState(trafficLightDTO.getCurrentState());
                    roadBlockRepo.update(mapper.blockDtoToBlockNoReccurency(roadBlock));
                });

        trafficLightRepo.update(mapper.trafficLightDtoTotrafficLight(trafficLightDTO));
    }
}
