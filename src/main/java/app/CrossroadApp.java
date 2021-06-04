package app;

import app.config.AppConfig;
import app.domain.DTO.CarsDTO;
import app.domain.DTO.TrafficLightDTO;
import app.domain.DTO.TrafficLightState;
import app.service.CarGenerationService;
import app.service.CarMovingService;
import app.service.RoadGenerationService;
import app.service.TrafficLightService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
@ComponentScan(basePackageClasses = {AppConfig.class})
public class CrossroadApp {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(CrossroadApp.class);
        RoadGenerationService roadService = context.getBean(RoadGenerationService.class);
        roadService.initRoad();
        run();

    }

    private static void run() {
        ApplicationContext context = new AnnotationConfigApplicationContext(CrossroadApp.class);
        RoadGenerationService roadService = context.getBean(RoadGenerationService.class);
        CarGenerationService carGenerationService = context.getBean(CarGenerationService.class);
        CarMovingService carMovingService = context.getBean(CarMovingService.class);
        TrafficLightService trafficLightService = context.getBean(TrafficLightService.class);

        if (!roadService.isRoadInitiated())
            roadService.initRoad();

        System.out.println("Дорога сгенерирована");

        trafficLightService.startAll();
        trafficLightService.changeCycleTimeByColor(TrafficLightState.RED, 5);
        trafficLightService.changeCycleTimeByColor(TrafficLightState.YELLOW, 1);
        trafficLightService.changeCycleTimeByColor(TrafficLightState.GREEN, 10);
        System.out.println("Светофоры инициализированны");

        carGenerationService.generateCars(8);

        while (carMovingService.getAllAutomobiles().size() != 0) {
            trafficLightService.changeStateByTime();
            System.out.println("Состояние светофора проверено\n");
            printAllTrafficLights(trafficLightService.getTrafficLightList());

            System.out.println("Перед движением: ");
            printCarsToConsole(carMovingService.getAllAutomobiles());

            carMovingService.moveAllCars();

            System.out.println("После движения: ");
            printCarsToConsole(carMovingService.getAllAutomobiles());


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void printCarsToConsole(List<CarsDTO> cars) {
        System.out.println("Список машин:");
        cars.forEach(auto -> {
            System.out.println("Машина с ID " + auto.getId() + " стоит на дорожном блоке с ID " + auto.getRoadBlock().getId().toString() + "\n");
        });
    }

    private static void printAllTrafficLights(List<TrafficLightDTO> trafficLightDTOS) {
        System.out.println("Список светофоров: ");
        trafficLightDTOS.forEach(trafficLight -> {
            System.out.println("Светофор с ID " + trafficLight.getId().toString()
                    + " имеет состояние " + trafficLight.getCurrentState().toString() + "\n");
        });
    }

}