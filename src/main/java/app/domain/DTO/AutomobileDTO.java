package app.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutomobileDTO {

    private Long id;
    private Integer speed;
    private DriveModel driveModel;
    private RoadBlockDTO roadBlock;
    private Boolean hasTurned = false;

    public AutomobileDTO(Integer speed, DriveModel driveModel, RoadBlockDTO roadBlock) {
        this.speed = speed;
        this.driveModel = driveModel;
        this.roadBlock = roadBlock;
    }

    //    private RoadBlockCallback roadBlockCallback;

//    public Automobile(Integer targetSpeed, DriveModel driveModel, RoadBlockCallback roadBlockCallback) {
//        this.targetSpeed = targetSpeed;
//        this.driveModel = driveModel;
//        this.currentSpeed = 0;
//        this.roadBlockCallback = roadBlockCallback;
//    }

//    public Automobile(Integer targetSpeed, RoadBlockCallback roadBlockCallback) {
//        this(targetSpeed, DriveModel.values()[new Random().nextInt(3)], roadBlockCallback);
//    }

//    public Automobile(RoadBlockCallback roadBlockCallback){
//        this(new Random().nextInt(90) + 30, roadBlockCallback);
//    }
//
//    public void go(){
//        RoadBlockCallback blockCallback = roadBlockCallback.subscribe(this, driveModel);
//
//        if (blockCallback != null){
//            roadBlockCallback.unsubscribe(this);
//            roadBlockCallback = blockCallback;
//        }
//    }
}
