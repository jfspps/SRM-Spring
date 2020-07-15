package com.srm.model.academic;

import com.srm.model.BaseEntity;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThresholdList extends BaseEntity {

    private List<Threshold> thresholdList;
}
