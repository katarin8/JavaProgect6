package app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RoadComponent {

    private static final int LINES_PER_SIDE = 2;
    private static final int LINE_LENGTH = 100;

    private static final int TRAFFIC_LIGHT_DIST = 20;

    private static final int MIN_AUTO_SPEED = 1;
    private static final int MAX_AUTO_SPEED = 3;

    @Autowired
    public RoadComponent() {
    }

    public int getLinesPerSide() {
        return LINES_PER_SIDE;
    }

    public int getLineLength() {
        return LINE_LENGTH;
    }

    public int getTrafficLightDist() {
        return TRAFFIC_LIGHT_DIST;
    }

    public long getCurrentTimeInSeconds() {
        return Instant.now().getEpochSecond();
    }

    public int getMinAutoSpeed() {
        return MIN_AUTO_SPEED;
    }

    public int getMaxAutoSpeed() {
        return MAX_AUTO_SPEED;
    }


}
