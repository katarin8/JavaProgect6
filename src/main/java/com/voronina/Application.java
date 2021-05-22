package com.voronina;

import com.voronina.configurations.SimulationConfiguration;
import com.voronina.data.simulation.Simulator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            ApplicationContext context = new AnnotationConfigApplicationContext(SimulationConfiguration.class);
            Simulator simulator = new Simulator(context);
            simulator.simulate();
        };
    }

}

