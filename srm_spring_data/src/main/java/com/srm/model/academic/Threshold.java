package com.srm.model.academic;

import com.srm.model.BaseEntity;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Threshold extends BaseEntity {
    //maps a numerical score with a A*/B-/D+/MERIT/DISTINCTION etc...

    private int numerical;
    private String alphabetical;
}
