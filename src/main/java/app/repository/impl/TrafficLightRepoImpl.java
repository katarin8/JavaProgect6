package app.repository.impl;

import app.domain.DTO.TrafficLightState;
import app.domain.model.RoadBlock;
import app.domain.model.TrafficLight;
import app.repository.RoadBlockRepo;
import app.repository.TrafficLightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class TrafficLightRepoImpl implements TrafficLightRepo {

    private final DataSource dataSource;
    private final RoadBlockRepo roadBlockRepo;

    @Autowired
    public TrafficLightRepoImpl(DataSource dataSource, RoadBlockRepo roadBlockRepo) {
        this.dataSource = dataSource;
        this.roadBlockRepo = roadBlockRepo;
    }

    @Override
    public Optional<TrafficLight> get(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            var stmt = connection.createStatement();
            var result = stmt.executeQuery("SELECT FROM trafficlight WHERE id = " + id);
            if (!result.next())
                return Optional.empty();

            var trafficLight = TrafficLight.builder().
                    id(result.getLong("id"))
                    .currentState(TrafficLightState.values()[result.getInt("trafficlightstate")])
                    .cycleTimeGreen(result.getLong("cycletimegreen"))
                    .cycleTimeYellow(result.getLong("cycletimeyellow"))
                    .cycleTimeRed(result.getLong("cycletimered"))
                    .lastSwitchTime(result.getLong("lastswitchtime"));

            stmt.close();
            stmt = connection.createStatement();
            result = stmt.executeQuery("SELECT FROM trafficlight_roadblocks WHERE trafficlight_id = " + id);
            List<RoadBlock> controlledBlocks = new ArrayList<>();

            while (result.next()) {
                var res = roadBlockRepo.get(result.getLong("controlledblocks_id"));
                res.ifPresent(controlledBlocks::add);
            }

            trafficLight.controlledBlocks(controlledBlocks);
            return Optional.of(trafficLight.build());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<TrafficLight> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            List<TrafficLight> trafficLights = new LinkedList<>();
            var stmt = connection.createStatement();
            var result = stmt.executeQuery("SELECT * FROM trafficlight");
            while (result.next()) {
                var trafficLight = TrafficLight.builder().
                        id(result.getLong("id"))
                        .currentState(TrafficLightState.values()[result.getInt("trafficlightstate")])
                        .cycleTimeGreen(result.getLong("cycletimegreen"))
                        .cycleTimeYellow(result.getLong("cycletimeyellow"))
                        .cycleTimeRed(result.getLong("cycletimered"))
                        .lastSwitchTime(result.getLong("lastswitchtime"));

                trafficLights.add(trafficLight.build());
            }
            stmt.close();

            for (var light : trafficLights) {
                stmt = connection.createStatement();
                result = stmt.executeQuery("SELECT * FROM trafficlight_roadblocks WHERE trafficlight_id = " + light.getId());
                List<RoadBlock> controlledBlocks = new ArrayList<>();

                while (result.next()) {
                    var res = roadBlockRepo.get(result.getLong("controlledblocks_id"));
                    res.ifPresent(controlledBlocks::add);
                }

                light.setControlledBlocks(controlledBlocks);
            }

            return trafficLights;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void save(TrafficLight entity) {
        try (Connection connection = dataSource.getConnection()) {
            var stmt = connection.createStatement();
            var args = new Object[]{
                    entity.getCurrentState().ordinal(),
                    entity.getCycleTimeGreen(),
                    entity.getCycleTimeYellow(),
                    entity.getCycleTimeRed(),
                    entity.getLastSwitchTime()
            };
            var q = new MessageFormat("INSERT INTO trafficlight (trafficlightstate, cycletimegreen, cycletimeyellow, cycletimered, lastswitchtime) VALUES " +
                    "({0}, {1}, {2}, {3}, {4}) RETURNING id").format(args);
            var result = stmt.executeQuery(q);
            result.next();
            var id = result.getInt("id");
            stmt.close();

            var msgFormat = new MessageFormat("INSERT INTO trafficlight_roadblocks (trafficlight_id, controlledblocks_id) VALUES ({0}, {1})");
            for (var roadblock : entity.getControlledBlocks()) {
                stmt = connection.createStatement();
                stmt.execute(msgFormat.format(new Object[]{id, roadblock.getId()}));
                stmt.close();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(TrafficLight entity) {
        try (Connection connection = dataSource.getConnection()) {
            var stmt = connection.createStatement();
            var args = new Object[]{
                    entity.getCurrentState().ordinal(),
                    entity.getCycleTimeGreen(),
                    entity.getCycleTimeYellow(),
                    entity.getCycleTimeRed(),
                    Long.toString(entity.getLastSwitchTime()),
                    entity.getId()
            };

            var q = new MessageFormat("UPDATE trafficlight SET (trafficlightstate, cycletimegreen, cycletimeyellow, cycletimered, lastswitchtime) = " +
                    "({0}, {1}, {2}, {3}, {4}) WHERE id = {5}").format(args);
            stmt.execute(q);
            stmt.close();

            for (var roadblock : entity.getControlledBlocks()) {
                stmt = connection.createStatement();
                stmt.execute("UPDATE roadblocks SET trafficlightstate = " + entity.getCurrentState().ordinal() +
                        "WHERE id = " + roadblock.getId());

                stmt.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            var stmt = connection.createStatement();
            stmt.execute("DELETE FROM trafficlight_roadblocks WHERE trafficlight_id = " + id);
            stmt.execute("DELETE FROM trafficlight WHERE id = " + id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(TrafficLight entity) {
        try (Connection connection = dataSource.getConnection()) {
            var stmt = connection.createStatement();
            stmt.execute("DELETE FROM trafficlight WHERE id = " + entity.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void clear() {
        try (Connection connection = dataSource.getConnection()) {
            var stmt = connection.createStatement();
            stmt.executeQuery("TRUNCATE trafficlight");
        } catch (SQLException throwables) {
        }
    }
}
