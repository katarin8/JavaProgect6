package app.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoadBlockDTO {

    private Long id;
    private RoadBlockDTO[] automobileLinksList = new RoadBlockDTO[3]; // как хранить для 1-3 полос??? пока заполняем нулями, делаем висячие на пустоту, всегда 3 полосы
    private AutomobileDTO automobile;
    private TrafficLightState trafficLightState;
    private Boolean isCrossRoad = false;

//    public synchronized boolean Subscribe(Automobile auto){
//        if (automobile != null)
//            return false;
//        this.automobile = auto;
//        return true;
//    }
//
//    public synchronized void Block(TrafficLightState state){
//        trafficLightState = state;
//    }
//
//    //обслуживает свой авто
//    @Override
//    public synchronized RoadBlockCallback subscribe(Automobile automobile, DriveModel autoDriveModel) {
//        RoadBlock block;
//        if (automobileLinksList[autoDriveModel.ordinal()] == null){
//            block = (autoDriveModel.ordinal() == 0) ? automobileLinksList[1] : automobileLinksList[3];
//        }else
//            block = automobileLinksList[autoDriveModel.ordinal()];
//
//        if (!block.Subscribe(automobile))
//            return null;
//
//        return this;
//    }
//
//    @Override
//    public void unsubscribe(Automobile automobile) {
//        this.automobile = null;
//    }


    @Override
    public String toString() {
        return "RoadBlockDTO{" +
                "id=" + id +
                ", automobileLinksList=[" + "leftId=" + (automobileLinksList[0] == null ? "null" : automobileLinksList[0].getId()) +
                "centerId=" + (automobileLinksList[1] == null ? "null" : automobileLinksList[1].getId()) + "rightId=" + (automobileLinksList[2] == null ? "null" : automobileLinksList[2].getId()) +
                "], automobileId=" + (automobile == null ? "null" : automobile.getId()) +
                ", trafficLightState=" + trafficLightState +
                ", isCrossRoad=" + isCrossRoad +
                '}';
    }
}
