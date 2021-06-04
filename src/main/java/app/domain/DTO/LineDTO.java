package app.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineDTO {

    private Long id;
    private int lineLength;
    private RoadBlockDTO startBlock; //соединять линии???

    public LineDTO(RoadBlockDTO startBlock, int lineLength) {
        this.startBlock = startBlock;
        this.lineLength = lineLength;
    }

//    public LineDTO(int lineLength){
//        this.lineLength = lineLength;
//
////        startBlock = new RoadBlock();
////        RoadBlock curr = startBlock;
////
////        for (int i = 0; i < lineLength - 1; i++) {
////            RoadBlock next = new RoadBlock();
////            curr.getAutomobileLinksList()[1] = next;
////            curr = next;
////        }
//    }

//    public RoadBlock getBlockByIndex(int index){
//        if (index > 100 || index < 0)
//            return null;
//
//        RoadBlock block = startBlock;
//        for(int i = 0; i < index && block != null; i++){
//            block = block.getAutomobileLinksList()[1];
//        }
//
//        return block;
//    }
//
//    public boolean[] getBlockLinksByIndex(int index){
//        RoadBlock block = getBlockByIndex(index);
//        boolean[] res = new boolean[3];
//
//        for (int i = 0; i < 3; i++)
//            res[i] = block.getAutomobileLinksList()[i] != null;
//
//        return res;
//    }


}
