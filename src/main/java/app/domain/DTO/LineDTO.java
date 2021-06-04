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
    private RoadBlockDTO startBlock;

    public LineDTO(RoadBlockDTO startBlock, int lineLength) {
        this.startBlock = startBlock;
        this.lineLength = lineLength;
    }

}
