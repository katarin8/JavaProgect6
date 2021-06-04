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

    //static Logger LOG = Logger.getLogger(CrossroadApp.class.getName());


    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(CrossroadApp.class);
//        var block = context.getBean(RoadGenerationService.class);
//
//        block.initRoad();

//        test.setTrafficLightState(TrafficLightState.RED);
//        var test = new RoadBlock();
//        test.setIsCrossroad(true);
//        block.save(test);
//        var test1 = new RoadBlock();
//        test1.setTrafficLightState(TrafficLightState.RED);
//        test1.setIsCrossroad(true);
//        block.save(test1);


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

        System.out.println("Road Generated");

        trafficLightService.startAll();
        trafficLightService.changeCycleTimeByColor(TrafficLightState.RED, 5);
        trafficLightService.changeCycleTimeByColor(TrafficLightState.YELLOW, 1);
        trafficLightService.changeCycleTimeByColor(TrafficLightState.GREEN, 10);
        System.out.println("Traffic Lights are initiated");

        carGenerationService.generateCars(8);

        while (carMovingService.getAllAutomobiles().size() != 0) {
            trafficLightService.changeStateByTime();
            System.out.println("Traffic lights state was checked\n");
            printAllTrafficLights(trafficLightService.getTrafficLightList());

            System.out.println("Before moving: ");
            printCarsToConsole(carMovingService.getAllAutomobiles());

            carMovingService.moveAllCars();

            System.out.println("After moving: ");
            printCarsToConsole(carMovingService.getAllAutomobiles());


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void printCarsToConsole(List<CarsDTO> cars) {
        System.out.println("Automobile list:");
        cars.forEach(auto -> {
            System.out.println("auto with ID " + auto.getId() + " stands on road block with ID " + auto.getRoadBlock().getId().toString() + "\n");
        });
    }

    private static void printAllTrafficLights(List<TrafficLightDTO> trafficLightDTOS) {
        System.out.println("Traffic light list: ");
        trafficLightDTOS.forEach(trafficLight -> {
            System.out.println("trafficLight with ID " + trafficLight.getId().toString()
                    + " has state " + trafficLight.getCurrentState().toString() + "\n");
        });
    }

}